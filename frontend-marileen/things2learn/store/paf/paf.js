
/*
 * Centralized State Management with vuex
 * Manage state, mutations and actions
 * like in vuex described https://vuex.vuejs.org/
 *
 */

import {supportsCrypto, hash, encode64} from '../../utils/hashing.js'
import { db } from 'baqend'

export const state = () => ({
  counter : 0,
  courses : [],
  topics : [],
  questions : [],
  learningStates : [],
  user : []
});

export const mutations = {
  increment (state) {
    state.counter++
  },
  setCourses (state, courses) {
    state.courses = courses;
  },
  setTopics (state, topics) {
    state.topics = topics;
  },
  setQuestion (state, {question, ls} ) {
    question.learningState = ls;
  },
  setQuestions (state, questions ) {
    state.questions = questions
  },
  setLearningStates (state, learningStates) {
    state.learningStates = learningStates;
  },
  setUser (state, user) {
    state.user = user;
  }
};

export const actions = {

  async initBackend ({ commit }) {
    await console.log(db.connect('things2learn'));
  },

  checklogin ({commit}) {
    if (window.sessionStorage.getItem("user") != null) {
      commit( 'setUser', JSON.parse(window.sessionStorage.getItem("user")) );
      return (window.sessionStorage.getItem("user")).isLoggedIn;
    }
  },

  async login({commit}, { email, password }) {

    console.log('login');

    var hashedPassword = null;

    // todo Kommentar dass mir bewusst ist dass es hier krachen kann
    // todo um pw schutz einzubauen, was anderes machen
    if ( supportsCrypto() ) {
      hashedPassword = await hash('SHA-256', password);
      hashedPassword = await encode64(hashedPassword);
    }

    try {
      const response = await fetch('http://127.0.0.1:8050/user/login', {
        method: 'POST',
        mode: 'cors',
        headers: {
          // 'Authorization': `bearer ${token}`,
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          "email": email,
          "password": hashedPassword || password
        })
      });

      if (response.ok) {

        var userData = await response.json();
        userData.isLoggedIn = true;

        //write to Vuex store and Browser Session Store
        window.sessionStorage.setItem("user", JSON.stringify(userData) );
        commit('setUser', userData);

      } else {
        var userData = [];
        userData.loginFailed = true;
        commit('setUser', userData);
      }

    } catch (e) {
      console.log(e)
    }
  },

  logout({commit}) {
    window.sessionStorage.removeItem("user");
    var userData = [];
    userData.isLoggedIn = false;
    commit('setUser', userData);
  },

  async getLearningStates({commit}) {
    console.log('loading learning states')
    try {
      const response = await fetch('http://127.0.0.1:8050/state', {
        method: 'GET',
        mode: 'cors',
        headers: {
          'Content-Type': 'application/json'
        }
      });

      switch (response.status) {
        case 200 : {
          console.log('ok');
          commit('setLearningStates', await response.json());
          break;
        }

        case 204 : {
          console.log('no content');
          break;
        }

        default : {
          console.log('get all learningStates failed');

        }
      }


    } catch (e) {
      console.log('error in getLearningStates (all)');
      console.log(e);
    }

  },

  async getQuestions ({commit}, { courseName, user }) {

    if (courseName != undefined ) {

      try {
        const response = await fetch('http://127.0.0.1:8050/questions/course/' + courseName , {
          method: 'GET',
          mode: 'cors',
          headers: {
            'Authorization': 'Bearer ' + user.jsonWebToken,
            'Content-Type': 'application/json'
          }
        });

        if (response.ok) {

          var questions = await response.json();

          //get Learningstates for Questions
          for (var question of questions) {
            console.log('get Questions - get ls for q');
            console.log(question);
            console.log('ls:', await getLearningState(user.userID, question.questionID, user.jsonWebToken));
            question.learningState = await getLearningState(user.userID, question.questionID, user.jsonWebToken)

            //question.learningState = { test : 'loo' }

          }
          commit('setQuestions', questions);

        } else {
          console.log('questions failed');
        }

      } catch (e) {
        console.log(e)
      }
    }

  },

  async getTopics ({commit}, courseName) {

    if (courseName != undefined ) {

      try {
        const response = await fetch('http://127.0.0.1:8050/topics/course/' + courseName, {
          method: 'GET',
          mode: 'cors',
          headers: {
            // 'Authorization': `bearer ${token}`,
            'Content-Type': 'application/json'
          }
        });

        if (response.ok) {

          commit('setTopics', await response.json());

        } else {
          console.log('topics failed');
        }

      } catch (e) {
        console.log(e)
      }
    }

  },

  async getCourses ({commit}) {
    try {
      const response = await fetch('http://127.0.0.1:8050/courses',{
        method : 'GET',
        mode: 'cors',
        headers: {
          // 'Authorization': `bearer ${token}`,
          'Content-Type': 'application/json'
        }
      });

      if (response.ok) {

        commit('setCourses', await response.json());


      } else {
        console.log('bahh');
      }

    } catch (e) {
      console.log(e)
    }
  },

  async setNewLearningstate ({commit}, {ls, question}) {
    console.log('store: setNewLearningstate');

    try {
      const response = await fetch('http://127.0.0.1:8050/user/' + this.state.user.userID + '/state/question/' + question.questionID, {
        method: 'PUT',
        mode: 'cors',
        headers: {
          'Authorization': 'Bearer ' + this.state.user.jsonWebToken,
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          "learningStateID": ls.learningStateID
        })
      });

      switch (response.status) {
        case 200 : {
          commit('setQuestion', { question : question, ls : ls });
          console.log('new ls set ok');

          break;
        }

        default : {
          console.log('set new learningStates failed');

        }
      }

    } catch (e) {
      console.log('error in set new LearningState: ', e);
    }

  },

  async setLearningStateImage ({commit}, { lsId }) {

    console.log('store: setLearningStateImage');
    try {
      const response = await fetch('http://127.0.0.1:8050/state/' + lsId + '/image', {
        method: 'GET',
        mode: 'cors',
        headers: {
          'Content-Type': 'application/json'
        }
      });

      if (response.ok) {

        console.log('ls image ok');
        let imgStream = await response.blob();
        return URL.createObjectURL(imgStream);

      } else {
        console.log('ls image failed');
        return null;
      }

    } catch (e) {
      console.log(e);
      return null;
    }
  }
};


async function getLearningState( userId, questionId, token ) {

  if (userId != undefined && questionId != undefined) {

    try {
      const response = await fetch('http://127.0.0.1:8050/user/' + userId + '/state/question/' + questionId, {
        method: 'GET',
        mode: 'cors',
        headers: {
          'Authorization': 'Bearer ' + token,
          'Content-Type': 'application/json'
        }
      });

      switch (await response.status) {
        case 200 : {
          //console.log('ls ok');

          return await response.json();
          break;
        }

        case 404 : {
          //console.log('no Learningstate found');
          return null;
          break;
        }

        default : {
          //console.log('learningState failed');
          return null;
        }
      }


    } catch (e) {
      console.log('error in getLearningState');
      console.log(e);
    }
  }

}


