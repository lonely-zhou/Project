<template>
  <page-header-vue :detail="detail" />
  <div class="box">
    <div class="mdEditor">
      <div>
        <textarea
          v-model="note.title"
          maxlength=""
          placeholder="请输入标题（建议10字以内）"
          style="overflow-y: hidden; height: 64px"
        />
      </div>
      <mavon-editor v-model="note.text" :toolbars="toolbars" />
      <div class="noteType">
        <el-row>
          <el-col :span="24"><p>选择分类</p></el-col>
          <el-col :span="24">
            <el-radio-group v-model="note.select_type">
              <el-radio-button label="生活杂谈"></el-radio-button>
              <el-radio-button label="学习分享"></el-radio-button>
              <el-radio-button label="工作经验"></el-radio-button>
            </el-radio-group>
          </el-col>
          <el-col :span="24">
            <el-tag
              v-for="tag in note.label"
              :key="tag"
              class="mx-1"
              closable
              :disable-transitions="false"
              @close="handleClose(tag)"
            >
              {{ tag }}
            </el-tag>
            <el-input
              v-if="inputVisible"
              ref="InputRef"
              v-model="inputValue"
              class="ml-1 w-20"
              @keyup.enter="handleInputConfirm"
              @blur="handleInputConfirm"
            >
            </el-input>
            <el-button v-else class="button-new-tag ml-1" @click="showInput">添加标签</el-button>
          </el-col>
          <el-col :span="24">
            <el-radio-group v-model="note.message">
              <el-radio-button label="0">公开</el-radio-button>
              <el-radio-button label="1">私密</el-radio-button>
            </el-radio-group>
          </el-col>
          <el-col :span="24">
            <el-button type="primary" plain @click="updNote">更新</el-button>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>
<script lang="ts" setup>
import { reactive, ref, nextTick } from 'vue';
import { ElMessage, ElInput } from 'element-plus';
import axios from 'axios';
import PageHeaderVue from './PageHeader.vue';
import api from '../api/index';

const store = api.store();
const detail = '写笔记';
const inputValue = ref('');
const inputVisible = ref(false);
const InputRef = ref<InstanceType<typeof ElInput>>();
const userNote = JSON.parse(store.getUserNote);
const note = reactive({
  id: userNote.id,
  title: userNote.title, // 标题
  text: userNote.text, // 正文
  user_id: userNote.user_id, // 作者ID
  name: userNote.name, // 作者
  create_time: api.dateFormat.getDateFormatYHD(), // 创建时间
  message: userNote.message, // 是否公开 0公开
  select_type: userNote.select_type, // 分类
  label_values: userNote.label_values as string, // 标签
  label: [], // 标签
  note_type: 'md',
});
const toolbars = {
  bold: true, // 粗体
  italic: true, // 斜体
  header: true, // 标题
  underline: true, // 下划线
  strikethrough: true, // 中划线
  mark: true, // 标记
  superscript: true, // 上角标
  subscript: true, // 下角标
  quote: true, // 引用
  ol: true, // 有序列表
  ul: true, // 无序列表
  link: true, // 链接
  imagelink: true, // 图片链接
  code: true, // code
  table: true, // 表格
  fullscreen: true, // 全屏编辑
  readmodel: true, // 沉浸式阅读
  htmlcode: true, // 展示html源码
  help: true, // 帮助
  /* 1.3.5 */
  undo: true, // 上一步
  redo: true, // 下一步
  trash: true, // 清空
  // save: true,  保存（触发events中的save事件）
  /* 1.4.2 */
  navigation: true, // 导航目录
  /* 2.1.8 */
  alignleft: true, // 左对齐
  aligncenter: true, // 居中
  alignright: true, // 右对齐
  /* 2.2.1 */
  subfield: true, // 单双栏模式
  preview: true, // 预览
};
function updNote() {
  const result = ref();
  note.label_values = note.label.toString();
  axios
    .post('api/note/updUserNote', note)
    .then((res) => {
      result.value = res.data;
    })
    .then(() => {
      if (result.value.code === 200) ElMessage.success('更新成功');
    });
}
const handleClose = (tag: string) => {
  note.label.splice(note.label.indexOf(tag as never), 1);
};

const showInput = () => {
  inputVisible.value = true;
  nextTick(() => {
    InputRef.value!.input!.focus();
  });
};

const handleInputConfirm = () => {
  if (inputValue.value) {
    note.label.push(inputValue.value as never);
  }
  inputVisible.value = false;
  inputValue.value = '';
};
</script>
<style scoped>
.box {
  text-align: left;
}
.mdEditor {
  width: 960px;
  margin: auto;
}
textarea {
  -webkit-text-size-adjust: 100%;
  -webkit-font-smoothing: antialiased;
  box-sizing: border-box;
  font: inherit;
  overflow: auto;
  margin: 0;
  width: 100%;
  background-color: transparent;
  outline: none;
  resize: none;
  text-align: justify;
  border: 0;
  font-size: 28px;
  padding: 12px 50px 12px 0;
  font-weight: 700;
  line-height: 40px;
  display: block;
  word-break: break-all;
  overflow-y: hidden;
  height: 64px;
}
.noteType {
  width: 40%;
}
.el-col-24 {
  margin-bottom: 2%;
}
.el-radio-group {
  width: 100%;
  justify-content: space-between;
}
</style>
