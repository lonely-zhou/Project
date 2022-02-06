<template>
  <el-page-header content="写笔记" @back="goIndex" title="返回" />
  <div class="box">
    <div class="noteEditor">
      <div>
        <textarea
          v-model="note.title"
          maxlength=""
          placeholder="请输入标题（建议10字以内）"
          style="overflow-y: hidden; height: 64px"
        />
        <el-button type="primary" @click="changeEditor">切换至MD编辑器</el-button>
      </div>
      <el-affix target=".noteEditor">
        <!-- 工具栏 -->
        <Toolbar :editorId="editorId" style="border-bottom: 1px solid #ccc" />
      </el-affix>
      <!-- 编辑器 -->
      <Editor :editorId="editorId" :defaultConfig="editorConfig" @onChange="handleChange" style="height: 500px" />
      <div class="noteType">
        <el-row>
          <el-col :span="24"><p>选择分类</p></el-col>
          <el-col :span="24">
            <el-radio-group v-model="note.select_categories">
              <el-radio-button label="生活杂谈"></el-radio-button>
              <el-radio-button label="学习分享"></el-radio-button>
              <el-radio-button label="工作经验"></el-radio-button>
            </el-radio-group>
          </el-col>
          <!-- <el-col :span="24"><p>输入标签</p></el-col> -->
          <el-col :span="24">
            <!-- <el-input v-model="note.label_values" placeholder="标签" /> -->
            <el-tag
              v-for="tag in note.label_values"
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
            <el-button type="primary" plain @click="insNote">保存</el-button>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>
<script lang="ts" setup>
// eslint-disable-next-line object-curly-newline
import { onBeforeUnmount, reactive, ref, nextTick } from 'vue';
// eslint-disable-next-line object-curly-newline
import { Editor, Toolbar, getEditor, removeEditor } from '@wangeditor/editor-for-vue';
import { useCookies } from 'vue3-cookies';
import axios from 'axios';
import { ElMessage, ElInput } from 'element-plus';
import router from '../router';
import api from '../api';

const { cookies } = useCookies();
const userInfo = cookies.get('userInfo') as any;
// 定义一个编辑器 id ，要求：全局唯一且不变！！！
const editorId = 'wangeEditor-1';
const inputValue = ref('');
const inputVisible = ref(false);
const InputRef = ref<InstanceType<typeof ElInput>>();
const note = reactive({
  title: '', // 标题
  text: '', // 正文
  user_id: userInfo.id, // 作者ID
  name: userInfo.username, // 作者
  create_time: api.dateFormat.getDateFormatYHD(), // 创建时间
  message: 0, // 是否公开 0公开
  select_categories: '', // 分类
  label_values: [], // 标签
});
// 编辑器配置
const editorConfig = {
  placeholder: '请输入内容...',
  MENU_CONF: {},
};
const store = api.store();

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
function goIndex() {
  router.push('/index');
}
function insNote() {
  const result = ref();
  if (note.title === '') {
    ElMessage.error('请输入标题');
  } else {
    axios
      .post('api/note/insNote', note, {
        headers: {
          Authorization: store.jwtToken,
        },
      })
      .then((res) => {
        result.value = res.data;
        // console.log(res);
      })
      .then(() => {
        if (result.value.code === 200) {
          ElMessage.success('保存成功');
          router.go(0);
        } else {
          ElMessage.error(result.value.msg);
        }
      });
  }
}
function changeEditor() {
  router.push('/mdEditor');
}

const handleClose = (tag: string) => {
  note.label_values.splice(note.label_values.indexOf(tag as never), 1);
};

const showInput = () => {
  inputVisible.value = true;
  nextTick(() => {
    InputRef.value!.input!.focus();
  });
};

const handleInputConfirm = () => {
  if (inputValue.value) {
    note.label_values.push(inputValue.value as never);
  }
  inputVisible.value = false;
  inputValue.value = '';
};
</script>
<style scoped>
.box {
  text-align: left;
  /* width: 750px;
  height: 1200px;
  border: 1px solid black;
  margin: auto; */
}
.noteEditor {
  width: 740px;
  margin: auto;
  margin-bottom: 10%;
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
<style src="@wangeditor/editor/dist/css/style.css"></style>
