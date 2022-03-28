<template>
  <el-empty description="无收藏" v-if="emptyFeedback" />
  <el-table :data="userFeedback" v-loading="loading" stripe v-if="!emptyFeedback">
    <el-table-column type="index" label="序号" />
    <el-table-column prop="text" label="反馈意见" />
    <el-table-column prop="time" label="时间" />
    <el-table-column prop="state" label="处理状态" />
    <el-table-column label="操作">
      <template #default="scope">
        <el-button size="small" @click="feedbackDetail(scope.row)">详情</el-button>
        <el-dropdown split-button size="small" @command="changeState">
          <span>更改状态</span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item :command="`${scope.row.id},处理中`">处理中</el-dropdown-item>
              <el-dropdown-item :command="`${scope.row.id},已处理`">已处理</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </template>
    </el-table-column>
  </el-table>
  <el-dialog v-model="dialogVisible" title="详情">
    <el-descriptions :column="1" border>
      <el-descriptions-item label="时间">{{ feedbackDetailInfo.time }}</el-descriptions-item>
      <el-descriptions-item label="反馈意见">{{ feedbackDetailInfo.text }}</el-descriptions-item>
      <el-descriptions-item label="处理状态">{{ feedbackDetailInfo.state }}</el-descriptions-item>
    </el-descriptions>
    <template #footer>
      <span>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-dropdown split-button @command="changeState">
          <span>更改状态</span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item :command="`${feedbackDetailInfo.id},处理中`">处理中</el-dropdown-item>
              <el-dropdown-item :command="`${feedbackDetailInfo.id},已处理`">已处理</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </span>
    </template>
  </el-dialog>
  <!-- 分页 -->
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
</template>
<script lang="ts" setup>
import axios from 'axios';
import { ElMessage } from 'element-plus';
import { computed, onMounted, reactive, ref } from 'vue';
import Result from '../../api/common';

const userFeedback = ref([
  {
    id: '',
    text: '',
    time: '',
    userId: '',
    state: '',
  },
]);
const feedbackDetailInfo = ref({
  id: '',
  text: '',
  time: '',
  userId: '',
  state: '',
});
const loading = ref(true);
const dialogVisible = ref(false);
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

// 分页改变
function changePage(pageNum: number) {
  axios.get(`/api/feedback/selAllFeedback?page=${pageNum}`).then((res) => {
    userFeedback.value = res.data.data.records;
    pagination.total = Number(res.data.msg);
    loading.value = false;
  });
}
// 更改反馈状态
function changeState(state: string) {
  const updFeedback = reactive({
    id: state.split(',')[0],
    state: state.split(',')[1],
  });
  let result: Result;
  axios
    .post('/api/feedback/updUserFeedbackState', updFeedback)
    .then((res) => {
      result = res.data;
    })
    .then(() => {
      if (result.code === 200) ElMessage.success('更新成功');
      changePage(pagination.page);
      dialogVisible.value = false;
    });
}
// 反馈详情
function feedbackDetail(detail: any) {
  dialogVisible.value = true;
  feedbackDetailInfo.value = detail;
}
onMounted(() => {
  axios.get('/api/feedback/selAllFeedback?page=1').then((res) => {
    userFeedback.value = res.data.data.records;
    pagination.total = Number(res.data.msg);
    loading.value = false;
  });
});
</script>
<style scoped></style>
