<template>

  <section class="container">

    <div>
      <h1 class="title">
        things2learn
      </h1>
      <h2 class="subtitle">
        Lernapp
      </h2>

      <Courses></Courses>

    </div>
  </section>
</template>

<script>

  /*
   * The index page is the starting point
   * Available Courses will be listed
   *
   * @Components: Courses
   */

import Courses from '~/components/Courses.vue'

import { mapState } from 'vuex'

export default {
  components: {
    Courses
  },

}
</script>

<style lang="scss">


.title {
  font-family: 'Quicksand', 'Source Sans Pro', -apple-system, BlinkMacSystemFont,
    'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
  display: block;
  font-weight: 300;
  font-size: 80px;
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
