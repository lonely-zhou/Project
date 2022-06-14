<script setup lang="ts">
import footerVue from './components/footer.vue';
import countDownVue from './components/countDown.vue';
</script>

<template>
  <router-view></router-view>
  <footer-vue />
  <count-down-vue />
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
