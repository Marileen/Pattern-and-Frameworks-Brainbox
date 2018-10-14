
<template>
    <div class="app">
        <hello v-if="userIsLoggedIn"></hello>
        <registration v-else></registration>
    </div>
</template>

<script>

    import Registration from './components/Register.vue';
    import Hello from './components/HelloComponent.vue';

    export default {
        data : () => {
            return {
                userIsLoggedIn : false
            }
        },
        methods : {
            askUserServiceIfUserIsLoggedIn() {
                this.userIsLoogedIn = false;
            }
        },
        components : {
            Registration,
            Hello
        }
    }
</script>

<style lang="scss" scoped>
    //
</style>