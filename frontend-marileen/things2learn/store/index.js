import Vuex from 'vuex'

export const state = () => ({
  counter: 8,
  courses : [],
  topics : [],
  questions : []
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
  }
}

export const actions = {

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

//
// export const createStore = () => {
//
//   return new Vuex.Store({
//
//     state : () => ({
//       counter : 10
//     }),
//
//     mutations : {
//       increment(state) {
//         state.counter++
//       }
//     },
//
//     modules : {
//       namespaced : true,
//
//       courses: {
//
//         state: () => ({
//           list: []
//         }),
//
//
//         mutations : {
//           // gibt grad keine
//         }
//
//       }
//     }
//   })
//
// }
