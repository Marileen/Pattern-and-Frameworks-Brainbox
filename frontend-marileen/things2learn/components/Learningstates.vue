<template>

  <section class="learningState">

    <form>
      <label :for="ls.learningStateID" class="state-item" :class="'color-learningstate' + ++key" v-for="(ls, key) in learningStates" :key="ls.learningStateID">
      <input type="radio" name="stateItem" :id="ls.learningStateID" v-on:change="setNewLearningstate" :checked="question.learningState ? question.learningState.learningStateID == ls.learningStateID : false">
        <span>{{ ls.stateName }} ({{ ls.count }})</span>
      </label>

      <span>ohne Status: {{questionsWithoutState}}</span>
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
      learningStates : Array,         //the (three) learningstates
      questionsWithoutState : Number
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

        var lsIndex = e.target.id - 1; //ID fängt bei 1 an, index bei 0
        var ls = this.learningStates[lsIndex];

        await this.$store.dispatch('setNewLearningstate', {ls : ls, question : this.question});

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

