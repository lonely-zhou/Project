<template>
  <div class="box">
    <img src="https://img.shields.io/badge/version-v1.0-orange" alt="版本" />
    <img :src="`https://img.shields.io/badge/©${year}-挽风°岛-critical`" />
    <a target="_blank" href="https://beian.miit.gov.cn">
      <img src="https://img.shields.io/badge/辽ICP备-2021007788号-blue" />
    </a>
    <a target="_blank" href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=21142102000082">
      <img
        :src="`https://img.shields.io/badge/辽公网安备-21142102000082号-blue?logo=data:image/png;base64,${BAIcon}`"
      />
    </a>
  </div>
</template>
<script lang="ts" setup>
import { ref } from 'vue';
import axios from 'axios';

const year = new Date().getFullYear();

const BAIcon = ref();
axios
  .get('api/user/selBaIcon')
  .then((res) => {
    BAIcon.value = res.data;
  })
  .then(() => {});
</script>
<style scoped>
.box {
  width: 100%;
  position: fixed;
  bottom: 0;
  user-select: none;
}
img {
  margin-right: 1rem;
}
</style>
