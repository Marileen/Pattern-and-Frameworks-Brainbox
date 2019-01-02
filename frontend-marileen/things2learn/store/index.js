import Vuex from 'vuex'

export const state = () => ({
  counter: 8,
  courses : [],
  topics : [],
  questions : [],
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

    console.log('set ls');
    console.log(question);
  },
  setUser (state, user) {
    state.user = user;

    console.log('set user: ', user);
  }
}

export const actions = {

  //todo
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
        // todo: vuex store can be empty while session storage still has data
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

  async getLearningState({commit}, { userId, questionId, token }) {


    if (userId != undefined && questionId != undefined) {

      try {
        const response = await fetch('http://127.0.0.1:8050/user/' + userId + '/state/question/' + questionId, {
          method: 'GET',
          mode: 'cors',
          headers: {
            'Authorization': token,
            'Content-Type': 'application/json'
          }
        });

        if (response.ok) {

          commit('setLearningstate', { learningState : await response.json(), questionId });

        } else {
          console.log('learningState failed');
        }

      } catch (e) {
        console.log(e);
      }
    }

  },

  async getQuestions ({commit}, courseName, topicName) {

    if (courseName != undefined ) {

      try {
        const response = await fetch('http://127.0.0.1:8050/questions/' + courseName , {
          method: 'GET',
          mode: 'cors',
          headers: {
            // 'Authorization': `bearer ${token}`,
            'Content-Type': 'application/json'
          }
        });

        if (response.ok) {

          commit('setQuestions', await response.json());

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
