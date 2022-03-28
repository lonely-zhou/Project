<template>
  <el-table :data="allRole" stripe v-loading="loading">
    <el-table-column type="index" label="序号" />
    <el-table-column prop="rname" label="角色" />
  </el-table>
</template>
<script lang="ts" setup>
import axios from 'axios';
import { onMounted, ref } from 'vue';
import Result from '../../api/common';

const loading = ref(true);
const allRole = ref();
onMounted(() => {
  let result: Result;
  axios
    .get('/api/role/selAllRole')
    .then((res) => {
      result = res.data;
    })
    .then(() => {
      if (result.code === 200) loading.value = false;
      allRole.value = result.data.records;
    });
});
</script>
<style scoped></style>
