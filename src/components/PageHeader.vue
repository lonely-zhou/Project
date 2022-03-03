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
  if (props.path === 'undefined') router.push(`/${props.path}`);
  else router.go(-1);
}
</script>
<style scoped>
.box {
  position: absolute;
  top: 12px;
  left: 12px;
}
</style>
