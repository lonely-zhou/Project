<template>
  <el-empty description="无点赞" v-if="emptyMyLikes" />
  <el-table :data="myLikes" v-loading="loading" stripe v-if="!emptyMyLikes">
    <el-table-column type="index" label="序号" />
    <el-table-column prop="title" label="标题" />
    <el-table-column prop="time" label="时间" />
    <el-table-column label="操作">
      <template #default="scope">
        <el-button size="small" @click="toReadNote(scope.row.noteId)">阅读</el-button>
        <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row.noteId)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
  <el-pagination
    style="margin: 2%"
    @current-change="changePage(pagination.page)"
    v-model:current-page="pagination.page"
    layout="prev, pager, next"
    :total="pagination.total"
    :page-size="5"
    hide-on-single-page
    background
  />
</template>
<script lang="ts" setup>
import axios from 'axios';
import { computed, onMounted, reactive, ref } from 'vue';
import Result from '../../api/common';
import router from '../../router';

const myLikes = ref([{ noteId: '', time: '', title: '' }]);
const loading = ref(false);
const pagination = reactive({
  page: 1,
  total: 0,
});

const emptyMyLikes = computed(() => {
  if (myLikes.value.length === 0) return true;
  return false;
});
// 删除点赞笔记
function handleDelete(index: number, noteId: string) {
  let result: Result;
  axios
    .delete(`api/likes/delUserLikeNote?noteId=${noteId}`)
    .then((res) => {
      result = res.data;
    })
    .then(() => {
      if (result.code === 200) myLikes.value.splice(index, 1);
    });
}
// 分页 页码改变
function changePage(pageNum: number) {
  axios.get(`api/collects/selUserNoteCollectList?page=${pageNum}`).then((res) => {
    myLikes.value = res.data.data.records;
  });
}
// 阅读全文
function toReadNote(id: string) {
  const noteId = id;
  router.push({ name: 'ReadNote', query: { noteId } });
}
onMounted(() => {
  let result: Result;
  // 获取点赞笔记数据
  axios
    .get('api/likes/selUserLikeList?page=1')
    .then((res) => {
      loading.value = true;
      result = res.data;
    })
    .then(() => {
      myLikes.value = result.data.records;
      loading.value = false;
      pagination.total = Number(result.msg);
    });
});
</script>
<style scoped></style>
