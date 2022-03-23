<template>
  <el-empty description="无收藏" v-if="emptyFeedback" />
  <el-table :data="userFeedback" v-loading="loading" stripe v-if="!emptyFeedback">
    <el-table-column type="index" label="序号" />
    <el-table-column prop="text" label="反馈意见" />
    <el-table-column prop="time" label="时间" />
    <el-table-column prop="state" label="处理状态" />
  </el-table>
</template>
<script lang="ts" setup>
import axios from 'axios';
import { computed, onMounted, reactive, ref } from 'vue';

const userFeedback = ref([
  {
    id: '',
    text: '',
    time: '',
    userId: '',
    state: '',
  },
]);
const loading = ref(false);
// 分页
const pagination = reactive({
  page: 1,
  total: 0,
});
// 空状态
const emptyFeedback = computed(() => {
  if (userFeedback.value.length === 0) return true;
  return false;
});

onMounted(() => {
  axios.get('/api/feedback/selAllFeedback?page=1').then((res) => {
    userFeedback.value = res.data.data.records;
    pagination.total = Number(res.data.msg);
  });
});
</script>
<style scoped></style>
