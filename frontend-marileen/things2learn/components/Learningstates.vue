<template>

  <section class="learningState">

    <form>
      <label :for="ls.learningStateID" class="state-item" :class="'color-learningstate' + ++key" v-for="(ls, key) in learningStates" :key="ls.learningStateID">
      <input type="radio" name="stateItem" :id="ls.learningStateID" v-on:change="setNewLearningstate" :checked="question.learningState ? question.learningState.learningStateID == ls.learningStateID : false">
        <span>{{ ls.stateName }}</span>
      </label>
    </form>


  </section>
</template>

<script type="application/javascript">


  import { mapState } from 'vuex';

  export default {

    components : {
    },

    props : {
      question : Object,
      learningStates : Array
    },

    fetch({ store }) {

    },

    computed: {
      ...mapState(['questions', 'user']),
    },

    data()  {
      return {

      }
    },
    methods : {

      async setNewLearningstate (e) {
        console.log('setNewLearningstate');

        try {
          const response = await fetch('http://127.0.0.1:8050/user/' + JSON.parse(window.sessionStorage.getItem("user")).userID + '/state/set', {
            method: 'PUT',
            mode: 'cors',
            headers: {
              'Authorization': 'Bearer ' + JSON.parse(window.sessionStorage.getItem("user")).jsonWebToken,
              'Content-Type': 'application/json'
            },
            body : JSON.stringify(  {
              "user" : {
                "userID" : JSON.parse(window.sessionStorage.getItem("user")).userID
              },

              "question" : {
                "questionID" : this.question.questionID
              },
              "learningState" : {
                "learningStateID" : e.target.id
              }
            })
          });

          switch (response.status) {
            case 201 : {
              console.log('new ls set ok');

              // der neue ls muss hier rangepackt werden:
              //this.question.learningState = this.learningStates[e.target.id];
              var lsIndex = e.target.id - 1; //ID fängt bei 1 an, index bei 0
              console.log('setNewLearningState, this.learningStates[lsIndex]', this.learningStates[lsIndex]);
              this.$store.commit('setQuestion', { question : this.question, ls : this.learningStates[lsIndex]});

              this.$emit('newLearningstate', {
                questions:  this.$store.state.questions
              });

              break;
            }

            case 204 : {
              console.log('no content');
              break;
            }

            default : {
              console.log('set new learningStates failed');

            }
          }


        } catch (e) {
          console.log('error in set new LearningState');
          console.log(e);
        }

      }

    },

    beforeMount(){
      //
    },

    mounted () {
      //
    }

  }

</script>

<style lang="scss">

  form {
    margin: 0 0 0 20px;
    height: 40px;
    padding: 8px 0 0 0;

    label {
      margin: 0;
      padding: 2px 10px;
      border-radius: 15px;
      margin: 0 10px 0 0;


    }
  }



</style>

