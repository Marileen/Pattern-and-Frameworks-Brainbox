<template>
  <div>
    <div class="layout--default" data-layout="login">

      <div class="row">
        <div class="col-12 text-center">

          <a href="/" class="menu-item">Start</a>

          <form v-if="!userIsLoggedIn" v-on:submit.prevent="login()">
          <input type="text" placeholder="email" v-model="email"/>
          <input type="text" placeholder="passwort" v-model="password"/>
          <button>login</button>

          <span v-if="loginFailed" class="alert alert-danger" role="alert">
              Username oder Passwort nicht korrekt.
            </span>
        </form>

          <form v-else v-on:submit.prevent="logout()">
            <p>Hallo {{ username }}</p>
            <button>logout</button>
          </form>
        </div>
      </div>
    </div>
    <nuxt/>
  </div>
</template>

<script type="application/javascript">

  export default {
    data() {
      return {
        password: '',
        email: '',
        username: window.sessionStorage.getItem("user") != null ? JSON.parse( window.sessionStorage.getItem("user") ).firstname : '',
        userIsLoggedIn: window.sessionStorage.getItem("user") != null ? true : false,
        loginFailed: false
      }
    },
    methods: {
      async login() {
        try {
          const response = await fetch('http://127.0.0.1:8050/user/login', {
            method: 'POST',
            mode: 'cors',
            headers: {
              // 'Authorization': `bearer ${token}`,
              'Content-Type': 'application/json'
            },
            body: JSON.stringify({
              "email": this.email,
              "password": this.password
            })
          });

          if (response.ok) {

            this.userIsLoggedIn = true;

            var userData = await response.json();

            window.sessionStorage.setItem("user", JSON.stringify(userData) );
            this.username = userData.firstname;

            // response.json().then(function(data) {
            //   console.log(data.firstname);
            //   default.username = data.firstname;
            // });

          } else {
            this.loginFailed = true;
          }

        } catch (e) {
          console.log(e)
        }
      },
      logout() {
        window.sessionStorage.removeItem("user");
        this.userIsLoggedIn = false;
      }
    },
    mounted() {
      //console.log( JSON.parse( window.sessionStorage.getItem("user") ).firstname );
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
