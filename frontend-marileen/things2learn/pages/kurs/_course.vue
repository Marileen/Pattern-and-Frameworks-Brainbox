<template>

  <section class="container">

    <div class="col-12">

      <h1 class="title row">
        {{ this.$route.params.course }}
      </h1>
    </div>

    <div class="col-12 col-md-3">

        <Topics v-bind:course="this.$route.params.course" v-bind:activeTopic="activeTopic" ></Topics>

      </div>

      <div class="col-12 col-md-9">

        <Questions v-bind:course="this.$route.params.course" v-on:active-topic="onTopicChange"></Questions>

      </div>

  </section>
</template>

<script>

  import Topics from "../../components/Topics";
  import Questions from "../../components/Questions";

  import { mapState } from 'vuex';

  export default {

    components: {Topics, Questions},


    validate : ({course}) =>  ({
      //todo
    }),

    data () {
      return {
        activeTopic : ''
      }
    },

    computed: {
      ...mapState(['questions', 'user']),
    },

    methods : {
      onTopicChange(data) {
        this.activeTopic = data.activeTopic;
      }
    },

    beforeMount(){
      this.$store.dispatch('getTopics', this.$route.params.course );
      this.$store.dispatch('getQuestions', { courseName : this.$route.params.course, user : JSON.parse(window.sessionStorage.getItem("user")) } );
    }

  }

</script>

<style lang="scss">

  .container {
    align-items: flex-start;
  }

</style>
