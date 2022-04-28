<template>
  <div class="common-layout">
    <el-container>
      <el-header><page-header-vue :detail="detail" /></el-header>
      <el-container>
        <el-aside>
          <el-select v-model="value" :placeholder="placeholder" size="large" @change="selClassificationNote">
            <el-option v-for="(item, index) in options" :key="index" :value="item" />
          </el-select>
        </el-aside>
        <el-container>
          <el-empty description="无笔记" v-show="emptyNote" />
          <el-main class="main" v-loading="loading" v-show="!emptyNote">
            <el-card class="box-card" v-for="(item, index) in noteList" :key="index">
              <template #header>
                <div class="card-header">
                  <span>{{ item.title }}</span>
                  <el-button class="button" type="text" @click="toReadNote(item.id)">详情</el-button>
                </div>
              </template>
              <el-row>
                <el-col :span="24">
                  <div class="articleTabled" @click="toReadNote(item.id)">{{ item.articleTabled }}</div>
                </el-col>
                <el-col :span="24">
                  <div class="noteNav">
                    <!-- 头像 -->
                    <el-col :span="3"><el-avatar :size="40" :src="item.avatarUrl" /></el-col>
                    <!-- 作者 -->
                    <el-col :span="3"> {{ item.name }} </el-col>
                    <!-- 浏览数 -->
                    <el-col :span="3"> <span class="iconfont icon-eye" />{{ item.look }} </el-col>
                    <el-col :span="3"> <span class="iconfont icon-like" />{{ item.likes }} </el-col>
                    <el-col :span="3"> <span class="iconfont icon-star" />{{ item.collection }} </el-col>
                    <el-col :span="3">
                      <el-button type="text" style="color: black" @click="toReportPage(item.id)">
                        <span class="iconfont icon-flag" />
                      </el-button>
                    </el-col>
                    <el-col :span="3">
                      <el-button type="text" style="color: black" @click="shareUrl(item.id)">
                        <span class="iconfont icon-share" />
                      </el-button>
                    </el-col>
                  </div>
                </el-col>
              </el-row>
            </el-card>
          </el-main>
          <el-footer>
            <el-pagination
              style="margin: 2%"
              @current-change="selClassificationNote"
              v-model:current-page="pagination.page"
              layout="prev, pager, next"
              :total="pagination.total"
              :page-size="5"
              hide-on-single-page
              background
            />
          </el-footer>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>
<script lang="ts" setup>
import axios from 'axios';
import { onMounted, reactive, ref } from 'vue';
import { ElMessage } from 'element-plus';
import Result from '../../api/common';
import api from '../../api';
import router from '../../router';
import PageHeaderVue from './PageHeader.vue';

const store = api.store();
const value = ref(store.classification);
const placeholder = ref('请选择');
const options = ref(['生活杂谈', '学习分享', '工作经验']);
const loading = ref(true);
const detail = '分类';
// 剪贴板操作
const clipboardObj = navigator.clipboard;
const pagination = reactive({
  page: 1,
  total: 0,
});
const noteList = ref([
  {
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
  },
]);
const emptyNote = ref(true);
// 分类查询笔记
function selClassificationNote() {
  store.setClassification(value.value);
  loading.value = true;
  let result: Result;
  axios
    .get(`api/note/selClassificationNote?page=${pagination.page}&classification=${value.value}`)
    .then((res) => {
      result = res.data;
    })
    .then(() => {
      noteList.value = result.data.records;
      pagination.total = Number(result.msg);
      loading.value = false;
      if (noteList.value.length !== 0) emptyNote.value = false;
      else emptyNote.value = true;
    });
}
// 跳转阅读全文
function toReadNote(noteId: string) {
  router.push({ name: 'ReadNote', query: { noteId } });
  if (store.isLogin === true) {
    axios.get(`api/looks/insUserLook?noteId=${noteId}`);
  }
}
// 获取分享链接
function shareUrl(noteId: string) {
  try {
    clipboardObj.writeText(`https://note.lonelyzhou.cn/readNote?noteId=${noteId}`);
    ElMessage.success('已复制到剪贴板');
  } catch {
    ElMessage.error('复制失败');
  }
}
// 跳转举报页
function toReportPage(noteId: string) {
  router.push({ name: 'ReportPage', query: { noteId } });
}
onMounted(() => {
  selClassificationNote();
});
</script>
<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.box-card {
  width: 480px;
  margin: 0 12px 12px 0;
}
.articleTabled {
  font-size: 12px;
  text-indent: 2em;
  font-family: 'OPPO Sans';
  height: 96px;
  cursor: pointer;
}
.main {
  display: flex;
  flex-wrap: wrap;
}
.noteNav {
  display: flex;
  align-items: center;
  justify-content: space-around;
}
</style>
