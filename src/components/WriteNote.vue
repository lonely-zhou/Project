<template>
  <el-page-header content="detail" @back="goBack" />
  <div class="box">
    <div class="noteEditor">
      <!-- 工具栏 -->
      <Toolbar :editorId="editorId" style="border-bottom: 1px solid #ccc" />
      <!-- 编辑器 -->
      <Editor :editorId="editorId" :defaultConfig="editorConfig" @onChange="handleChange" style="height: 500px" />
    </div>
  </div>
</template>
<script lang="ts" setup>
import { onBeforeUnmount, reactive } from 'vue';
// eslint-disable-next-line object-curly-newline
import { Editor, Toolbar, getEditor, removeEditor } from '@wangeditor/editor-for-vue';
import router from '../router';

// 定义一个编辑器 id ，要求：全局唯一且不变！！！
const editorId = 'wangeEditor-1';
const note = reactive({
  title: '',
  text: '',
});
// 编辑器配置
const editorConfig = {
  placeholder: '请输入内容...',
  MENU_CONF: {},
};

// 回调函数
const handleChange = (editor: any) => {
  // console.log('change:', JSON.stringify(editor.children));
  note.text = editor.getHtml();
};

// 组件销毁时，及时销毁编辑器
onBeforeUnmount(() => {
  const editor = getEditor(editorId);
  if (editor == null) return;

  // 销毁，并移除 editor
  editor.destroy();
  removeEditor(editorId);
});
function goBack() {
  router.push('/index');
}
</script>
<style scoped>
.box {
  text-align: left;
}
.noteEditor {
  border: 1px solid black;
  width: 740px;
  margin: auto;
}
</style>
<style src="@wangeditor/editor/dist/css/style.css"></style>
