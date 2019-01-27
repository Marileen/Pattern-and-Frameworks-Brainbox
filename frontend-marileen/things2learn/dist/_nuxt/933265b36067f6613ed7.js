(window.webpackJsonp=window.webpackJsonp||[]).push([[3],{167:function(t,e,n){var s=n(169);"string"==typeof s&&(s=[[t.i,s,""]]),s.locals&&(t.exports=s.locals);(0,n(13).default)("03f326e4",s,!0,{})},168:function(t,e,n){"use strict";var s=n(167);n.n(s).a},169:function(t,e,n){(t.exports=n(12)(!1)).push([t.i,"\n.color-learningstate1{background-color:#81f4ab\n}\n.color-learningstate2{background-color:#f4b981\n}\n.color-learningstate3{background-color:#b15e6c\n}\ndiv[data-component=courses]{display:flex;flex-wrap:wrap;justify-content:center\n}\n[data-atom=card]{width:250px;height:100px\n}\n.title{font-family:Quicksand,Source Sans Pro,-apple-system,BlinkMacSystemFont,Segoe UI,Roboto,Helvetica Neue,Arial,sans-serif;display:block;font-weight:300;font-size:100px;color:#35495e;letter-spacing:1px\n}\n.subtitle{font-weight:300;font-size:42px;color:#526488;word-spacing:5px;padding-bottom:15px\n}\n.links{padding-top:15px\n}",""])},176:function(t,e,n){"use strict";var s=n(20),o=n.n(s),a=n(33),r={fetch:function(t){t.store.commit("setUser")},computed:o()({},Object(a.b)(["counter","courses","user"])),watch:{user:function(t,e){this.isLoggedIn=t.isLoggedIn}},data:function(){return{isLoggedIn:this.$store.state.user.isLoggedIn||!1}},methods:{},beforeMount:function(){this.$store.dispatch("getCourses"),null!=window.sessionStorage.getItem("user")&&(this.isLoggedIn=JSON.parse(window.sessionStorage.getItem("user")).isLoggedIn)},mounted:function(){console.log("courses mounted")}},i=(n(168),n(25)),l=Object(i.a)(r,function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("section",{},[n("div",{staticClass:"component",attrs:{"data-component":"courses"}},t._l(t.$store.state.courses,function(e){return n("nuxt-link",{key:e.courseName,attrs:{to:t.isLoggedIn?"kurs/"+e.courseName:"register","data-atom":"card"}},[n("span",[t._v(t._s(e.courseName))])])}),1)])},[],!1,null,null,null);l.options.__file="Courses.vue";e.a=l.exports},194:function(t,e,n){"use strict";n.r(e);n(54),n(32);var s=n(1),o=n.n(s),a=n(176),r=n(52),i={components:{Courses:a.a},data:function(){return{firstname:"",lastname:"",email:"",password:"",loginFailMail:!1,loginFailDefault:!1}},methods:{register:function(){var t=o()(regeneratorRuntime.mark(function t(e){var n,s,o;return regeneratorRuntime.wrap(function(t){for(;;)switch(t.prev=t.next){case 0:if(n=null,!Object(r.c)()){t.next=10;break}return console.log("encrypt pw"),t.next=5,Object(r.b)("SHA-256",this.password);case 5:return n=t.sent,t.next=8,Object(r.a)(n);case 8:n=t.sent,console.log(n);case 10:return t.next=12,fetch("http://127.0.0.1:8050/user/register",{method:"POST",mode:"cors",headers:{"Content-Type":"application/json"},body:JSON.stringify({firstname:this.firstname,lastname:this.lastname,email:this.email,password:n||this.password})});case 12:if(!(s=t.sent).ok){t.next=25;break}return t.next=16,s.json();case 16:(o=t.sent).isLoggedIn=!0,this.loginFailMail=!1,this.loginFailDefault=!1,window.sessionStorage.setItem("user",JSON.stringify(o)),this.$store.commit("setUser",JSON.parse(window.sessionStorage.getItem("user"))),this.$nuxt.$router.replace({path:"/"}),t.next=27;break;case 25:o=[],this.loginFailMail=!0;case 27:case"end":return t.stop()}},t,this)}));return function(e){return t.apply(this,arguments)}}()}},l=n(25),c=Object(l.a)(i,function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"container"},[n("h2",[t._v("Meine Tochter Jessica wollte wissen wie man programmiert und darf hier eine Testdatei haben")]),t._v(" "),n("form",{staticClass:"needs-validation",on:{submit:function(e){return e.preventDefault(),t.register(e)}}},[n("div",{staticClass:"mb-3"},[n("label",{attrs:{for:"email"}},[t._v("Email  ")]),t._v(" "),n("input",{directives:[{name:"model",rawName:"v-model",value:t.email,expression:"email"}],staticClass:"form-control",attrs:{type:"text",id:"email",placeholder:"you@example.com",required:""},domProps:{value:t.email},on:{input:function(e){e.target.composing||(t.email=e.target.value)}}})]),t._v(" "),n("button",{staticClass:"btn btn-primary btn-lg btn-block",attrs:{type:"submit"}},[t._v(" Registrieren ")])]),t._v(" "),t._m(0)])},[function(){var t=this.$createElement,e=this._self._c||t;return e("form",[e("input",{attrs:{type:"text",placeholder:"name"}}),this._v(" "),e("input",{attrs:{type:"text",placeholder:"nachname"}}),this._v(" "),e("input",{attrs:{type:"text",placeholder:"hausnummer"}}),this._v(" "),e("input",{attrs:{type:"text",placeholder:"straße"}}),this._v(" "),e("input",{attrs:{type:"text",placeholder:"stadt"}}),this._v(" "),e("input",{attrs:{type:"text",placeholder:"telefonnummer"}}),this._v(" "),e("button",{staticClass:"btn btn-primary btn-lg btn-block",attrs:{type:"submit"}},[this._v("senden")])])}],!1,null,null,null);c.options.__file="jessi.vue";e.default=c.exports}}]);