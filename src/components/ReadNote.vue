<template>
  <div class="box">
    <PageHeaderVue :detail="detail" :path="path" />
    <div class="noteInfo">
      <h1 class="title">{{ note.title }}</h1>
      <div class="info">
        <span class="iconfont icon-time-circle"> {{ note.createTime }}</span>
        <span class="iconfont icon-comment"> {{ noteCommentCount }}</span>
        <span class="iconfont icon-eye"> {{ note.look }}</span>
        <span class="iconfont icon-like"> {{ note.likes }}</span>
        <span class="iconfont icon-user"> {{ note.name }}</span>
      </div>
    </div>
    <!-- 笔记正文 -->
    <div class="note" v-html="note.text" v-if="!showEditor" />
    <mavon-editor
      v-model="note.text"
      v-if="showEditor"
      :editable="false"
      :subfield="false"
      defaultOpen="preview"
      :toolbarsFlag="false"
    />
    <div style="margin-top: 40px">
      <el-button type="primary" plain class="iconfont icon-like" v-show="!isLike" @click="addLike">
        &nbsp;点赞
      </el-button>
      <el-button type="primary" plain class="iconfont icon-like" v-show="isLike" @click="addLike">
        &nbsp;已点赞
      </el-button>
      <el-button type="success" plain class="iconfont icon-star" @click="insUserNoteCollect" v-show="!showCollect">
        &nbsp;收藏
      </el-button>
      <el-button type="success" plain class="iconfont icon-star" @click="insUserNoteCollect" v-show="showCollect">
        &nbsp;已收藏
      </el-button>
      <el-button type="warning" plain class="iconfont icon-share" @click="shareUrl">&nbsp;分享</el-button>
      <el-button type="danger" plain class="iconfont icon-flag">&nbsp;举报</el-button>
      <el-button type="info" plain class="iconfont icon-download" @click="download">&nbsp;下载</el-button>
    </div>
    <div class="insComment">
      <p style="margin: 20px 0">发表评论</p>
      <el-input
        v-model="comment.message"
        maxlength="140"
        show-word-limit
        :autosize="{ minRows: 2, maxRows: 4 }"
        type="textarea"
        placeholder="说点什么吧！"
      />
      <el-button type="primary" plain @click="submitComment">确定</el-button>
    </div>
    <div class="showComment" style="margin-top: 40px">
      <p style="margin-bottom: 10px">评论</p>
      <el-row v-for="(item, index) in noteCommentList" :key="index">
        <el-col :span="2"><el-avatar :size="50" :src="item.avatar_url"></el-avatar></el-col>
        <el-col :span="22">
          <el-row>
            <el-col :span="24">{{ item.nickname }}</el-col>
            <el-col :span="24">{{ item.message }}</el-col>
            <el-col :span="24" style="font-size: small">{{ item.time }}</el-col>
          </el-row>
          <el-divider></el-divider>
        </el-col>
      </el-row>
      <!-- 分页 -->
      <el-pagination
        style="margin: 2%"
        @current-change="changePage(page)"
        v-model:current-page="page"
        layout="prev, pager, next"
        :total="total"
        background
        hide-on-single-page
      />
    </div>
  </div>
  <el-backtop />
</template>
<script lang="ts" setup>
import axios from 'axios';
import { ElMessage, ElNotification } from 'element-plus';
import { computed, onMounted, reactive, ref } from 'vue';
import { useRoute } from 'vue-router';
import PageHeaderVue from './PageHeader.vue';
import api from '../api';

const route = useRoute();
const detail = '阅读全文';
const path = 'index';
const store = api.store();
const { noteId } = route.query;
const user = store.user as any;
const isLike = ref();
const showCollect = ref();
const loginFlag = ref(store.isLogin);
const clipboardObj = navigator.clipboard;
const noteCommentList = ref();
const total = ref(); // 分页数
const page = ref(1);

const comment = reactive({
  message: '',
  userId: user === null ? '' : user.id,
  noteId,
  time: api.dateFormat.getDateFormatYHD(),
});
const note = ref({
  id: '', // 笔记ID
  title: '', // 标题
  text: '', // 正文
  labelValues: '', // 标签
  selectType: '', // 笔记类型
  selectCategories: '', // 笔记分类
  selectGrade: '', // 笔记等级
  originalAuthor: '', // 原作者
  message: '', // 是否公开
  createTime: '', // 创建时间
  likes: '', // 点赞数
  name: '', // 作者名
  articleTabled: '', // 笔记摘要
  look: '', // 浏览次数
  collection: '', // 收藏数
  userId: '', // 作者ID
  avatarUrl: '', // 头像
  noteType: '',
});
const noteCommentCount = ref();

const showEditor = computed(() => {
  if (note.value.noteType === 'md') return true;
  return false;
});

// 查询笔记评论数
function getNoteCommentCount() {
  return axios.get(`api/comment/selNoteCommentCount?noteId=${noteId}`);
}
// 通过 noteId 查询笔记
function getNote() {
  return axios.get(`api/note/selNote?noteId=${noteId}`);
}
// 用户是否点赞
function isUserLikeNote() {
  const result = ref();
  axios
    .get(`api/likes/isUserLikeNote?noteId=${noteId}`)
    .then((res) => {
      result.value = res.data.data;
    })
    .then(() => {
      if (result.value === 'true') isLike.value = true;
      else isLike.value = false;
    });
}
// 点赞
function addLike() {
  const result = ref();
  if (loginFlag.value === true) {
    axios.get(`api/likes/addLike?userId=${user.id}&noteId=${noteId}`).then((res) => {
      result.value = res.data;
      isUserLikeNote();
    });
  } else {
    ElNotification({
      title: 'Warning',
      message: '登录才能点赞哦！',
      type: 'warning',
    });
  }
}

// 获取分享链接
function shareUrl() {
  try {
    clipboardObj.writeText(`https://note.lonelyzhou.cn/readNote?noteId=${noteId}`);
    ElMessage.success('已复制到剪贴板');
  } catch {
    ElMessage.error('复制失败');
  }
}
// 查询笔记所有评论
function selNoteCommentList() {
  axios.get(`api/comment/selNoteCommentList?noteId=${noteId}&page=1`).then((res) => {
    noteCommentList.value = res.data.data.records;
    total.value = Number(res.data.msg);
  });
}
// 提交评论
function submitComment() {
  const result = ref();
  if (comment.message === '') {
    ElMessage.error('评论为空');
    return;
  }
  if (!loginFlag.value || loginFlag.value === null) {
    ElMessage.error('未登录');
    return;
  }
  console.log(comment);

  axios
    .post('api/comment/insNoteComment', comment)
    .then((res) => {
      result.value = res.data;
    })
    .then(() => {
      if (result.value.code === 200) {
        ElMessage.success('评论成功');
        selNoteCommentList();
        comment.message = '';
      } else {
        ElMessage.error(result.value.data);
      }
    });
}
// 页码改变
function changePage(pageNum: number) {
  axios.get(`api/comment/selNoteCommentList?noteId=${noteId}&page=${pageNum}`);
}
function isUserNoteCollect() {
  const result = ref();
  axios
    .get(`api/collects/isUserNoteCollect?userId=${user.id}&noteId=${noteId}`)
    .then((res) => {
      result.value = res.data.data;
    })
    .then(() => {
      if (result.value === 'true') showCollect.value = true;
      else showCollect.value = false;
    });
}
// 用户收藏笔记
function insUserNoteCollect() {
  const result = ref();
  if (loginFlag.value) {
    axios
      .get(`api/collects/insUserNoteCollect?userId=${user.id}&noteId=${noteId}`)
      .then((res) => {
        result.value = res.data;
      })
      .then(() => {
        isUserNoteCollect();
      });
  } else {
    ElMessage.error('未登录');
  }
}

function download() {
  let type: string;
  if (note.value.noteType === 'note') type = 'html';
  else type = 'md';
  api.download(note.value.title, note.value.text, type);
}
onMounted(() => {
  const result = ref();
  axios.all([getNote(), getNoteCommentCount()]).then((res) => {
    result.value = res[0].data;
    note.value = result.value.data;
    noteCommentCount.value = res[1].data.data;
  });
  if (loginFlag.value) {
    isUserLikeNote();
  }
  selNoteCommentList();
});
</script>
<style scoped>
.box {
  width: 80%;
  margin: 0 auto;
}
.note,
.showComment,
.insComment {
  text-align: left;
  width: 60%;
  margin: 0 auto;
}
.note:deep() code {
  -webkit-font-smoothing: antialiased;
  font-weight: normal;
  outline: 0;
  box-sizing: border-box;
  color: #abb2bf;
  background: #282c34;
  font-family: 'Source Code Pro', 'DejaVu Sans Mono', 'Ubuntu Mono', 'Anonymous Pro', 'Droid Sans Mono', Menlo, Monaco,
    Consolas, Inconsolata, Courier, monospace, 'PingFang SC', 'Microsoft YaHei', sans-serif;
  display: block;
  line-height: 22px;
  overflow-x: auto;
  white-space: pre;
  border-radius: 4px;
  padding: 8px;
  tab-size: 4;
  font-size: 14px;
  word-wrap: break-word;
  user-select: none;
}
.info span {
  margin-right: 6px;
}
.note:deep() img {
  width: 100%;
}
.text {
  margin-bottom: 6px;
}
</style>
