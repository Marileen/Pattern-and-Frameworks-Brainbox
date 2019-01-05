<template>

  <section class="">
      <div class="component" data-component="topics">
        <div data-atom="card" v-for="topic in topics" :key="topic.topicName" :id="topic.topicID" :class="{ active:activeTopic == topic.topicName }">
          <span>{{ topic.topicName }}</span>
        </div>
      </div>

  </section>
</template>

<script type="application/javascript">


  import { mapState } from 'vuex'

  export default {

    props : {
      activeTopic : String
    },

    fetch({ store }) {

    },

    computed: mapState([
      'topics'
    ]),

    data()  {
      return {

      }
    },
    methods : {

    },
    beforeMount(){
      this.$store.dispatch('getTopics');
    }
  }

</script>

<style lang="scss">

  @import "../assets/colors.scss";

  div[data-component="courses"] {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
  }

  [data-atom="card"] {
    color: $color-text-dark;
    background-color: $color-topic;

    &:hover {
      box-shadow: 0px 2px 3px rgba(0,0,0,.1);
      transform: none;
    }

    &:hover.active {
      box-shadow: 0 2px 6px rgba(0,0,0,.2);
      transform: scale(1.04) translateZ(.2px);
    }
  }

  .title {
    font-family: 'Quicksand', 'Source Sans Pro', -apple-system, BlinkMacSystemFont,
    'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
    display: block;
    font-weight: 300;
    font-size: 70px;
    color: #35495e;
    letter-spacing: 1px;
    padding: 30px 0;
  }

  .subtitle {
    font-weight: 300;
    font-size: 42px;
    color: #526488;
    word-spacing: 5px;
    padding-bottom: 15px;
  }

  .links {
    padding-top: 15px;
  }
</style>

