import Vue from 'vue'
import App from './App.vue'

 import 'bootstrap';
// import 'bootstrap/js/dist/util';

//todo: card collapse geht noch nicht
import '../node_modules/bootstrap/js/dist/collapse';

import style from '../sass/main.scss'

new Vue({
    el: '#app',
    render: h => h(App)
})