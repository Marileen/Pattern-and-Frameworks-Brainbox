<template>

  <div class="container">

    <h2>Meine Tochter Jessica wollte wissen wie man programmiert und darf hier eine Testdatei haben</h2>

  <form class="needs-validation" v-on:submit.prevent="register">
    <div class="mb-3">
      <label for="email">Email  </label>
      <input type="text" class="form-control" id="email" placeholder="you@example.com" required="" v-model="email">
    </div>

    <button class="btn btn-primary btn-lg btn-block" type="submit"> Registrieren </button>
  </form>

    <form>
      <input type="text" placeholder="name">
      <input type="text" placeholder="nachname">
      <input type="text" placeholder="hausnummer">
      <input type="text" placeholder="straße">
      <input type="text" placeholder="stadt">
      <input type="text" placeholder="telefonnummer">
      <button class="btn btn-primary btn-lg btn-block" type="submit">senden</button>
    </form>

  </div>

</template>

<script>

import Courses from '~/components/Courses.vue'
import {supportsCrypto, hash, encode64} from '../utils/hashing.js'

export default {
  components: {
    Courses
  },

  data () {
    return {
      firstname : '',
      lastname : '',
      email : '',
      password : '',
      loginFailMail : false,
      loginFailDefault : false
    }
  },

  methods : {

    async register (e) {

      var hashedPassword = null;

      if ( supportsCrypto() ) {
        console.log('encrypt pw');
        hashedPassword = await hash('SHA-256', this.password);
        hashedPassword = await encode64(hashedPassword);
        console.log(hashedPassword);
      }

      const response = await fetch('http://127.0.0.1:8050/user/register', {
        method: 'POST',
        mode: 'cors',
        headers: {
        'Content-Type': 'application/json'
      },
        body: JSON.stringify({
          "firstname" : this.firstname,
          "lastname" : this.lastname,
          "email": this.email,
          "password": hashedPassword || this.password
        })
    });

    if (response.ok) {

      var userData = await response.json();
      userData.isLoggedIn = true;
      this.loginFailMail = false;
      this.loginFailDefault = false;

      //write to Vuex store and Browser Session Store
      window.sessionStorage.setItem("user", JSON.stringify(userData) );
      this.$store.commit( 'setUser', JSON.parse(window.sessionStorage.getItem("user")) );
      this.$nuxt.$router.replace({ path: '/' })

    } else {
      var userData = [];
      this.loginFailMail = true;
    }

    }
  }
}
</script>
