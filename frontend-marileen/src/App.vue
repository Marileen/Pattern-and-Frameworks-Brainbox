
<template>
    <div class="app">
        <hello v-if="userIsLoggedIn"></hello>
        <user-menu v-else></user-menu>
    </div>
</template>

<script>

    import UserMenu from './components/user/UserMenu.vue';
    import Hello from './components/HelloComponent.vue';

    export default {
        data : () => {
            return {
                // todo: User State abholen und setzen
                // hier kann man momentan manuell umschalten zum testen
                userIsLoggedIn : false
            }
        },
        methods : {
            askUserServiceIfUserIsLoggedIn() {
                this.userIsLoogedIn = false;
            }
        },
        components : {
            UserMenu,
            Hello
        }
    }
</script>

<style lang="scss" scoped>
    //
</style>