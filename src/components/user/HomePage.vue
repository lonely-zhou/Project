<template>
  <one-word-vue />
  <div class="box">
    <div class="bacImg">
      <el-affix>
        <div class="navTop">
          <el-row>
            <el-col :span="3" :xs="8"><span class="text" @click="toClassification('生活杂谈')">生活杂谈</span></el-col>
            <el-col :span="3" :xs="8"><span class="text" @click="toClassification('学习分享')">学习分享</span></el-col>
            <el-col :span="3" :xs="8"><span class="text" @click="toClassification('工作经验')">工作经验</span></el-col>
            <!-- 当前日期 城市 天气 温度 -->
            <el-col :span="10" :xs="24">
              <a target="_black" href="https://www.qweather.com/">
                <span class="text">
                  {{ dawDate.dateNow }}
                  {{ dawDate.weather.city }}
                  {{ dawDate.weather.text }}
                  {{ dawDate.weather.temp }} ℃
                </span>
              </a>
            </el-col>
            <el-col :span="3" :xs="12" @click="toWrite">
              <span class="text">写笔记</span>
            </el-col>
            <el-col :span="2" :xs="12">
              <!-- 头像 -->
              <el-dropdown @command="handleCommand">
                <el-avatar :size="50" :src="avatarUrl" v-if="showAvatar"></el-avatar>
                <el-avatar :size="50" v-if="!showAvatar">登录</el-avatar>
                <template #dropdown>
                  <el-dropdown-menu>
                    <el-dropdown-item command="toLogin" v-if="showLogin"><span>去登录</span></el-dropdown-item>
                    <el-dropdown-item v-if="showUserCenter" command="toPersonalCenter">
                      <span>个人中心</span>
                    </el-dropdown-item>
                    <el-dropdown-item command="toLogout" v-if="showUserCenter"><span>退出登录</span></el-dropdown-item>
                  </el-dropdown-menu>
                </template>
              </el-dropdown>
            </el-col>
          </el-row>
        </div>
      </el-affix>
    </div>
    <div class="main">
      <el-row>
        <el-col :span="18">
          <!-- 空状态 -->
          <el-empty v-if="showEmptyNote">
            <el-button type="primary" @click="router.go(0)">刷新</el-button>
          </el-empty>
          <div v-for="(item, index) in noteList" :key="index">
            <div class="noteList" v-if="showNoteList">
              <el-row>
                <el-col :span="24">
                  <div class="noteText" @click="toReadNote(item.id)">
                    <div class="noteTextTitle">{{ item.title }}</div>
                    <div class="noteTextSyn">{{ item.articleTabled }}</div>
                  </div>
                </el-col>
                <el-col :span="24">
                  <div class="noteNav">
                    <el-row>
                      <!-- 头像 -->
                      <el-col :span="3"><el-avatar :size="40" :src="item.avatarUrl" /></el-col>
                      <!-- 作者 -->
                      <el-col :span="3"> {{ item.name }} </el-col>
                      <!-- 浏览数 -->
                      <el-col :span="3"> <span class="iconfont icon-eye" />{{ item.look }} </el-col>
                      <!-- 点赞数 -->
                      <el-col :span="3">
                        <el-tooltip effect="light" content="点赞" show-after="3000" placement="bottom">
                          <el-button type="text" style="color: black" @click="insLike(item.id, index)">
                            <span class="iconfont icon-like" :id="`like${item.id}`" />
                            {{ isUserLikeNote(item.id) }}
                          </el-button>
                        </el-tooltip>
                        {{ item.likes }}
                      </el-col>
                      <!-- 收藏数 -->
                      <el-col :span="3">
                        <el-tooltip effect="light" content="收藏" show-after="3000" placement="bottom">
                          <el-button type="text" style="color: black" @click="insUserCollect(item.id, index)">
                            <span class="iconfont icon-star" :id="`collect${item.id}`" />
                            {{ isUserNoteCollect(item.id) }}
                          </el-button>
                        </el-tooltip>
                        {{ item.collection }}
                      </el-col>
                      <!-- 举报按钮 -->
                      <el-col :span="3">
                        <el-tooltip effect="light" content="举报" show-after="3000" placement="bottom">
                          <el-button type="text" style="color: black" @click="toReportPage(item.id)">
                            <span class="iconfont icon-flag" />
                          </el-button>
                        </el-tooltip>
                      </el-col>
                      <!-- 分享按钮 -->
                      <el-col :span="3">
                        <el-tooltip effect="light" content="分享" show-after="3000" placement="bottom">
                          <el-button type="text" style="color: black" @click="shareUrl(item.id)">
                            <span class="iconfont icon-share" />
                          </el-button>
                        </el-tooltip>
                      </el-col>
                      <!-- 阅读全文按钮 -->
                      <el-col :span="3">
                        <el-button type="text" @click="toReadNote(item.id)">
                          阅读全文
                          <el-icon class="el-icon--right"><arrow-right-bold /></el-icon>
                        </el-button>
                      </el-col>
                    </el-row>
                  </div>
                  <!-- noteNav end -->
                </el-col>
              </el-row>
            </div>
          </div>
          <!-- noteList end -->
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
        </el-col>
        <el-col :span="6">
          <el-row>
            <!-- logo -->
            <el-col :span="24"><img src="../../assets/logo_2.png" alt="logo" /></el-col>
            <!-- 搜索 -->
            <el-col :span="24">
              <search-vue />
            </el-col>
            <!-- 标签云 -->
            <el-col :span="24" style="margin-top: 24px">
              <p style="margin-bottom: 12px">标签云</p>
              <el-tag
                v-for="(item, index) in labelValuesList"
                :key="index"
                :color="setColor()"
                class="tag"
                @click="clickTag(item)"
              >
                <el-tooltip effect="light" :content="item" placement="top">
                  <span style="color: white">{{ item }}</span>
                </el-tooltip>
              </el-tag>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
    </div>
  </div>
  <el-backtop />
  <footer-vue />
</template>
<script lang="ts" setup>
import { computed, onActivated, onMounted, reactive, ref } from 'vue';
import { ArrowRightBold } from '@element-plus/icons-vue';
import { useRouter } from 'vue-router';
import axios from 'axios';
import { ElMessage, ElNotification } from 'element-plus';
import FooterVue from './Footer.vue';
import searchVue from './search.vue';
import api from '../../api/index';
import oneWordVue from './oneWord.vue';
import Result from '../../api/common';

const store = api.store();
const user = ref();
const avatarUrl = ref(''); // 头像url
const router = useRouter();
// 剪贴板操作
const clipboardObj = navigator.clipboard;
const labelValuesList = ref();
const editorStyle = ref();
const userSettings = ref({ editorStyle: '', dynamicBackground: 0 });
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
const noteListEmpty = ref();
const total = ref(); // 分页数
const page = ref(1);

const showAvatar = computed(() => {
  if (store.isLogin) return true;
  return false;
});
// 下拉列表点击事件
function handleCommand(command: string) {
  switch (command) {
    case 'toLogin':
      router.push('/login');
      break;
    case 'toLogout':
      axios.get('api/user/logout');
      store.setIsLogin(false);
      router.go(0);
      break;
    case 'toPersonalCenter':
      router.push('/personalCenter');
      break;

    default:
      break;
  }
}
// 日期与天气数据 dawDate
const dawDate = reactive({
  dateNow: api.dateFormat.getDateFormatYHDW(),
  weather: api.weather.getWeather(),
});
// '去登陆'是否显示
const showLogin = computed(() => {
  if (store.isLogin || !store.isLogin === null) return false;
  return true;
});
// 个人中心是否显示
const showUserCenter = computed(() => {
  if (!showLogin.value) return true;
  return false;
});
// 空状态是否显示
const showEmptyNote = computed(() => {
  if (noteListEmpty.value) return false;
  return true;
});
// 笔记列表是否显示
const showNoteList = computed(() => {
  if (showEmptyNote.value) return false;
  return true;
});
function changePage(num: number) {
  axios.get(`api/note/getNoteList?page=${num}`).then((res) => {
    noteList.value = res.data.data.records;
  });
}
// 跳转写笔记 判断是否登录
function toWrite(): void {
  if (store.isLogin) {
    axios
      .get('api/settings/selUserSettingsList')
      .then((res) => {
        editorStyle.value = res.data.data.editorStyle;
      })
      .then(() => {
        if (editorStyle.value === 'noteEditor') router.push('/write');
        else router.push('/mdEditor');
      });
  } else {
    ElMessage.error('请登录');
  }
}
// 跳转阅读全文
function toReadNote(noteId: string) {
  router.push({ name: 'ReadNote', query: { noteId } });
  if (store.isLogin === true) {
    axios.get(`api/looks/insUserLook?noteId=${noteId}`);
  }
}
// 用户是否点赞笔记
function isUserLikeNote(noteId: string) {
  let result: Result;
  if (store.isLogin) {
    axios
      .get(`api/likes/isUserLikeNote?noteId=${noteId}`)
      .then((res) => {
        result = res.data;
      })
      .then(() => {
        const span = document.getElementById(`like${noteId}`) as HTMLElement;
        if (result.data === 'true') {
          span.style.color = 'red';
        } else {
          span.style.color = 'black';
        }
      });
  }
}
// 用户是否收藏笔记
function isUserNoteCollect(noteId: string) {
  let result: Result;
  if (store.isLogin) {
    axios
      .get(`api/collects/isUserNoteCollect?noteId=${noteId}`)
      .then((res) => {
        result = res.data;
      })
      .then(() => {
        const span = document.getElementById(`collect${noteId}`) as HTMLElement;
        if (result.data === 'true') {
          span.style.color = 'red';
        } else {
          span.style.color = 'black';
        }
      });
  }
}
// 点赞
function insLike(noteId: string, index: number) {
  if (store.isLogin === true) {
    const result = ref();
    axios
      .get(`api/likes/insLike?noteId=${noteId}`)
      .then((res) => {
        result.value = res.data;
      })
      .then(() => {
        noteList.value[index].likes = result.value.data;
        isUserLikeNote(noteId);
      });
  } else ElMessage.error('登录才能点赞哦！');
}
// 跳转举报页
function toReportPage(noteId: string) {
  if (store.isLogin === true) {
    router.push({ name: 'ReportPage', query: { noteId } });
  } else ElMessage.error('举报笔记请先登录！');
}
// 收藏笔记
function insUserCollect(noteId: string, index: number) {
  if (store.isLogin === true) {
    axios.get(`api/collects/insUserNoteCollect?noteId=${noteId}`).then((res) => {
      noteList.value[index].collection = res.data.data;
      isUserNoteCollect(noteId);
    });
  } else ElMessage.error('登录才能收藏哦！');
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
// 随机颜色
function setColor() {
  const R = Math.floor(Math.random() * 130 + 110);
  const G = Math.floor(Math.random() * 130 + 110);
  const B = Math.floor(Math.random() * 130 + 110);
  return `rgb(${R},${G},${B})`;
}
// 点击标签
function clickTag(tagName: string) {
  store.setQ(undefined);
  router.push({ name: 'SearchPage', query: { tagName } });
}
onActivated(() => {
  const result = ref();
  axios
    .get('api/user/isLogin')
    .then((res) => {
      result.value = res.data;
      store.setIsLogin(result.value.data.isLogin);
      store.setUser(result.value.data.user);
      store.setRole(res.data.data.role);
    })
    .then(() => {
      user.value = store.user;
      if (user.value != null) avatarUrl.value = user.value.avatarUrl;
    })
    .catch(() => {
      store.setRole('guest');
      store.setIsLogin(false);
    });
});
// 跳转笔记分类页
function toClassification(str: string) {
  store.setClassification(str);
  router.push('classification');
}
// 查询用户设置
function selUserSettingsList() {
  axios.get('api/settings/selUserSettingsList').then((res) => {
    userSettings.value = res.data.data;
  });
}
onMounted(() => {
  selUserSettingsList();
  store.particlesBackground = userSettings.value.dynamicBackground === 1;
  const result = ref();
  axios
    .get('api/user/isLogin')
    .then((res) => {
      result.value = res.data;
      store.setIsLogin(result.value.data.isLogin);
      store.setUser(result.value.data.user);
      store.setRole(res.data.data.role);
    })
    .then(() => {
      user.value = store.user;
      if (user.value != null) avatarUrl.value = user.value.avatarUrl;
      if (user.value.phone === '0' && user.value.email === '0' && store.isLogin) {
        ElNotification.warning({ title: '未设置手机号或邮箱', message: '手机号或邮箱是找回密码的重要凭证' });
      }
    })
    .catch(() => {
      store.setRole('guest');
      store.setIsLogin(false);
    });

  // 笔记列表
  axios
    .get(`api/note/getNoteList?page=${1}`)
    .then((res) => {
      noteList.value = res.data.data.records;
      total.value = Number(res.data.msg);
      noteListEmpty.value = true;
    })
    .catch(() => {
      noteListEmpty.value = false;
    });
  // 标签列表
  axios
    .get('api/note/selLabelValuesList')
    .then((res) => {
      labelValuesList.value = res.data.data;
    })
    .then(() => {
      if (labelValuesList.value.indexOf('') !== -1) labelValuesList.value.splice(labelValuesList.value.indexOf(''), 1);
    });

  console.log(
    // eslint-disable-next-line operator-linebreak
    '  _____                        _                    _  _____ _                    \n' +
      // eslint-disable-next-line operator-linebreak
      ' |  __ \\                      | |   /\\             | |/ ____| |                   \n' +
      // eslint-disable-next-line operator-linebreak
      ' | |__) |___  ___ ___  _ __ __| |  /  \\   _ __   __| | (___ | |__   __ _ _ __ ___ \n' +
      // eslint-disable-next-line operator-linebreak
      " |  _  // _ \\/ __/ _ \\| '__/ _` | / /\\ \\ | '_ \\ / _` |\\___ \\| '_ \\ / _` | '__/ _ \\\n" +
      // eslint-disable-next-line operator-linebreak
      ' | | \\ \\  __/ (_| (_) | | | (_| |/ ____ \\| | | | (_| |____) | | | | (_| | | |  __/\n' +
      ' |_|  \\_\\___|\\___\\___/|_|  \\__,_/_/    \\_\\_| |_|\\__,_|_____/|_| |_|\\__,_|_|  \\___|    \n',
  );
});
</script>
<style scoped>
.box {
  width: 80%;
  background-color: #fff;
  margin: 0 auto;
  min-height: calc(934px - 60px);
}
.navTop {
  background-color: rgba(40, 42, 44, 0.6);
  font-size: large;
  user-select: none;
  color: #ffffff;
  position: fixed;
  width: 80%;
  z-index: 999;
}
.text {
  cursor: pointer;
  transition: all 0.5s;
}
.text:hover {
  font-size: larger;
}
.navTop > .el-row {
  align-items: center;
}
.bacImg {
  background: url('https://img.lonelyzhou.cn/RecordAndShare/20180910_4.jpg');
  background-size: cover;
  height: 160px;
}
.noteList {
  border: 1px solid rgb(0, 0, 0);
  border-radius: 16px;
  margin-top: 32px;
  margin-left: 64px;
}
.main img {
  width: 70%;
}
.noteText {
  text-align: left;
  margin: 16px 0 0 16px;
  cursor: pointer;
}
.noteTextTitle {
  font-family: '思源黑体';
  font-size: large;
}
.noteTextSyn {
  height: 68px;
  font-size: 12px;
  text-indent: 2em;
  font-family: 'OPPO Sans';
  margin-top: 6px;
  margin-right: 3px;
}
.noteNav {
  margin-top: 2px;
}
.noteNav > .el-row {
  align-items: center;
}
a {
  color: white;
}
.isLike {
  color: red;
}
.search {
  border-radius: 20px;
  width: 80%;
}
.tag {
  margin-right: 3px;
  cursor: pointer;
}
</style>
