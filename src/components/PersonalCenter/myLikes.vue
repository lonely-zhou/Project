<template>
  <el-table :data="myLikes" stripe>
    <el-table-column type="index" label="序号" />
    <el-table-column prop="title" label="标题" width="180" />
    <el-table-column prop="time" label="时间" width="180" />
    <el-table-column prop="address" label="Address" />
  </el-table>
</template>
<script lang="ts" setup>
import axios from 'axios';
import { onMounted, ref } from 'vue';
import Result from '../../api/common';

const myLikes = ref([{ noteId: '', time: '', title: '' }]);

onMounted(() => {
  let result: Result;
  axios
    .get('api/likes/selUserLikeList?page=1')
    .then((res) => {
      result = res.data;
      console.log(res);
    })
    .then(() => {
      myLikes.value = result.data.records;
    });
});
</script>
<style scoped></style>
