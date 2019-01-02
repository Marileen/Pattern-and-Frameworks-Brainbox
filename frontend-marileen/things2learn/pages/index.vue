<template>

  <section class="container">

    <!--<div class="row">-->
      <!--<div class="col-4 test">test bootstrap col</div>-->
    <!--</div>-->
    <div>
      <!--<logo/>-->
      <h1 class="title">
        things2learn
      </h1>
      <h2 class="subtitle">
        Lernapp
      </h2>

      <!--<div class="store-test">Store State Counter: {{ $store.state.counter }}  </div>-->

      <!--<button @click="$store.commit('increment')">-->
        <!--{{ $store.state.counter }}-->
      <!--</button><br>-->

      <Courses></Courses>

    </div>
  </section>
</template>

<script>
import Logo from '~/components/Logo.vue'
import Courses from '~/components/Courses.vue'

import { mapState } from 'vuex'

export default {
  components: {
    Logo,
    Courses
  },

  fetch({ store }) {
    store.commit('increment')
  },

  computed: mapState([
    'counter'
  ]),
}
</script>

<style lang="scss">

.container {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: flex-start;
  text-align: center;
}

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
