<template>

  <section class="">


    <!--<div class="store-test">Store State Counter: {{ $store.state.counter }}  </div>-->
    <!--<div class="store-test">Courses Length: {{ $store.state.courses.length }}  </div>-->

    <!--<button @click="$store.commit('increment')">-->
      <!--{{ $store.state.counter }}-->
    <!--</button><br>-->

      <div class="component" data-component="courses">
        <nuxt-link :to="`kurs/${course.courseName}`" data-atom="card" v-for="course in $store.state.courses" :key="course.courseName">
          <span>{{ course.courseName }}</span>
        </nuxt-link>
      </div>

  </section>
</template>

<script type="application/javascript">


  import { mapState } from 'vuex'

  export default {

    fetch({ store }) {

    },

    computed: mapState([
      'counter',
      'courses'
    ]),

    data()  {
      return {

      }
    },
    methods : {

    },
    beforeMount(){
      this.$store.dispatch('getCourses');
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

