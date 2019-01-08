<template>

  <section class="">


    <!--<div class="store-test">Store State Counter: {{ $store.state.counter }}  </div>-->
    <!--<div class="store-test">Courses Length: {{ $store.state.courses.length }}  </div>-->

    <!--<button @click="$store.commit('increment')">-->
      <!--{{ $store.state.counter }}-->
    <!--</button><br>-->

      <div class="component" data-component="courses">
        <a :href="isLoggedIn ? `kurs/${course.courseName}` : '/register'" data-atom="card" v-for="course in $store.state.courses" :key="course.courseName">
          <span>{{ course.courseName }}</span>
        </a>
      </div>

  </section>
</template>

<script type="application/javascript">


  import { mapState } from 'vuex'

  export default {

    fetch({ store }) {

    },

    computed: {
      ...mapState([
        'counter',
        'courses',
        'user'
      ]),
      //isLoggedIn : this.$store.state.user.isLoggedIn
    },

    data()  {
      return {
        isLoggedIn : false
      }
    },
    methods : {

      showCourse (e) {
        //console.log('ddd course');

        //this.$nuxt.$router.replace({ path: 'kurs/' + e.target.attr('href') })
        //:to="`kurs/${course.courseName}`"
      }

    },
    beforeMount(){
      this.$store.dispatch('getCourses');

      if (window.sessionStorage.getItem("user") != null) {
        this.isLoggedIn = JSON.parse(window.sessionStorage.getItem("user")).isLoggedIn;
      }
    },

    mounted() {
      console.log('courses mounted');
      console.log(this.$store.state.user.isLoggedIn)
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

