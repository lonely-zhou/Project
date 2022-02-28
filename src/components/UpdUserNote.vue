<template>
  <div class="box">
    <div class="noteEditor">
      <div>
        <textarea
          v-model="note.title"
          maxlength=""
          placeholder="请输入标题（建议10字以内）"
          style="overflow-y: hidden; height: 64px"
        />
      </div>
      <div ref="editor" />
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
// eslint-disable-next-line object-curly-newline
import { onMounted, onBeforeUnmount, ref, reactive, nextTick } from 'vue';
import WangEditor from 'wangeditor';
// import { useCookies } from 'vue3-cookies';
import { ElInput, ElMessage } from 'element-plus';
import axios from 'axios';
// import router from '../router';
import api from '../api/index';

const editor = ref();
const store = api.store();
// const { cookies } = useCookies();
// const userInfo = cookies.get('userInfo') as any;
const inputValue = ref('');
const inputVisible = ref(false);
const InputRef = ref<InstanceType<typeof ElInput>>();
const userNote = JSON.parse(store.getUserNote);
const note = reactive({
  id: userNote.id,
  title: userNote.title, // 标题
  text: '', // 正文
  user_id: userNote.user_id, // 作者ID
  name: userNote.name, // 作者
  create_time: api.dateFormat.getDateFormatYHD(), // 创建时间
  message: userNote.message, // 是否公开 0公开
  select_categories: userNote.select_categories, // 分类
  label_values: userNote.label_values as string, // 标签
  label: [], // 标签
});
if (userNote.label_values !== '' && userNote.label_values !== undefined) {
  const labelList = note.label_values.split(',');
  for (let index = 0; index < labelList.length; index += 1) {
    note.label.push(labelList[index] as never);
  }
}
let instance: any;
onMounted(() => {
  instance = new WangEditor(editor.value);
  Object.assign(instance.config, {
    onchange() {
      console.log('change');
    },
  });
  instance.config.onchange = (newHtml: any) => {
    note.text = newHtml;
  };
  instance.config.height = 400;
  instance.create();
  instance.txt.html(userNote.text);
});

onBeforeUnmount(() => {
  instance.destroy();
  instance = null;
});
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
</script>
<style scoped>
.box {
  text-align: left;
}
.noteEditor {
  /* width: 1020px; */
  width: 80%;
  margin: auto;
  padding-top: 5%;
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
  width: 30%;
}

.el-col-24 {
  margin-bottom: 2%;
}
.el-radio-group {
  width: 100%;
  justify-content: space-between;
}
</style>
