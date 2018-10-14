
<script>
    export default {
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