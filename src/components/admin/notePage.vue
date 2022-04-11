<template>
  <div class="box">
    <el-table :data="allReportNote" stripe style="width: 100%">
      <el-table-column type="index" label="序号" />
      <el-table-column prop="title" label="标题" />
      <el-table-column label="摘要">
        <template #default="scope">
          <span style="margin-left: 10px">{{ scope.row.articleTabled.slice(0, 25) }}......</span>
        </template>
      </el-table-column>
      <el-table-column prop="time" label="时间" />
      <el-table-column prop="message" label="举报理由" />
      <el-table-column align="right" fixed="right">
        <template #header>
          <el-input v-model="search" size="small" placeholder="搜索笔记" @keydown.enter="searchNote" />
        </template>
        <template #default="scope">
          <el-button size="small" @click="handleInfo(scope.$index, scope.row)">详情</el-button>
          <el-popconfirm
            title="将撤销该笔记的举报状态?"
            confirm-button-text="确定"
            cancel-button-text="取消"
            @confirm="delRepotrNote(scope.$index, scope.row.noteId)"
          >
            <template #reference>
              <el-button size="small" type="warning">撤销</el-button>
            </template>
          </el-popconfirm>
          <el-popconfirm
            title="确定删除该笔记?"
            confirm-button-text="确定"
            cancel-button-text="取消"
            @confirm="handleDelete(scope.row.id)"
          >
            <template #reference>
              <el-button size="small" type="danger">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog v-model="dialogVisible" title="详情" width="30%">
      <el-descriptions :column="1" border>
        <el-descriptions-item label="标题">{{ reportNoteInfo.title }}</el-descriptions-item>
        <el-descriptions-item label="作者">{{ reportNoteInfo.name }}</el-descriptions-item>
        <el-descriptions-item label="时间">{{ reportNoteInfo.time }}</el-descriptions-item>
        <el-descriptions-item label="摘要">{{ reportNoteInfo.articleTabled }}</el-descriptions-item>
        <el-descriptions-item label="举报理由">{{ reportNoteInfo.message }}</el-descriptions-item>
      </el-descriptions>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-popconfirm
            title="确定删除该笔记?"
            confirm-button-text="确定"
            cancel-button-text="取消"
            @confirm="handleDelete(reportNoteInfo.id)"
          >
            <template #reference>
              <el-button type="danger">删除</el-button>
            </template>
          </el-popconfirm>
        </span>
      </template>
    </el-dialog>
  </div>
</template>
<script lang="ts" setup>
import axios from 'axios';
import { ElMessage } from 'element-plus';
import { onMounted, ref } from 'vue';
import Result from '../../api/common';

interface Note {
  id: string;
  articleTabled: string;
  time: string;
  message: string;
  title: string;
}
const allReportNote = ref([]);
const search = ref();
const dialogVisible = ref(false);
const reportNoteInfo = ref();

function handleInfo(index: number, row: Note) {
  dialogVisible.value = true;
  reportNoteInfo.value = row;
  console.log(row);
}
function handleDelete(index: number) {
  axios.get(`api/note/delUserNote?noteId=${index}`);
}
function delRepotrNote(index: number, noteId: string) {
  allReportNote.value.splice(index, 1);
  let result: Result;
  axios
    .delete(`/api/report-note/delReportNote?noteId=${noteId}`)
    .then((res) => {
      result = res.data;
    })
    .then(() => {
      if (result.code === 200) ElMessage.success('撤销成功');
    });
}
function searchNote() {
  let result: Result;
  axios
    .get(`/api/admin/searchNote?page=1&title=${search.value}`)
    .then((res) => {
      result = res.data;
    })
    .then(() => {
      allReportNote.value = result.data.records;
    });
}
onMounted(() => {
  let result: Result;
  axios.get('/api/report-note/selAllReportNote').then((res) => {
    result = res.data;
    allReportNote.value = result.data;
    console.log(result.data);
  });
});
</script>
<style scoped></style>
