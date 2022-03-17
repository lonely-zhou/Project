<template>
  <el-radio-group v-model="noteMessage" size="small" @change="changeMessage">
    <el-radio-button label="">全部笔记</el-radio-button>
    <el-radio-button label="0">公开笔记</el-radio-button>
    <el-radio-button label="1">私密笔记</el-radio-button>
  </el-radio-group>
  <el-empty description="无笔记" v-if="emptyMyNotes" />
  <el-table :data="myNotes" v-loading="loading" stripe v-if="!emptyMyNotes">
    <el-table-column type="index" label="序号" />
    <el-table-column prop="title" label="标题" />
    <el-table-column prop="createTime" label="时间" />
    <el-table-column label="操作">
      <template #default="scope">
        <el-button size="small" @click="handleEditor(scope.row)">修改</el-button>
        <!-- <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row.noteId)">删除</el-button> -->
        <el-popconfirm
          title="确定删除该笔记?"
          confirm-button-text="确定"
          cancel-button-text="取消"
          @confirm="handleDelete(scope.$index, scope.row.id)"
        >
          <template #reference>
            <el-button type="danger" size="small">删除</el-button>
          </template>
        </el-popconfirm>
      </template>
    </el-table-column>
  </el-table>
</template>
<script lang="ts" setup>
import axios from 'axios';
import { computed, onMounted, reactive, ref } from 'vue';
import { ElMessage } from 'element-plus';
import Result from '../../api/common';
import api from '../../api';
import router from '../../router';

// 笔记私密状态
const noteMessage = ref('');
const loading = ref(false);
const store = api.store();
const pagination = reactive({
  page: 1,
  total: 0,
});
const myNotes = ref([
  {
    title: '',
    createTime: '',
    look: '',
    likes: '',
    collection: '',
    id: '',
    message: '',
    noteType: '',
  },
]);

const emptyMyNotes = computed(() => {
  if (myNotes.value.length === 0) return true;
  return false;
});

function changeMessage() {
  axios.get(`api/note/selUserNote?&page=1&message=${noteMessage.value}`).then((res) => {
    myNotes.value = res.data.data.records;
    pagination.total = Number(res.data.msg);
  });
}
// 修改笔记
function handleEditor(note: any) {
  store.setUserNote(note);
  if (note.noteType === 'md') router.push('/updUserNoteMd');
  else router.push('/updUserNote');
}
// 删除用户笔记
function handleDelete(index: number, noteId: string) {
  let result: Result;
  axios
    .delete(`api/note/delUserNote?noteId=${noteId}`)
    .then((res) => {
      result = res.data;
    })
    .then(() => {
      if (result.code === 200) myNotes.value.splice(index, 1);
      else ElMessage.error(result.msg);
    });
}
onMounted(() => {
  let result: Result;
  // 获取用户笔记数据
  axios
    .get(`api/note/selUserNote?page=1&message=${noteMessage.value}`)
    .then((res) => {
      loading.value = true;
      result = res.data;
    })
    .then(() => {
      myNotes.value = result.data.records;
      loading.value = false;
      pagination.total = Number(result.msg);
    });
});
</script>
<style scoped>
.box {
  display: flex;
  justify-content: space-around;
}
</style>
