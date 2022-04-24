<template>
  <div class="oneWord">{{ result.data.content }}</div>
</template>
<script lang="ts" setup>
import axios from 'axios';
import { ref } from 'vue';

const result = ref({
  code: 200,
  data: {
    id: '',
    tag: '',
    origin: '',
    content: '',
    datetime: '',
  },
});
axios
  .get('https://api.xygeng.cn/one')
  .then((res) => {
    result.value = res.data;
  })
  .then(() => {});
</script>
<style scoped>
.oneWord {
  color: black;
  font-size: 18px;
  opacity: 0.1;
  font-style: italic;
  writing-mode: vertical-lr;
  position: absolute;
  top: 10%;
  left: 30px;
  font-family: '楷体';
}
</style>
