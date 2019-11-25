<template>

  <section class="learningState">

    <form>
      <label :for="question.questionID + '-' + ls.learningStateID" class="state-item" :class="'color-learningstate' + ++key" v-for="(ls, key) in learningStates" :key="ls.learningStateID">
      <input type="radio" name="stateItem" :id="question.questionID + '-' + ls.learningStateID" v-on:change="setNewLearningstate(ls)" :checked="question.learningState ? question.learningState.learningStateID == ls.learningStateID : false">
        <span>{{ ls.stateName }} ({{ lsCount[ls.stateName] }})</span>
      </label>

      <span>ohne Status: {{lsCount['other']}}</span>
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
      lsCount : Object
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

      async setNewLearningstate (ls) {

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

