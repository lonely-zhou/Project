<!-- 修改手机或者邮箱 -->
<template>
  <div class="box">
    <PageHeaderVue :detail="detail" :path="path"></PageHeaderVue>
    <!-- 修改手机 -->
    <div class="updPhone" v-if="show">
      <el-steps :active="1" finish-status="success" simple style="margin-top: 20px">
        <el-step title="Step 1"></el-step>
        <el-step title="Step 2"></el-step>
      </el-steps>
      <el-tabs v-model="activeName">
        <el-tab-pane name="step1" class="step1">
          <div style="display: flex">
            <el-input placeholder="输入手机号" v-model="updPhone.phone" clearable />
            <el-button type="primary" style="width: 30%" :disabled="set60sDisabled" @click="set60s('phone')">
              <span class="wait_60s">
                {{ wait60sText }}
                <span v-show="wait60sShow">{{ wait60s }} </span>
              </span>
            </el-button>
          </div>
          <el-input v-model="updPhone.code" placeholder="输入验证码" clearable />
          <el-button style="width: 100%; margin-top: 10%" type="primary" @click="updatePhone" :disabled="step1Button">
            确定
          </el-button>
        </el-tab-pane>
        <el-tab-pane name="step2">Config</el-tab-pane>
      </el-tabs>
    </div>
    <div class="updEmail" v-if="!show">
      <el-steps :active="1" finish-status="success" simple style="margin-top: 20px">
        <el-step title="Step 1" />
        <el-step title="Step 2" />
      </el-steps>
      <el-tabs v-model="activeName">
        <el-tab-pane name="step1" class="step1">
          <div style="display: flex">
            <el-input placeholder="输入邮箱" v-model="updEmail.email" clearable />
            <el-button type="primary" style="width: 30%" :disabled="set60sDisabled" @click="set60s('email')">
              <span class="wait_60s">
                {{ wait60sText }}
                <span v-show="wait60sShow">{{ wait60s }} </span>
              </span>
            </el-button>
          </div>
          <el-input v-model="updEmail.code" placeholder="输入验证码" clearable />
          <el-button style="width: 100%; margin-top: 10%" type="primary" @click="updateEmail" :disabled="step1Button">
            确定
          </el-button>
        </el-tab-pane>
        <el-tab-pane name="step2">Config</el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>
<script lang="ts" setup>
import { computed, reactive, ref } from 'vue';
import { useRoute } from 'vue-router';
import axios from 'axios';
import { ElMessage } from 'element-plus';
import PageHeaderVue from './PageHeader.vue';
import api from '../../api';
// import cookies from '../api/cookies';
import router from '../../router';

const route = useRoute();
const store = api.store();
const path = 'personalCenter';
const { showInfo } = route.params;
const activeName = ref('step1');
const set60sDisabled = ref(false);
const wait60s = ref(); // 60秒等待
const wait60sText = ref('获取验证码');
const wait60sShow = ref(true);
// const code = ref();
const userInfo = store.user;
const updPhone = reactive({
  phone: '',
  code: '',
});
const updEmail = reactive({
  email: '',
  code: '',
});

const show = computed(() => {
  if (showInfo === 'phone') return true;
  return false;
});
const detail = computed(() => {
  if (showInfo === 'phone') return '更新手机';
  return '更新邮箱';
});
const step1Button = computed(() => {
  if (updPhone.code === '' && updEmail.code === '') return true;
  return false;
});
function sendPHone() {
  const result = ref();
  axios
    .get(`/api/user/sendSMS?phone=${updPhone.phone}`)
    .then((res) => {
      result.value = res.data;
      console.log(result.value);
    })
    .then(() => {
      if (result.value.code === 200) ElMessage.success('验证码发送成功');
      else ElMessage.error('验证码发送失败');
    });
}
function sendEmail() {
  const result = ref();
  axios
    .get(`/api/user/sendEmail?email=${updEmail.email}`)
    .then((res) => {
      result.value = res.data;
    })
    .then(() => {
      if (result.value.code === 200) ElMessage.success('验证码发送成功');
      else ElMessage.success('验证码发送失败');
    });
}
function updatePhone() {
  if (updPhone.phone === '') {
    ElMessage.error('手机号为空');
    return;
  }
  const result = ref();
  const userData = reactive({
    phone: updPhone.phone,
    username: userInfo.username,
  });
  axios
    .post('/api/user/updUserPhone', userData)
    .then((res) => {
      result.value = res.data;
      // cookies.set('userInfo', JSON.stringify(result.value.data), '7d');
      store.setUser(result.value.data);
    })
    .then(() => {
      if (result.value.code === 200) {
        ElMessage.success('信息修改成功！');
        router.push('/personalCenter');
      } else ElMessage.error(result.value.msg);
    });
}
function updateEmail() {
  if (updEmail.email === '') {
    ElMessage.error('邮箱为空');
    return;
  }
  const result = ref();
  const userData = reactive({
    email: updEmail.email,
    username: userInfo.username,
  });
  axios
    .post('/api/user/updUserEmail', userData)
    .then((res) => {
      result.value = res.data;
      store.setUser(result.value.data);
      // cookies.set('userInfo', JSON.stringify(result.value.data), '7d');
    })
    .then(() => {
      if (result.value.code === 200) {
        ElMessage.success('信息修改成功！');
        router.push('/personalCenter');
      } else ElMessage.error(result.value.msg);
    });
}
function set60s(type: string) {
  if (updPhone.phone === '' && updEmail.email === '') ElMessage.error('请输入');
  else {
    wait60s.value = 60;
    wait60sText.value = '';
    set60sDisabled.value = !set60sDisabled.value;
    const time = setInterval(() => {
      wait60s.value -= 1;
      if (wait60s.value === 0) {
        wait60sShow.value = false;
        set60sDisabled.value = !set60sDisabled.value;
        wait60sText.value = '获取验证码';
        clearInterval(time);
      }
    }, 1000);
    switch (type) {
      case 'phone':
        sendPHone();
        break;
      case 'email':
        sendEmail();
        break;

      default:
        break;
    }
  }
}
</script>
<style scoped>
.box {
  background-color: #fff;
}
.updPhone,
.updEmail {
  width: 640px;
  height: 540px;
  /* 盒子水平垂直居中 --- start*/
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  margin: auto;
  /* 盒子水平垂直居中 --- end*/
  border: 1px solid black;
  border-radius: 3px;
  box-shadow: 0 1px 6px 0;
}
.step1,
.step2 {
  width: 80%;
  margin: auto;
  margin-top: 10%;
}
</style>
