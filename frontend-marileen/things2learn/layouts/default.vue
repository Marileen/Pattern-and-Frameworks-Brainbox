<template>
  <div>
    <div class="layout--default" data-layout="login">

      <div class="row">
        <div class="col-12 text-center">

          <a href="/" class="menu-item">Start</a>

          <form v-if="!user.isLoggedIn" v-on:submit.prevent="login()">
          <input type="text" placeholder="email" v-model="email"/>
          <input type="text" placeholder="passwort" v-model="password"/>
          <button>login</button>

          <span v-if="user.loginFailed" class="alert alert-danger" role="alert">
              Username oder Passwort nicht korrekt.
            </span>
        </form>

          <form v-else v-on:submit.prevent="logout()">
            <p>Hallo {{ user.firstname }}</p>
            <button>logout</button>
          </form>
        </div>
      </div>
    </div>
    <nuxt/>
  </div>
</template>

<script type="application/javascript">

  import { mapState } from 'vuex';

  export default {
    data() {
      return {
        password: '',
        email: '',
      }
    },

    computed: {
      ...mapState(['user']),
    },

    methods: {
      login () {
          this.$store.dispatch('login', {email : this.email, password : this.password});

      }
    },
    mounted() {
      //user state aus session holen, falls user eingeloggt ist
      if (window.sessionStorage.getItem("user") != null) {
        this.$store.commit( 'setUser', JSON.parse(window.sessionStorage.getItem("user")) );
      }
    }
  }
</script>

<style lang="scss">

  form {

    display: inline-block;
    position: relative;
    
    p {
      color: #ecf7dd;
      font-size: 18px;
      margin-bottom: 10px;
    }

    .alert {
      position: absolute;
      right: 0;
      margin-right: -86%;
      top: -9px;
    }
  }


  .button--green {
    display: inline-block;
    border-radius: 4px;
    border: 1px solid #3b8070;
    color: #3b8070;
    text-decoration: none;
    padding: 10px 30px;
  }

  .button--green:hover {
    color: #fff;
    background-color: #3b8070;
  }

  .button--grey {
    display: inline-block;
    border-radius: 4px;
    border: 1px solid #35495e;
    color: #35495e;
    text-decoration: none;
    padding: 10px 30px;
    margin-left: 15px;
  }

  .button--grey:hover {
    color: #fff;
    background-color: #35495e;
  }

  div[data-layout="login"] {
    //background-color: #41b883;
    background-color: #3b8070;
    padding: 20px 0;
  }

</style>
