<template>
  <el-empty description="无反馈" v-if="emptyMyFeedback" />
  <el-table :data="myFeedback" v-loading="loading" stripe v-if="!emptyMyFeedback">
    <el-table-column type="index" label="序号" />
    <el-table-column prop="text" label="反馈内容" />
    <el-table-column prop="time" label="时间" />
    <el-table-column prop="state" label="状态" />
    <el-table-column label="操作">
      <template #default="scope">
        <el-popconfirm
          title="将取消该反馈?"
          confirm-button-text="确定"
          cancel-button-text="取消"
          @confirm="handleCancel(scope.$index, scope.row.id)"
        >
          <template #reference>
            <el-button size="small" type="warning">取消</el-button>
          </template>
        </el-popconfirm>
        <el-button size="small" type="success" @click="handleInfo(scope.$index, scope.row)">修改</el-button>
        <el-popconfirm
          title="将删除该反馈?"
          confirm-button-text="确定"
          cancel-button-text="取消"
          @confirm="handleDelete(scope.$index, scope.row.id)"
        >
          <template #reference>
            <el-button size="small" type="danger">删除</el-button>
          </template>
        </el-popconfirm>
      </template>
    </el-table-column>
  </el-table>
  <el-pagination
    style="margin: 2%"
    @current-change="changePage(pagination.page)"
    v-model:current-page="pagination.page"
    layout="prev, pager, next"
    :total="pagination.total"
    :page-size="9"
    hide-on-single-page
    background
  />
  <!-- 修改反馈信息 -->
  <el-dialog v-model="dialogVisible" title="反馈详情">
    <el-input
      v-model="updFeedback.text"
      maxlength="140"
      show-word-limit
      :autosize="{ minRows: 4, maxRows: 12 }"
      type="textarea"
      placeholder="说点什么吧！"
    />
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="updUserFeedback">确定</el-button>
      </span>
    </template>
  </el-dialog>
</template>
<script lang="ts" setup>
import axios from 'axios';
import { ElMessage } from 'element-plus';
import { computed, onMounted, reactive, ref } from 'vue';
import Result from '../../api/common';

const myFeedback = ref([
  {
    id: '',
    state: '',
    text: '',
    time: '',
    userId: '',
  },
]);
const updFeedback = ref({ id: '', state: '', text: '' });
const dialogVisible = ref(false);
const loading = ref(false);
const pagination = reactive({
  page: 1,
  total: 0,
});

// 空状态
const emptyMyFeedback = computed(() => {
  if (myFeedback.value.length === 0) return true;
  return false;
});

function changePage(page: number) {
  let result: Result;
  axios
    .get(`api/feedback/selUserFeedback?page=${page}`)
    .then((res) => {
      result = res.data;
    })
    .then(() => {
      myFeedback.value = result.data.records;
    });
}
// 取消反馈
function handleCancel(index: number, id: string) {
  let result: Result;
  updFeedback.value.id = id;
  updFeedback.value.state = '已取消';
  axios
    .post('api/feedback/updUserFeedbackState', updFeedback.value)
    .then((res) => {
      result = res.data;
    })
    .then(() => {
      if (result.code === 200) {
        ElMessage.success('取消成功');
        changePage(pagination.page);
      } else ElMessage.warning(result.msg);
    });
}
// 删除反馈
function handleDelete(index: number, id: string) {
  let result: Result;
  axios
    .delete(`api/feedback/delUserFeedback?id=${id}`)
    .then((res) => {
      result = res.data;
    })
    .then(() => {
      if (result.code === 200) ElMessage.success('删除成功');
      myFeedback.value.splice(index, 1);
    });
}
// 修改反馈
function handleInfo(index: number, feedback: any) {
  dialogVisible.value = true;
  updFeedback.value.text = feedback.text;
  updFeedback.value.id = feedback.id;
}
function updUserFeedback() {
  let result: Result;
  axios
    .post('api/feedback/updUserFeedbackState', updFeedback.value)
    .then((res) => {
      result = res.data;
    })
    .then(() => {
      if (result.code === 200) {
        ElMessage.success('更新成功');
        changePage(pagination.page);
      }
    });
}
onMounted(() => {
  changePage(pagination.page);
});
</script>
<style scoped></style>
