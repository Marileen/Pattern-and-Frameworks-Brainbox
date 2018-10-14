
<template>
    <div>
        <h1>Hello {{ name }} - {{ age }}!</h1>
        <button v-on:click="incAge()">+1</button>
    </div>
</template>

<script>
    export default {
        props: {
            name: {
                type: String,
                required: true
            }
        },
        data : () => {
            return {
                age : 56
            }
        },
        methods : {
            incAge() {
                this.age+=1;
            }
        }
    }
</script>

<style lang="scss" scoped>
    h1 {
        color: red;
    }
</style>