<template>
    <div class="full-width center-content">
        <hello-component name="mari" />
    </div>
</template>

<script>
    import HelloComponent from './components/HelloComponent.vue'
    export default {
        components: {
            HelloComponent
        }
    }
</script>