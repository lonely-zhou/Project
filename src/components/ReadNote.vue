<template>
  <div class="box">
    <div class="noteInfo">
      <h1 class="title">{{ note.title }}</h1>
      <div class="info">
        <span class="iconfont icon-time-circle"> {{ note.create_time }}</span>
        <span class="iconfont icon-comment"> {{ noteCommentCount }}</span>
        <span class="iconfont icon-eye"> {{ note.look }}</span>
        <span class="iconfont icon-like"> {{ note.likes }}</span>
        <span class="iconfont icon-user"> {{ note.name }}</span>
      </div>
    </div>
    <div class="note" v-html="note.text" />
    <div style="margin-top: 40px">
      <el-button type="primary" plain class="iconfont icon-like" v-show="!isLike" @click="addLike">
        &nbsp;点赞
      </el-button>
      <el-button type="primary" plain class="iconfont icon-like" v-show="isLike" @click="addLike"
        >&nbsp;已点赞</el-button
      >
      <el-button type="success" plain class="iconfont icon-star">&nbsp;收藏</el-button>
      <el-button type="warning" plain class="iconfont icon-share" @click="shareUrl">&nbsp;分享</el-button>
      <el-button type="danger" plain class="iconfont icon-flag">&nbsp;举报</el-button>
    </div>
    <div class="insComment">
      <p>发表评论</p>
      <div style="margin: 20px 0"></div>
      <el-input
        v-model="comment.message"
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
</template>
<script lang="ts" setup>
import axios from 'axios';
import { ElMessage, ElNotification } from 'element-plus';
import { onMounted, reactive, ref } from 'vue';
import { useRoute } from 'vue-router';
import cookies from '../api/cookies';
import api from '../api/index';

const route = useRoute();
const store = api.store();
const { noteId } = route.query;
const user = cookies.get('userInfo') as any;
const isLike = ref();
const loginFlag = ref(store.loginFlag);
const clipboardObj = navigator.clipboard;
const noteCommentList = ref();
const total = ref(); // 分页数
const page = ref(1);
const comment = reactive({
  message: '',
  user_id: user.id,
  note_id: noteId,
  time: api.dateFormat.getDateFormatYHD(),
});
const note = ref({
  id: '', // 笔记ID
  title: '', // 标题
  text: '', // 正文
  label_values: '', // 标签
  select_type: '', // 笔记类型
  select_categories: '', // 笔记分类
  select_grade: '', // 笔记等级
  original_author: '', // 原作者
  message: '', // 是否公开
  create_time: '', // 创建时间
  likes: '', // 点赞数
  name: '', // 作者名
  article_tabled: '', // 笔记摘要
  look: '', // 浏览次数
  collection: '', // 收藏数
  user_id: '', // 作者ID
  avatar_url: '', // 头像
});
const noteCommentCount = ref();

function getNoteCommentCount() {
  return axios.get(`api/comment/selNoteCommentCount?noteId=${noteId}`);
}
function getNote() {
  return axios.get(`api/note/selNote?noteId=${noteId}`);
}
function isUserLikeNote() {
  axios.get(`api/likes/isUserLikeNote?userId=${user.id}&noteId=${noteId}`).then((res) => {
    isLike.value = res.data.data;
  });
}
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

function shareUrl() {
  try {
    clipboardObj.writeText(`localhost/readNote?noteId=${noteId}`); // localhost
    // clipboardObj.writeText(`https://www.lonelyzhou.cn/readNote?noteId=${noteId}`);
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
function submitComment() {
  const result = ref();
  if (comment.message === '') {
    ElMessage.error('评论为空');
    return;
  }
  axios
    .post('api/comment/insNoteComment', comment)
    .then((res) => {
      result.value = res.data;
    })
    .then(() => {
      if (result.value.code === 200) {
        ElMessage.success('评论成功');
        selNoteCommentList();
      } else {
        ElMessage.error(result.value.data);
      }
    });
}
function changePage(pageNum: number) {
  axios.get(`api/comment/selNoteCommentList?noteId=${noteId}&page=${pageNum}`);
}
onMounted(() => {
  const result = ref();
  axios.all([getNote(), getNoteCommentCount()]).then((res) => {
    result.value = res[0].data;
    note.value = result.value.data;
    noteCommentCount.value = res[1].data.data;
  });
  isUserLikeNote();
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
.note >>> code {
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
.note >>> img {
  width: 100%;
}
.text {
  margin-bottom: 6px;
}
</style>
