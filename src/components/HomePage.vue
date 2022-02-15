<template>
  <div class="box">
    <div class="bacImg">
      <div class="navTop">
        <el-row>
          <el-col :span="3" :xs="8"><span class="text">生活杂谈</span></el-col>
          <el-col :span="3" :xs="8"><span class="text">学习分享</span></el-col>
          <el-col :span="3" :xs="8"><span class="text">工作经验</span></el-col>
          <!-- 当前日期 城市 天气 温度 -->
          <el-col :span="10" :xs="24">
            <span class="text">
              {{ dawDate.dateNow }}
              {{ dawDate.weather.city }}
              {{ dawDate.weather.text }}
              {{ dawDate.weather.temp }} ℃
            </span>
          </el-col>
          <el-col :span="3" :xs="12" @click="toWrite">
            <span class="text">写笔记</span>
          </el-col>
          <el-col :span="2" :xs="12">
            <!-- 头像 -->
            <el-dropdown @command="handleCommand">
              <el-avatar :size="50" :src="circleUrl"></el-avatar>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item command="toLogin" v-if="showLogin"><span>去登录</span></el-dropdown-item>
                  <el-dropdown-item v-if="showUserCenter" command="toPersonalCenter"
                    ><span>个人中心</span></el-dropdown-item
                  >
                  <el-dropdown-item command="toLogout" v-if="showUserCenter"><span>退出登录</span></el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </el-col>
        </el-row>
      </div>
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
                  <div class="noteText">
                    <div class="noteTextTitle">{{ item.title }}</div>
                    <div class="noteTextSyn">{{ item.article_tabled }}</div>
                  </div>
                </el-col>
                <el-col :span="24">
                  <div class="noteNav">
                    <el-row>
                      <!-- 头像 -->
                      <el-col :span="3"><el-avatar :size="40" :src="item.avatar_url" /></el-col>
                      <!-- 作者 -->
                      <el-col :span="3"> {{ item.name }} </el-col>
                      <!-- 浏览数 -->
                      <el-col :span="3"> <span class="iconfont icon-eye" />{{ item.look }} </el-col>
                      <!-- 点赞数 -->
                      <el-col :span="3">
                        <el-button type="text" style="color: black"> <span class="iconfont icon-like" /> </el-button>
                        {{ item.likes }}
                      </el-col>
                      <!-- 收藏数 -->
                      <el-col :span="3">
                        <el-button type="text" style="color: black">
                          <span class="iconfont icon-star" />
                        </el-button>
                        {{ item.collection }}
                      </el-col>
                      <!-- 举报按钮 -->
                      <el-col :span="3">
                        <el-button type="text" style="color: black"><span class="iconfont icon-flag" /></el-button>
                      </el-col>
                      <!-- 分享按钮 -->
                      <el-col :span="3">
                        <el-button type="text" style="color: black"><span class="iconfont icon-share" /></el-button>
                      </el-col>
                      <!-- 阅读全文按钮 -->
                      <el-col :span="3">
                        <el-button type="text">
                          阅读全文
                          <el-icon class="el-icon--right"><arrow-right-bold /></el-icon>
                        </el-button>
                      </el-col>
                    </el-row>
                  </div>
                  <!-- noteNav -->
                </el-col>
              </el-row>
            </div>
          </div>
          <!-- noteList -->
          <el-pagination
            style="margin: 2%"
            @current-change="changePage(page)"
            v-model:current-page="page"
            layout="prev, pager, next"
            :total="total"
            background
          />
        </el-col>
        <el-col :span="6">
          <el-row>
            <el-col :span="24"><img src="../assets/logo_2.png" alt="logo" /></el-col>
          </el-row>
        </el-col>
      </el-row>
    </div>
  </div>

  <el-backtop />
  <footer-vue />
</template>
<script lang="ts" setup>
// eslint-disable-next-line object-curly-newline
import { computed, onMounted, reactive, ref } from 'vue';
import { ArrowRightBold } from '@element-plus/icons-vue';
import { useCookies } from 'vue3-cookies';
import { useRouter } from 'vue-router';
import axios from 'axios';
import { ElMessage, ElNotification } from 'element-plus';
import FooterVue from './Footer.vue';
import api from '../api/index';

const { cookies } = useCookies();
const store = api.store();
const loginFlag = ref(store.loginFlag);
const circleUrl = ref(); // 头像url
const router = useRouter();
const noteList = ref([
  {
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
  },
]);
const noteListEmpty = ref();
const total = ref(); // 分页数
const page = ref(1);
// 下拉列表点击事件
function handleCommand(command: string) {
  switch (command) {
    case 'toLogin':
      router.push('/login');
      break;
    case 'toLogout':
      axios.get('api/user/logout');
      // cookies.set('loginFlag', 'false', '1d');
      store.setLoginFlag(false);
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
  if (loginFlag.value || !loginFlag.value === null) return false;
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
onMounted(() => {
  const user = cookies.get('userInfo') as any;
  if (user !== null) {
    if (user.phone === '0' && loginFlag.value) {
      ElNotification.warning({ title: '未设置手机号', message: '手机号是找回密码的重要凭证' });
    }
    if (loginFlag.value) {
      circleUrl.value = user.avatar_url;
    }
  }
  axios
    .get(`api/note/getNoteList?page=${1}`)
    .then((res) => {
      noteList.value = res.data.data.records;
      total.value = Number(res.data.msg);
      // page.value = Math.ceil(Number(res.data.msg) / 9);
      noteListEmpty.value = true;
      // console.log(res);
    })
    .catch(() => {
      noteListEmpty.value = false;
    });
});
function toTop() {
  const goTop = setInterval(() => {
    let scrollTop = document.body.scrollTop + document.documentElement.scrollTop;
    scrollTop -= 10;
    document.documentElement.scrollTop = scrollTop;
    if (scrollTop < 0) {
      clearInterval(goTop);
    }
  }, 5);
}
function changePage(num: number) {
  axios
    .get(`api/note/getNoteList?page=${num}`)
    .then((res) => {
      noteList.value = res.data.data.records;
      console.log(res);
    })
    .then(() => {
      toTop();
    });
}
// 跳转写笔记 判断是否登录
function toWrite(): void {
  if (loginFlag.value) router.push('/write');
  else {
    ElMessage.error('请登录');
  }
}
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
  /* width: 770px; */
  /* height: 160px; */
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
</style>
