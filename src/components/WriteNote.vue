<template>
  <div class="box">
    <mavon-editor v-model="value" />
  </div>
</template>
<script lang="ts" setup>
import { ref } from 'vue';

const value = ref();
</script>
<style scoped>
</style>
