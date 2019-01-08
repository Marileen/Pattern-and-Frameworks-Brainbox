<template>

  <section class="learningState">

    <form>
      <label :for="ls.learningStateID" class="state-item" v-bind:class="'color-learningstate' + ++key" v-for="(ls, key) in learningStates" :key="ls.learningStateID">
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

      //todo
      async setNewLearningstate (e) {
        console.log('setNewLearningstate');

        try {
          const response = await fetch('http://127.0.0.1:8050/user/' + JSON.parse(window.sessionStorage.getItem("user")).userID + '/state/set', {
            method: 'POST',
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

              //todo - update anzeige der figure bei der question
              //todo store updaten und watchen

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

