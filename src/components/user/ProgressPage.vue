<template>
  <el-affix> <el-progress :percentage="props.percentage" :show-text="false" :stroke-width="3" /></el-affix>
</template>
<script lang="ts" setup>
import { defineProps } from 'vue';

const props = defineProps({
  percentage: String,
});
</script>
<style scoped></style>
