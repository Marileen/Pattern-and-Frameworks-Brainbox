<template>

  <div class="container">

  <form class="needs-validation" v-on:submit.prevent="register">
    <div class="row">

      <div class="col-12">
        <h3 class=" ">Registriere dich, um Zugriff auf das Kursmaterial zu erhalten.</h3>
      </div>

      <div class="col-12">
        <div class="alert-danger" v-if="loginFailMail">
          Ein User mit dieser E-Mail Adresse existiert bereits
        </div>

        <div class="alert-danger" v-if="loginFailDefault">
          Etwas ist schief gelaufen
        </div>
      </div>

      <div class="col-md-6 mb-3">

        <label for="firstName">Vorname</label>
        <input type="text" class="form-control" id="firstName" placeholder="" v-model="firstname" required="">
        <div class="invalid-feedback">
          Valid first name is required.
        </div>
      </div>
      <div class="col-md-6 mb-3">
        <label for="lastName">Nachname</label>
        <input type="text" class="form-control" id="lastName" placeholder="" v-model="lastname">

      </div>
    </div>


    <div class="mb-3">
      <label for="email">Email  </label>
      <input type="email" class="form-control" id="email" placeholder="you@example.com" required="" v-model="email">
      <div class="invalid-feedback">
        Bitte eine gültige E-Mail-Adresse angeben
      </div>
    </div>

    <div class="mb-3">
      <label for="address">Password</label>
      <input type="password" class="form-control" id="address" placeholder="" required="" v-model="password">
      <div class="invalid-feedback">
        Bitte ein Passwort wählen
      </div>
    </div>

    <button class="btn btn-primary btn-lg btn-block" type="submit"> Registrieren </button>
  </form>

  </div>

</template>

<script>

  /*
 * The register page handles the registration of new user
 * After successfully registration user is logged in and will be stored in session storage
 *
 * todo: fetch request should be removed from here in the future
 *
 */

import {supportsCrypto, hash, encode64} from '../utils/hashing.js'

export default {

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

<style lang="scss">

  h3 {
    margin-top: 35px;
  }
</style>
