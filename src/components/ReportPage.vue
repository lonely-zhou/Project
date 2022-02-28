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
            <span>分类: {{ noteInfo.select_categories }}</span>
          </el-col>
        </el-row>
      </div>
      <el-divider style="width: 90%; margin: 24px auto" content-position="center">〻〻〻</el-divider>
      <p class="text">举报理由.</p>
      <el-input
        v-model="report.text"
        :autosize="{ minRows: 2, maxRows: 4 }"
        type="textarea"
        placeholder="说点什么吧！"
      />
      <el-input v-model="report.email" placeholder="输入邮箱" style="margin-top: 20px" />
      <el-button type="primary" plain style="width: 100%; margin-top: 40px">提交</el-button>
    </div>
  </div>
</template>
<script lang="ts" setup>
import axios from 'axios';
import { onMounted, reactive, ref } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();
const { noteId } = route.query;
const noteInfo = ref({ name: '', title: '', select_categories: '' });
const report = reactive({
  email: '',
  text: '',
  time: '',
});

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
