<template>

  <section class="">

      <div class="component" data-component="courses">
        <nuxt-link :to="isLoggedIn ? `kurs/${course.courseName}` :  `register`" data-atom="card" v-for="course in $store.state.courses" :key="course.courseName">
          <span>{{ course.courseName }}</span>
        </nuxt-link>
      </div>

  </section>
</template>

<script type="application/javascript">

  /*
   * The Courses Component fetches the Courses via Store
   * and iterates over them to display the courses.
   * A link for each Course is set in condition of logged in user, if not logged in it leads to registration page
   *
   */


  import { mapState } from 'vuex'

  export default {

    fetch({ store }) {
      store.commit('setUser')
    },

    computed: {
      ...mapState([
        'counter',
        'courses',
        'user'
      ]),
      //isLoggedIn : this.$store.state.user.isLoggedIn
    },

    watch : {

      user (newUser, oldUser) {
        //this.handleSlideChange();
        this.isLoggedIn = newUser.isLoggedIn;
      }

    },

    data()  {
      return {
        isLoggedIn : this.$store.state.user.isLoggedIn || false
      }
    },
    methods : {


    },
    beforeMount(){
      this.$store.dispatch('getCourses');

      if (window.sessionStorage.getItem("user") != null) {
        this.isLoggedIn = JSON.parse(window.sessionStorage.getItem("user")).isLoggedIn;
      }
    },

    mounted() {
      console.log('courses mounted');
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
     width: 250px;
     height: 100px;
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

