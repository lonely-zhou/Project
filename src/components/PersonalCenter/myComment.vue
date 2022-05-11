<template>
  <el-empty description="无评论" v-if="emptyMyComment" />
  <el-table :data="myComments" v-loading="loading" stripe v-if="!emptyMyComment">
    <el-table-column type="index" label="序号" />
    <el-table-column prop="title" label="笔记标题" />
    <el-table-column prop="time" label="时间" />
    <el-table-column prop="message" label="评论内容" />
    <el-table-column label="操作">
      <template #default="scope">
        <el-button size="small" @click="toReadNote(scope.row.noteId)">阅读</el-button>
        <el-button size="small" type="info" @click="handleEditor(scope.row.noteId, scope.row.message)">
          修改
        </el-button>
        <el-popconfirm
          title="确定删除该笔记评论?"
          confirm-button-text="确定"
          cancel-button-text="取消"
          @confirm="handleDelete(scope.$index, scope.row.noteId)"
        >
          <template #reference>
            <el-button type="danger" size="small">删除</el-button>
          </template>
        </el-popconfirm>
      </template>
    </el-table-column>
  </el-table>
  <el-dialog v-model="dialogVisible" title="修改评论">
    <el-input
      v-model="updComment.message"
      maxlength="140"
      show-word-limit
      :autosize="{ minRows: 2, maxRows: 4 }"
      type="textarea"
      :placeholder="updComment.message"
    />
    <template #footer>
      <span>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitUpdComment">确定</el-button>
      </span>
    </template>
  </el-dialog>
</template>
<script lang="ts" setup>
import axios from 'axios';
import { ElMessage } from 'element-plus';
import { computed, onMounted, reactive, ref } from 'vue';
import Result from '../../api/common';
import router from '../../router';

const loading = ref(false);
const dialogVisible = ref(false);
const myComments = ref([
  {
    id: '',
    title: '',
    time: '',
    message: '',
    noteId: '',
  },
]);
const pagination = reactive({
  page: 1,
  total: 0,
});
const updComment = ref({
  noteId: '',
  message: '',
});

const emptyMyComment = computed(() => {
  if (myComments.value.length === 0) return true;
  return false;
});

// 分页 页码改变
function changePage(pageNum: number) {
  axios.get(`api/comment/selUserCommentList?page=${pageNum}`).then((res) => {
    myComments.value = res.data.data.records;
  });
}
// 修改评论
function handleEditor(id: string, commentInfo: any) {
  dialogVisible.value = true;
  updComment.value.noteId = id;
  updComment.value.message = commentInfo;
}
// 提交修改评论
function submitUpdComment() {
  axios.post('api/comment/updNoteComment', updComment.value).then((res) => {
    if (res.data.code === 200) ElMessage.success('评论修改成功');
    else ElMessage.error(res.data.msg);
    changePage(pagination.page);
    dialogVisible.value = false;
  });
}
// 删除评论
function handleDelete(index: number, id: string) {
  let result: Result;
  axios
    .delete(`api/comment/delNoteComment?id=${id}`)
    .then((res) => {
      result = res.data;
    })
    .then(() => {
      if (result.code === 200) myComments.value.splice(index, 1);
      else ElMessage.error(result.msg);
    });
}
function toReadNote(id: string) {
  const noteId = id;
  router.push({ name: 'ReadNote', query: { noteId } });
}
onMounted(() => {
  let result: Result;
  // 获取用户评论数据
  axios
    .get('api/comment/selUserCommentList?page=1')
    .then((res) => {
      loading.value = true;
      result = res.data;
    })
    .then(() => {
      myComments.value = result.data.records;
      loading.value = false;
      pagination.total = Number(result.msg);
    });
});
</script>
<style scoped></style>
