<template>
  <el-row>
    <el-col :spqn="24">
      <p>更改默认编辑器</p>
      <el-radio-group v-model="userSettings.editorStyle" @change="change(userSettings.editorStyle)">
        <el-radio label="noteEditor">普通编辑器</el-radio>
        <el-radio label="mdEditor">Markdown编辑器</el-radio>
      </el-radio-group>
    </el-col>
    <el-col :span="24">
      <router-link to="admin">
        <el-button type="text" v-if="showAdminButton">网站管理后台</el-button>
      </router-link>
    </el-col>
    <el-col :span="24">
      <el-button type="text" @click="openDialog = true">意见反馈</el-button>
      <el-dialog v-model="openDialog" title="反馈信息">
        <el-input
          v-model="feedbackInfo.text"
          maxlength="140"
          show-word-limit
          :autosize="{ minRows: 4, maxRows: 12 }"
          type="textarea"
          placeholder="说点什么吧！"
        />
        <el-button type="primary" plain @click="submitFeedbackInfo">确定</el-button>
      </el-dialog>
    </el-col>
  </el-row>
</template>
<script lang="ts" setup>
import axios from 'axios';
import { ElMessage } from 'element-plus';
import { computed, onMounted, reactive, ref } from 'vue';
import api from '../../api';

const userSettings = ref({ editorStyle: '' });
const store = api.store();
const openDialog = ref(false);
const feedbackInfo = reactive({
  text: '',
});

const showAdminButton = computed(() => {
  if (store.role === 'admin' || store.role === 'su-admin') return true;
  return false;
});

function selUserSettingsList() {
  axios.get('api/settings/selUserSettingsList').then((res) => {
    userSettings.value = res.data.data;
  });
}
function change(editorText: string) {
  const result = ref();
  axios
    .get(`api/settings/updUserEditorStyle?editor=${editorText}`)
    .then((res) => {
      result.value = res.data;
    })
    .then(() => {
      selUserSettingsList();
      if (result.value.code === 200) ElMessage.success('编辑器已更改');
    });
}
function submitFeedbackInfo() {}
onMounted(() => {
  selUserSettingsList();
});
</script>
<style scoped></style>
