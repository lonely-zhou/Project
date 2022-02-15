<template>
  <page-header-vue :detail="detail" :path="path" />
  <div class="box">
    <div style="text-align: left">
      <el-tabs tab-position="left" type="card" v-model="activeName" @tab-click="selUserNote">
        <el-tab-pane label="首页" name="首页" style="margin-top: 20px">
          <el-row>
            <el-col :span="8">
              <el-row>
                <el-col :span="24"><el-avatar :size="50" :src="userInfo.avatar_url" /></el-col>
              </el-row>
            </el-col>
            <el-col :span="8">
              <el-row>
                <el-col :span="24">
                  <el-row style="text-align: left">
                    <el-col :span="24" :pull="19" style="font-size: large">{{ userInfo.nickname }}</el-col>
                    <el-col :span="24" :pull="19" style="font-size: small; margin-top: 9px">
                      用户名：{{ userInfo.username }}
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
            <!-- <el-col :span="4">{{ userInfo.username }}</el-col>
            <el-col :span="4">{{ userInfo.username }}</el-col> -->
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
          <el-row style="width: 40%">
            <el-col :span="24">
              <el-input v-model="updUserInfo.nickname" maxlength="8" show-word-limit :placeholder="userInfo.nickname">
                <template #prepend>昵称</template>
              </el-input>
            </el-col>
            <el-col :span="24" style="text-align: center; margin-top: 20px">
              <el-radio-group v-model="updUserInfo.sex">
                <el-radio-button label="男"></el-radio-button>
                <el-radio-button label="女"></el-radio-button>
                <el-radio-button label="保密"></el-radio-button>
              </el-radio-group>
            </el-col>
            <el-col :span="24" style="text-align: center; margin-top: 20px">
              <el-button type="primary" plain @click="updUser" :disabled="disUpdUser">保存</el-button>
            </el-col>
          </el-row>
        </el-tab-pane>
        <el-tab-pane label="更换头像">
          <el-row>
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
              <el-row>
                <el-col :span="24"><el-avatar :size="80" :src="userInfo.avatar_url" /></el-col>
                <el-col :span="24">当前头像</el-col>
              </el-row>
            </el-col>
          </el-row>
        </el-tab-pane>
        <el-tab-pane label="我的笔记" name="我的笔记">
          <div class="myNote">
            <el-row v-for="(v, i) in userNoteList" :key="i" class="userNoteList">
              <el-col :span="12">
                <el-row>
                  <!-- 标题 -->
                  <el-col :span="24" class="title">
                    {{ i + 1 + '.' }} {{ v.title }} <span class="createTime">{{ v.create_time }}</span>
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
                <el-button type="primary" plain>删除</el-button>
              </el-col>
            </el-row>
          </div>
        </el-tab-pane>
        <el-tab-pane label="我的评论">Task</el-tab-pane>
        <el-tab-pane label="账户安全">
          <el-row>
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
      </el-tabs>
    </div>
  </div>
  {{ userInfo }}
</template>
<script lang="ts" setup>
import axios from 'axios';
import { computed, reactive, ref } from 'vue';
import { useCookies } from 'vue3-cookies';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import PageHeaderVue from './PageHeader.vue';
import api from '../api/index';

const detail = '个人中心';
const activeName = ref('首页');
const { cookies } = useCookies();
const path = 'index';
const userInfo = cookies.get('userInfo') as any;
const router = useRouter();
const uploadData = { username: userInfo.username };
const userNoteList = ref();
const store = api.store();

const updUserInfo = reactive({
  username: userInfo.username,
  nickname: '',
  sex: userInfo.sex,
});

const isPhone = computed(() => {
  if (userInfo.phone !== '0') return userInfo.phone.replace(/(\d{3})\d{4}(\d{4})/, '$1****$2');
  return '未绑定';
});
const isEmail = computed(() => {
  if (userInfo.email !== '0') return userInfo.email;
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
const disUpdUser = computed(() => {
  if (updUserInfo.nickname === '') return true;
  return false;
});

function updUser() {
  const result = ref();
  axios
    .post('api/user/updUser', updUserInfo)
    .then((res) => {
      result.value = res.data;
      cookies.set('userInfo', JSON.stringify(result.value.data), '7d');
    })
    .then(() => {
      if (result.value.code === 200) {
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
function selUserNote() {
  if (activeName.value === '我的笔记') {
    axios.get(`api/note/selUserNote?userid=${userInfo.id}&page=1`).then((res) => {
      userNoteList.value = res.data.data.records;
      console.log(res);
    });
  }
}
function updUserNote(index: number) {
  store.setUserNote(userNoteList.value[index]);
  router.push('/write');
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
.myNote {
  width: 80%;
  height: 90%;
}
.myNote .title {
  font-family: '思源黑体';
  font-size: large;
}
.userNoteList {
  border: 1px solid black;
  border-radius: 5px;
  margin-bottom: 12px;
  align-items: center;
}
.createTime {
  font-size: xx-small;
}
</style>
