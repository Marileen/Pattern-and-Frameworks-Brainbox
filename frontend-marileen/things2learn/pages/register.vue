<template>

  <div class="container">


  <form class="needs-validation"  v-on:submit.prevent="register()">
    <div class="row">

      <div class="col-md-6 mb-3">

        <label for="firstName">Vorname</label>
        <input type="text" class="form-control" id="firstName" placeholder="" value="" required="">
        <div class="invalid-feedback">
          Valid first name is required.
        </div>
      </div>
      <div class="col-md-6 mb-3">
        <label for="lastName">Nachname</label>
        <input type="text" class="form-control" id="lastName" placeholder="" value="">

      </div>
    </div>


    <div class="mb-3">
      <label for="email">Email  </label>
      <input type="email" class="form-control" id="email" placeholder="you@example.com" required="">
      <div class="invalid-feedback">
        Bitte eine gültige E-Mail-Adresse angeben
      </div>
    </div>

    <div class="mb-3">
      <label for="address">Password</label>
      <input type="password" class="form-control" id="address" placeholder="" required="">
      <div class="invalid-feedback">
        Bitte ein Passwort wählen
      </div>
    </div>

    <button class="btn btn-primary btn-lg btn-block" type="submit"> Registrieren </button>
  </form>

  </div>

</template>

<script>
import Courses from '~/components/Courses.vue'

export default {
  components: {
    Courses
  },

  methods : {
    async register (e) {
      console.log(e.target);

      const response = await fetch('http://127.0.0.1:8050/user/register', {
        method: 'POST',
        mode: 'cors',
        headers: {
        'Content-Type': 'application/json'
      },
        body: JSON.stringify({
          "email": email,
          "password": password
        })
    });

    if (response.ok) {

      var userData = await response.json();

    } else {
      var userData = [];
    }

    }
  }
}
</script>
