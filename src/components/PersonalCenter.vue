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
                <el-col :span="24" class="avatar">当前头像</el-col>
                <el-button type="primary" plain @click="selUserHistoricalAvatar"> 历史头像 </el-button>
              </el-row>
            </el-col>
          </el-row>
        </el-tab-pane>
        <el-tab-pane label="我的笔记" name="我的笔记">
          <my-note-vue></my-note-vue>
        </el-tab-pane>
        <el-tab-pane label="我的评论" name="我的评论">
          <my-comment-vue></my-comment-vue>
        </el-tab-pane>
        <el-tab-pane label="账户安全" class="analysis">
          <el-card class="box-card">
            <template #header>
              <div class="card-header">
                <span>账户安全</span>
              </div>
            </template>
            <div class="analysis">
              <span class="iconfont icon-phone">手机</span> {{ isPhone }}
              <el-button type="text" @click="toUpdPE('phone')"> {{ isPhoneText }} </el-button>
            </div>
            <div class="analysis">
              <span class="iconfont icon-mail">邮箱</span> {{ isEmail }}
              <el-button type="text" @click="toUpdPE('email')"> {{ isEmailText }} </el-button>
            </div>
          </el-card>
        </el-tab-pane>
        <el-tab-pane label="我的点赞" name="我的点赞">
          <my-likes-vue></my-likes-vue>
        </el-tab-pane>
        <el-tab-pane label="我的收藏" name="我的收藏">
          <my-collect-vue></my-collect-vue>
        </el-tab-pane>
        <el-tab-pane label="我的反馈" name="我的反馈">
          <my-feedback-vue></my-feedback-vue>
        </el-tab-pane>
        <el-tab-pane label="其他">
          <other-vue />
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
  <!-- 历史头像弹出框 -->
  <el-dialog v-model="dialogVisible" title="Tips" width="30%">
    <el-radio-group v-model="radio" fill="#cccccc" @change="changeAvatar(radio)">
      <el-radio-button
        :label="item"
        v-for="(item, index) in userHistoricalAvatar"
        :key="index"
        class="historicalAvatar"
      >
        <el-avatar :size="50" :src="item" />
      </el-radio-button>
    </el-radio-group>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitAvatar">确定</el-button>
      </span>
    </template>
  </el-dialog>
</template>
<script lang="ts" setup>
import axios from 'axios';
import { computed, reactive, ref } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import PageHeaderVue from './PageHeader.vue';
import otherVue from './PersonalCenter/other.vue';
import myLikesVue from './PersonalCenter/myLikes.vue';
import myCollectVue from './PersonalCenter/myCollect.vue';
import myNoteVue from './PersonalCenter/myNote.vue';
import myCommentVue from './PersonalCenter/myComment.vue';
import myFeedbackVue from './PersonalCenter/myFeedback.vue';
import api from '../api/index';

const detail = '个人中心';
const store = api.store();
const activeName = ref('首页');
const path = 'index';
const user = store.user as any;
const router = useRouter();
const uploadData = { username: user.username };
const userHistoricalAvatar = ref();
const radio = ref(user.avatarUrl);
const dialogVisible = ref(false);
const show = ref(true);
// const userSettings = ref({ editorStyle: '' });
// const noteMessage = ref();
const myData = reactive({
  myNoteList: [
    {
      title: '',
      createTime: '',
      look: '',
      likes: '',
      collection: '',
      id: '',
      message: '',
      noteType: '',
    },
  ],
  myCommentList: [
    {
      id: '',
      title: '',
      time: '',
      message: '',
      noteId: '',
    },
  ],
  myCollectList: [{ noteId: '', time: '', title: '' }],
  myLikeList: [{ noteId: '', time: '', title: '' }],
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
// 更新信息
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
  if (activeName.value === '我的评论') {
    axios.get('api/comment/selUserCommentList?page=1').then((res) => {
      myData.myCommentList = res.data.data.records;
      paginationData.totalMyComment = Number(res.data.msg);
      show.value = true;
    });
  }
}
function selUserHistoricalAvatar() {
  axios
    .get('api/user/selUserHistoricalAvatar')
    .then((res) => {
      userHistoricalAvatar.value = res.data.data;
    })
    .then(() => {
      dialogVisible.value = true;
    });
}
function changeAvatar(avatar: string) {
  console.log(avatar);
}
function submitAvatar() {
  axios.get(`api/user/updSelAvatar?avatarUrl=${radio.value}`).then((res) => {
    if (res.data.code === 200) {
      ElMessage.success('更新头像成功');
      dialogVisible.value = false;
    }
  });
}
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
.avatar {
  padding: 8px 12px;
}
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
  width: 360px;
}
.analysis {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.historicalAvatar {
  margin-right: 20px;
}
</style>
