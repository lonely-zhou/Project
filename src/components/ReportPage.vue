<template>
  <div class="box">
    <div class="reportBox">
      <div class="reportNoteInfo">
        <p class="text">举报信息.</p>
        <el-row>
          <el-col :span="8">
            <span>标题: {{ noteInfo.title }}</span>
          </el-col>
          <el-col :span="8">
            <span>作者: {{ noteInfo.name }}</span>
          </el-col>
          <el-col :span="8">
            <span>分类: {{ noteInfo.selectType }}</span>
          </el-col>
        </el-row>
      </div>
      <el-divider style="width: 90%; margin: 24px auto" content-position="center">〻〻〻</el-divider>
      <p class="text">举报理由.</p>
      <el-form :model="report" @validate="validate">
        <el-form-item
          prop="message"
          :rules="{
            required: true,
            message: '请输入举报理由',
            trigger: 'blur',
          }"
        >
          <el-input
            v-model="report.message"
            :autosize="{ minRows: 2, maxRows: 4 }"
            type="textarea"
            placeholder="说点什么吧！"
          />
        </el-form-item>
        <el-form-item
          prop="email"
          :rules="[
            {
              required: true,
              message: '请输入邮箱',
              trigger: 'blur',
            },
            {
              type: 'email',
              message: '邮箱格式不正确',
              trigger: ['blur', 'change'],
            },
          ]"
        >
          <el-input v-model="report.email" placeholder="输入邮箱" style="margin-top: 20px" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" plain style="width: 100%; margin-top: 40px" @click="insReportNote">提交</el-button>
        </el-form-item>
      </el-form>
      <!-- <el-input
        v-model="report.message"
        :autosize="{ minRows: 2, maxRows: 4 }"
        type="textarea"
        placeholder="说点什么吧！"
      />
      <el-input v-model="report.email" placeholder="输入邮箱" style="margin-top: 20px" />
      <el-button type="primary" plain style="width: 100%; margin-top: 40px" @click="insReportNote">提交</el-button> -->
    </div>
  </div>
</template>
<script lang="ts" setup>
import axios from 'axios';
import { ElMessage } from 'element-plus';
import { onMounted, reactive, ref } from 'vue';
import { useRoute } from 'vue-router';
import Result from '../api/common';

const route = useRoute();
const { noteId } = route.query;
const noteInfo = ref({ name: '', title: '', selectType: '' });
const isMessageTrue = ref(false);
const isEmailTrue = ref(false);
const report = reactive({
  email: '',
  message: '',
  noteId,
});
// 举报笔记
function insReportNote() {
  let result: Result;
  if (isMessageTrue.value && isEmailTrue.value) {
    axios
      .post('api/report-note/insReportNote', report)
      .then((res) => {
        result = res.data;
      })
      .then(() => {
        if (result.code === 200) ElMessage.success('举报成功');
        else ElMessage.error('举报失败');
      });
  } else if (!isMessageTrue.value) ElMessage.error('举报理由为空');
  else if (!isEmailTrue.value) ElMessage.error('邮箱不正确');
}
function validate(prop: string, isValid: boolean) {
  switch (prop) {
    case 'message':
      isMessageTrue.value = isValid;
      break;
    case 'email':
      isEmailTrue.value = isValid;
      break;
    default:
      break;
  }
}
onMounted(() => {
  const result = ref();
  axios.get(`api/note/selNote?noteId=${noteId}`).then((res) => {
    result.value = res.data;
    noteInfo.value = result.value.data;
  });
});
</script>
<style scoped>
.reportBox {
  width: 31.625rem;
  height: 27.5rem;
  background-color: #fff;
  /* 盒子水平垂直居中 --- start*/
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  margin: auto;
  /* 盒子水平垂直居中 --- end*/
  border-radius: 0.1875rem;
  box-shadow: 0 0.0625rem 0.3125rem 0;
}
.text {
  text-align: left;
  margin: 10px 0 10px 10px;
  font-family: 'Courier New', Courier, monospace;
  font-size: large;
}
.reportNoteInfo span {
  margin-right: 20px;
}
</style>
