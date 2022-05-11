<template>
  <el-empty description="无收藏" v-if="emptyMyCollect" />
  <el-table :data="myCollects" v-loading="loading" stripe v-if="!emptyMyCollect">
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

const loading = ref(false);
const myCollects = ref([{ noteId: '', time: '', title: '' }]);
const pagination = reactive({
  page: 1,
  total: 0,
});
// 空状态
const emptyMyCollect = computed(() => {
  if (myCollects.value.length === 0) return true;
  return false;
});
// 分页改变
function changePage(pageNum: number) {
  axios.get(`api/collects/selUserNoteCollectList?page=${pageNum}`).then((res) => {
    myCollects.value = res.data.data.records;
    pagination.total = Number(res.data.msg);
  });
}
// 删除收藏笔记
function handleDelete(index: number, noteId: string) {
  let result: Result;
  axios
    .delete(`api/collects/delUserNoteCollect?noteId=${noteId}`)
    .then((res) => {
      result = res.data;
    })
    .then(() => {
      if (result.code === 200) myCollects.value.splice(index, 1);
    });
}
// 阅读全文
function toReadNote(id: string) {
  const noteId = id;
  router.push({ name: 'ReadNote', query: { noteId } });
}
onMounted(() => {
  axios.get('api/collects/selUserNoteCollectList?page=1').then((res) => {
    myCollects.value = res.data.data.records;
    pagination.total = Number(res.data.msg);
  });
});
</script>
<style scoped></style>
