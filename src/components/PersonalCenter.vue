<template>
  <page-header-vue :detail="detail" :path="path" />
  <div class="box">
    <div style="text-align: left">
      <el-tabs tab-position="left" type="card" v-model="activeName" @tab-click="clickTap">
        <el-tab-pane label="首页" name="首页" style="margin-top: 20px">
          <el-row>
            <el-col :span="8">
              <el-row>
                <el-col :span="24"><el-avatar :size="50" :src="user.avatarUrl" /></el-col>
              </el-row>
            </el-col>
            <el-col :span="8">
              <el-row>
                <el-col :span="24">
                  <el-row style="text-align: left">
                    <el-col :span="24" :pull="19" style="font-size: large">{{ user.nickname }}</el-col>
                    <el-col :span="24" :pull="19" style="font-size: small; margin-top: 9px">
                      用户名：{{ user.username }}
                    </el-col>
                  </el-row>
                </el-col>
              </el-row>
            </el-col>
            <el-col :span="8">
              <el-row>
                <el-col :span="24">
                  <el-button type="primary" plain @click="activeName = '我的信息'">修改信息</el-button>
                </el-col>
              </el-row>
            </el-col>
            <!-- <el-col :span="4">{{ user.username }}</el-col>
            <el-col :span="4">{{ user.username }}</el-col> -->
            <el-divider style="margin: 12px 0" />
            <el-row>
              <el-col :span="24"> <p>账户安全</p></el-col>
              <el-col :span="24" style="margin-top: 16px">
                <span class="iconfont icon-phone">手机</span> {{ isPhone }}
              </el-col>
              <el-col :span="24" style="margin-top: 16px">
                <span class="iconfont icon-mail">邮箱</span> {{ isEmail }}
              </el-col>
            </el-row>
          </el-row>
        </el-tab-pane>
        <el-tab-pane label="我的信息" name="我的信息">
          <el-row style="width: 40%" class="myInfo">
            <el-col :span="24">
              <el-input v-model="upduser.nickname" maxlength="8" show-word-limit :placeholder="user.nickname">
                <template #prepend>昵称</template>
              </el-input>
            </el-col>
            <el-col :span="24" style="margin-top: 20px">
              性别：
              <el-radio-group v-model="upduser.sex">
                <el-radio label="男"></el-radio>
                <el-radio label="女"></el-radio>
                <el-radio label="保密"></el-radio>
              </el-radio-group>
            </el-col>
            <el-col :span="24" style="margin-top: 20px">
              <el-button type="primary" plain @click="updUser" style="width: 100%"> 保存 </el-button>
            </el-col>
          </el-row>
        </el-tab-pane>
        <el-tab-pane label="更换头像">
          <el-row class="myAvatar">
            <el-col :span="12" :push="4">
              <el-upload
                action="api/user/updAvatar"
                :data="uploadData"
                :show-file-list="false"
                :on-success="uploadSuccess"
              >
                <div class="updAvatar"><span class="iconfont icon-plus updAvatarIcon" /></div>
              </el-upload>
            </el-col>
            <el-col :span="12">
              <el-row style="margin-top: 30px">
                <el-col :span="24"><el-avatar :size="80" :src="user.avatarUrl" /></el-col>
                <el-col :span="24">当前头像</el-col>
              </el-row>
            </el-col>
          </el-row>
        </el-tab-pane>
        <el-tab-pane label="我的笔记" name="我的笔记">
          <div class="myNote" v-if="show">
            <el-radio-group v-model="noteMessage" size="small" @change="changeMessage">
              <el-radio-button label="">全部笔记</el-radio-button>
              <el-radio-button label="0">公开笔记</el-radio-button>
              <el-radio-button label="1">私密笔记</el-radio-button>
            </el-radio-group>
            <el-empty description="无笔记" v-if="showMyNote" />
            <el-row v-for="(v, i) in myData.myNoteList" :key="i" class="userNoteList">
              <el-col :span="12">
                <el-row>
                  <!-- 标题 -->
                  <el-col :span="24" class="title">
                    {{ i + 1 + '.' }} {{ v.title }}
                    <span class="createTime">
                      {{ v.create_time }}
                      <span v-if="v.message === '1'">私密</span>
                    </span>
                  </el-col>
                  <el-col :span="24" class="info">
                    <el-row>
                      <!-- 浏览数 -->
                      <el-col :span="3"> <span class="iconfont icon-eye" />{{ v.look }} </el-col>
                      <!-- 点赞数 -->
                      <el-col :span="3"> <span class="iconfont icon-like" /> {{ v.likes }} </el-col>
                      <!-- 收藏数 -->
                      <el-col :span="3"> <span class="iconfont icon-star" />{{ v.collection }} </el-col>
                    </el-row>
                  </el-col>
                </el-row>
              </el-col>
              <el-col :span="12" style="text-align: right">
                <el-button type="primary" plain @click="updUserNote(i)">修改</el-button>
                <el-button type="primary" plain @click="delUserNote(v.id, i)">删除</el-button>
              </el-col>
            </el-row>
            <el-pagination
              style="margin: 2%"
              @current-change="changePageMyNote(paginationData.pageMyNote)"
              v-model:current-page="paginationData.pageMyNote"
              layout="prev, pager, next"
              :total="paginationData.totalMyNote"
              background
              hide-on-single-page
            />
          </div>
        </el-tab-pane>
        <el-tab-pane label="我的评论" name="我的评论">
          <div class="myComment" v-if="show">
            <el-empty description="无评论" v-if="showMyComment" />
            <el-row v-for="(item, index) in myData.myCommentList" :key="index" class="userCommentList">
              <el-col :span="20">
                <el-row>
                  <el-col :span="24">
                    {{ index + 1 }}.&nbsp;{{ item.title }} <span style="font-size: smaller">{{ item.time }}</span>
                  </el-col>
                  <el-col :span="24"> {{ item.message }} </el-col>
                </el-row>
              </el-col>
              <el-col :span="4">
                <el-row>
                  <el-col :span="24">
                    <el-button type="primary" plain @click="updMyComment(index, item.id)">修改</el-button>
                  </el-col>
                  <el-col :span="24">
                    <el-button type="primary" plain @click="delMyComment(index, item.id)">删除</el-button>
                  </el-col>
                </el-row>
              </el-col>
            </el-row>
            <el-pagination
              style="margin: 2%"
              @current-change="changePageMyComment(paginationData.pageMyComment)"
              v-model:current-page="paginationData.pageMyComment"
              layout="prev, pager, next"
              :total="paginationData.totalMyComment"
              background
              hide-on-single-page
            />
          </div>
        </el-tab-pane>
        <el-tab-pane label="账户安全">
          <el-row class="myAccount">
            <el-col :span="24" style="margin-top: 16px">
              <span class="iconfont icon-phone">手机</span> {{ isPhone }}
              <el-button type="text" @click="toUpdPE('phone')"> {{ isPhoneText }} </el-button>
            </el-col>
            <el-col :span="24" style="margin-top: 16px">
              <span class="iconfont icon-mail">邮箱</span> {{ isEmail }}
              <el-button type="text" @click="toUpdPE('email')"> {{ isEmailText }} </el-button>
            </el-col>
          </el-row>
        </el-tab-pane>
        <el-tab-pane label="我的点赞" name="我的点赞">
          <el-empty description="无点赞" v-if="showMyLike" />
          <div class="myLike" v-if="show">
            <el-row v-for="(item, index) in myData.myLikeList" :key="index" class="userLikeList">
              <el-col :span="20" @click="toReadNote(item.note_id)" class="toReadNote">
                {{ index + 1 }}.&nbsp;{{ item.title }} <span style="font-size: smaller">{{ item.time }}</span>
              </el-col>
              <el-col :span="4">
                <el-button type="primary" plain @click="delUserLikeNote(item.note_id, index)">删除</el-button>
              </el-col>
            </el-row>
            <el-pagination
              style="margin: 2%"
              @current-change="changePageMyCollect(paginationData.pageMyCollect)"
              v-model:current-page="paginationData.pageMyCollect"
              layout="prev, pager, next"
              :total="paginationData.totalMyCollect"
              :page-size="5"
              hide-on-single-page
              background
            />
          </div>
        </el-tab-pane>
        <el-tab-pane label="我的收藏" name="我的收藏">
          <el-empty description="无收藏" v-if="showMyCollect" />
          <div class="myCollect" v-if="show">
            <el-row v-for="(item, index) in myData.myCollectList" :key="index" class="userCollectList">
              <el-col :span="20" @click="toReadNote(item.note_id)" class="toReadNote">
                {{ index + 1 }}.&nbsp;{{ item.title }} <span style="font-size: smaller">{{ item.time }}</span>
              </el-col>
              <el-col :span="4">
                <el-button type="primary" plain @click="delUserNoteCollect(item.note_id, index)">删除</el-button>
              </el-col>
            </el-row>
            <el-pagination
              style="margin: 2%"
              @current-change="changePageMyCollect(paginationData.pageMyCollect)"
              v-model:current-page="paginationData.pageMyCollect"
              layout="prev, pager, next"
              :total="paginationData.totalMyCollect"
              :page-size="5"
              hide-on-single-page
              background
            />
          </div>
        </el-tab-pane>
        <el-tab-pane label="其他">
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
          </el-row>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>
<script lang="ts" setup>
import axios from 'axios';
// eslint-disable-next-line object-curly-newline
import { computed, onMounted, reactive, ref } from 'vue';
// import { useCookies } from 'vue3-cookies';
import { useRouter } from 'vue-router';
import { ElMessage, ElMessageBox } from 'element-plus';
import PageHeaderVue from './PageHeader.vue';
import api from '../api/index';

const detail = '个人中心';
const store = api.store();
const activeName = ref('首页');
// const { cookies } = useCookies();
const path = 'index';
const user = store.user as any;
const router = useRouter();
const uploadData = { username: user.username };
const show = ref(true);
const userSettings = ref({ editorStyle: '' });
const noteMessage = ref();
const myData = reactive({
  myNoteList: [
    {
      title: '',
      create_time: '',
      look: '',
      likes: '',
      collection: '',
      id: '',
      message: '',
      note_type: '',
    },
  ],
  myCommentList: [
    {
      id: '',
      title: '',
      time: '',
      message: '',
      note_id: '',
    },
  ],
  myCollectList: [{ note_id: '', time: '', title: '' }],
  myLikeList: [{ note_id: '', time: '', title: '' }],
});
const paginationData = reactive({
  totalMyNote: 0,
  pageMyNote: 1,
  totalMyComment: 0,
  pageMyComment: 1,
  totalMyCollect: 0,
  pageMyCollect: 1,
  totalMyLike: 0,
  pageMyLike: 1,
});

const upduser = reactive({
  username: user.username,
  nickname: '',
  sex: user.sex,
});

const isPhone = computed(() => {
  if (user.phone !== '0') return user.phone.replace(/(\d{3})\d{4}(\d{4})/, '$1****$2');
  return '未绑定';
});
const isEmail = computed(() => {
  if (user.email !== '0') return user.email;
  return '未绑定';
});
const isPhoneText = computed(() => {
  if (isPhone.value === '未绑定') return '绑定';
  return '修改';
});
const isEmailText = computed(() => {
  if (isEmail.value === '未绑定') return '绑定';
  return '修改';
});
const showMyComment = computed(() => {
  if (myData.myCommentList.length === 0) return true;
  return false;
});
const showMyNote = computed(() => {
  if (myData.myNoteList.length === 0) return true;
  return false;
});
const showMyCollect = computed(() => {
  if (myData.myCollectList.length === 0) return true;
  return false;
});
const showMyLike = computed(() => {
  if (myData.myLikeList.length === 0) return true;
  return false;
});

const showAdminButton = computed(() => {
  const role = JSON.parse(sessionStorage.getItem('role') as string);
  if (role === 'admin') return true;
  return false;
});

function updUser() {
  const result = ref();
  axios
    .post('api/user/updUser', upduser)
    .then((res) => {
      result.value = res.data;
    })
    .then(() => {
      if (result.value.code === 200) {
        store.setUser(result.value.data);
        ElMessage.success('信息修改成功！');
        router.go(0);
      } else ElMessage.error('信息修改失败！');
    });
}
function toUpdPE(showInfo: string) {
  router.push({ name: 'UpdPE', params: { showInfo } });
}
function uploadSuccess() {
  ElMessage.success('头像上传成功，下次登录生效！');
}
function clickTap() {
  show.value = false;
  if (activeName.value === '我的笔记') {
    axios.get(`api/note/selUserNote?userid=${user.id}&page=1`).then((res) => {
      myData.myNoteList = res.data.data.records;
      paginationData.totalMyNote = Number(res.data.msg);
      show.value = true;
    });
  }
  if (activeName.value === '我的评论') {
    axios.get(`api/comment/selUserCommentList?userId=${user.id}&page=1`).then((res) => {
      myData.myCommentList = res.data.data.records;
      paginationData.totalMyComment = Number(res.data.msg);
      show.value = true;
    });
  }
  if (activeName.value === '我的收藏') {
    axios.get(`api/collects/selUserNoteCollectList?userId=${user.id}&page=1`).then((res) => {
      myData.myCollectList = res.data.data.records;
      // paginationData.totalMyCollect = Number(res.data.msg);
      paginationData.totalMyCollect = Number(res.data.msg);
      show.value = true;
    });
  }
  if (activeName.value === '我的点赞') {
    axios.get(`api/likes/selUserLikeList?userId=${user.id}&page=1`).then((res) => {
      myData.myLikeList = res.data.data.records;
      paginationData.totalMyLike = Number(res.data.msg);
      show.value = true;
    });
  }
}
function updUserNote(index: number) {
  store.setUserNote(JSON.stringify(myData.myNoteList[index]));
  console.log(myData.myNoteList[index]);

  if (myData.myNoteList[index].note_type === 'md') router.push('/updUserNoteMd');
  else router.push('/updUserNote');
}
// 删除用户笔记
function delUserNote(noteId: string, index: number) {
  const result = ref();
  axios
    .get(`api/note/delUserNote?noteId=${noteId}`)
    .then((res) => {
      result.value = res.data;
    })
    .then(() => {
      if (result.value.code === 200) {
        myData.myNoteList.splice(index, 1);
      }
    });
}
function changePageMyNote(pageMyNoteNum: number) {
  axios.get(`api/?userid=${user.id}&page=${pageMyNoteNum}`).then((res) => {
    myData.myNoteList = res.data.data.records;
  });
}
function changePageMyComment(pageMyCommentNum: number) {
  axios.get(`api/?userid=${user.id}&page=${pageMyCommentNum}`).then((res) => {
    myData.myCommentList = res.data.data.records;
  });
}
function changePageMyCollect(pageMyCollectNum: number) {
  axios.get(`api/collects/selUserNoteCollectList?userId=${user.id}&page=${pageMyCollectNum}`).then((res) => {
    myData.myCollectList = res.data.data.records;
  });
}
function delUserNoteCollect(noteId: string, index: number) {
  const result = ref();
  axios
    .delete(`api/collects/delUserNoteCollect?userId=${user.id}&noteId=${noteId}`)
    .then((res) => {
      result.value = res.data;
    })
    .then(() => {
      if (result.value.code === 200) {
        myData.myCollectList.splice(index, 1);
      }
    });
}
function toReadNote(noteId: string) {
  router.push({ name: 'ReadNote', query: { noteId } });
}
// 修改我的评论
function updMyComment(index: number, commentId: string) {
  ElMessageBox.prompt('输入评论', '评论', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    inputValue: myData.myCommentList[index].message,
  })
    .then(({ value }) => {
      const result = ref();
      const comment = reactive({
        id: commentId,
        time: api.dateFormat.getDateFormatYHD(),
        message: value,
      });
      axios
        .post('api/comment/updNoteComment', comment)
        .then((res) => {
          result.value = res.data;
        })
        .then(() => {
          myData.myCommentList[index].message = result.value.data.message;
          myData.myCommentList[index].time = result.value.data.time;
          ElMessage({
            type: 'success',
            message: '修改成功',
          });
        });
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: '未修改评论',
      });
    });
}
function delMyComment(index: number, id: string) {
  const result = ref();
  axios
    .delete(`api/comment/delNoteComment?id=${id}`)
    .then((res) => {
      result.value = res.data;
    })
    .then(() => {
      if (result.value.code === 200) {
        myData.myCommentList.splice(index, 1);
      }
    });
}
function delUserLikeNote(noteId: string, index: number) {
  const result = ref();
  axios
    .delete(`api/likes/delUserLikeNote?userId=${user.id}&noteId=${noteId}`)
    .then((res) => {
      result.value = res.data;
    })
    .then(() => {
      if (result.value.code === 200) {
        myData.myLikeList.splice(index, 1);
      }
    });
}
function selUserSettingsList() {
  axios.get(`api/settings/selUserSettingsList?userId=${user.id}`).then((res) => {
    userSettings.value = res.data.data;
  });
}
function change(editorText: string) {
  const result = ref();
  axios
    .get(`api/settings/updUserEditorStyle?userId=${user.id}&editor=${editorText}`)
    .then((res) => {
      result.value = res.data;
    })
    .then(() => {
      selUserSettingsList();
      if (result.value.code === 200) ElMessage.success('编辑器已更改');
    });
}
function changeMessage() {
  axios.get(`api/note/selUserNote?userid=${user.id}&page=1&message=${noteMessage.value}`).then((res) => {
    myData.myNoteList = res.data.data.records;
    paginationData.totalMyNote = Number(res.data.msg);
  });
}
onMounted(() => {
  selUserSettingsList();
});
</script>
<style scoped>
.box {
  background-color: white;
}
.box > div {
  overflow: hidden;
  width: 980px;
  height: 100%;
  margin: 10px auto 100px;
  border: 1px solid #e1e2e5;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.14);
  border-radius: 4px;
  background-color: #fafafa;
}
p {
  text-align: left;
}
.updAvatar {
  width: 178px;
  height: 178px;
  border: 1px dashed #d9d9d9;
  position: relative;
}
.updAvatarIcon {
  font-size: 28px;
  color: #8c939d;
  width: 28px;
  height: 28px;
  text-align: center;
  /* 盒子水平垂直居中 --- start*/
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  margin: auto;
  /* 盒子水平垂直居中 --- end*/
}
.myNote,
.myComment,
.myCollect,
.myLike,
.myAvatar,
.myInfo,
.myAccount {
  margin: 30px 0 0 60px;
}
.myInfo {
  text-align: left;
}
.myNote .title {
  font-family: '思源黑体';
  font-size: large;
}
.userNoteList,
.userCommentList,
.userCollectList,
.userLikeList {
  border: 1px solid black;
  border-radius: 5px;
  margin-bottom: 12px;
  width: 640px;
  align-items: center;
}
.createTime {
  font-size: xx-small;
}
.toReadNote {
  cursor: pointer;
}
</style>
