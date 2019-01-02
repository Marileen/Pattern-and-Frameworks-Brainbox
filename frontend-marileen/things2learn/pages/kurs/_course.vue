<template>

  <section class="container">

    <div class="col-12 col-md-4">

        <Topics v-bind:course="this.$route.params.course" v-bind:activeTopic="activeTopic" ></Topics>

      </div>

      <div class="col-12 col-md-8">

        <h1 class="title row">
          {{ this.$route.params.course }}
        </h1>

        <Questions v-bind:course="this.$route.params.course" v-on:active-topic="onTopicChange"></Questions>

      </div>

  </section>
</template>

<script>

  import Topics from "../../components/Topics";
  import Questions from "../../components/Questions";

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

    methods : {
      onTopicChange(data) {
        this.activeTopic = data.activeTopic;
      }
    },

    beforeMount(){
      this.$store.dispatch('getTopics', this.$route.params.course );
      this.$store.dispatch('getQuestions', this.$route.params.course );
    }

  }

</script>

<style lang="scss">

  .container {
    align-items: flex-start;
  }

</style>
