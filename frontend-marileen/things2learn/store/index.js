//import Vue from 'Vue';

export const state = () => ({
  counter: 8,
  courses : [],
  topics : [],
  questions : [],
  learningStates : [],
  user : []
})

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
  setQuestions (state, questions ) {
    state.questions = questions
  },
  setLearningstate (state, { learningState, questionId }) {

    var question = state.questions.find(function(el) {
      return el.questionID == questionId
    });

    question.learningState = learningState;

    Vue.set(question, 'learningState', learningState);

    console.log('set ls');
  },

  setUser (state, user) {
    state.user = user;

    console.log('set user: ', user);
  }
}

export const actions = {

  async login({commit}, { email, password }) {

    console.log('login');

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
          "password": password
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

  //todo
  logout() {
    window.sessionStorage.removeItem("user");
    var userData = [];
    userData.isLoggedIn = false;

    commit('setUser', userData);
  },

  async getQuestions ({commit}, { courseName, user }) {

    if (courseName != undefined ) {

      try {
        const response = await fetch('http://127.0.0.1:8050/questions/' + courseName , {
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
            console.log(question);
            question.learningState = await getLearningState(user.userID, question.questionID, user.jsonWebToken)

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
        const response = await fetch('http://127.0.0.1:8050/topics/' + courseName, {
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

        console.log('this: ', this);
        commit('setCourses', await response.json());


      } else {
        console.log('bahh');
      }

    } catch (e) {
      console.log(e)
    }
  }
}

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

      switch (response.status) {
        case 200 : {
          console.log('ok');
          //commit('setLearningstate', { learningState : await response.json(), questionId : questionId });

          return response.json();
          break;
        }

        case 204 : {
          console.log('no content');
          return {};
          break;
        }

        default : {
          console.log('learningState failed');
          return {};
        }
      }


    } catch (e) {
      console.log('error in getLearningState');
      console.log(e);
    }
  }

}
