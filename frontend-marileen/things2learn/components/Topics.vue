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

  div[data-component="courses"] {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
  }


  [data-atom="card"] {
    display: flex;
    align-items: center;
    justify-content: center;
    border-radius: 4px;
    text-decoration: none;
    padding: 10px 30px;
    background-color: #bad3a0;
    /*background-color: #ecf7dd;*/

    width: 250px;
    height: 100px;
    margin: 25px;

    box-shadow: 0px 2px 3px rgba(0,0,0,.1);
    cursor: pointer;

    transition: transform .3s ease-out;
    overflow: hidden;
    position: relative;
    -webkit-backface-visibility: hidden;
    -webkit-font-smoothing: antialiased!important;
    font-smoothing: antialiased!important;
    -webkit-transform: translateZ(.1px) scale(1);
    transform: translateZ(.1px) scale(1);

    &:hover {
      box-shadow: 0 2px 6px rgba(0,0,0,.2);

      transform: scale(1.04) translateZ(.2px);
      -webkit-backface-visibility: hidden;
      backface-visibility: hidden;
    }
  }

  div[data-page="couses"] {

  }

  .container {
    min-height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
    text-align: center;

  }

  .title {
    font-family: 'Quicksand', 'Source Sans Pro', -apple-system, BlinkMacSystemFont,
    'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
    display: block;
    font-weight: 300;
    font-size: 100px;
    color: #35495e;
    letter-spacing: 1px;
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

