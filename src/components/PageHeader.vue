<template>
  <el-page-header :content="props.detail" @back="goBack" title="返回" class="box" />
</template>
<script lang="ts" setup>
import { defineProps } from 'vue';
import router from '../router';

const props = defineProps({
  detail: String,
  path: String,
});

function goBack() {
  router.push(`/${props.path}`);
}
</script>
<style scoped>
.box {
  background-color: white;
  position: absolute;
  top: 12px;
  left: 12px;
}
</style>
