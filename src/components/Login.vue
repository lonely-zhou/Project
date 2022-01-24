<template>
  <div class="box">
    <!-- 动画fade -->
    <transition name="fade">
      <!-- 登录 外盒子 -->
      <div class="loginBox" v-show="showFP">
        <!-- https://img.lonelyzhou.cn/RecordAndShare/logo_recordAndShare.jpg -->
        <img class="logoImg" src="https://img.lonelyzhou.cn/RecordAndShare/logo_recordAndShare.jpg" alt="logo" />
        <form action="" class="form-box">
          <el-input
            class="form-input"
            v-model="user.username"
            placeholder="请输入账号"
            :prefix-icon="Avatar"
            clearable
          />
          <el-input
            class="form-input"
            v-model="user.password"
            placeholder="请输入密码"
            :prefix-icon="Unlock"
            clearable
            show-password
          />
        </form>
        <el-row :gutter="20" style="margin-top: 10px">
          <el-col :span="6" :offset="6">
            <el-checkbox v-model="userCookie.rememberPassword" label="自动登录" size="large" />
          </el-col>
          <el-col :span="6">
            <el-checkbox v-model="userCookie.adminLogin" label="管理员登录" size="large" />
          </el-col>
        </el-row>
        <el-button type="primary" class="btn" @click="onSubmit" :disabled="disabled"> 登录 </el-button>
        <el-row :gutter="20" style="margin-top: 30px; user-select: none">
          <el-col :span="6" :offset="6">
            <router-link to="/signup">注册账号</router-link>
          </el-col>
          <el-col :span="6" @click="showFP = !showFP" style="cursor: pointer">忘记密码</el-col>
        </el-row>
      </div>
    </transition>
    <!-- 动画 fade -->
    <transition name="fade">
      <!-- 忘记密码 外盒子 -->
      <div class="forgotPasswordBox" v-show="!showFP">
        <el-steps :active="active" finish-status="success" simple style="margin-top: 20px">
          <el-step title="确认账号"></el-step>
          <el-step title="重置密码"></el-step>
          <el-step title="重置成功"></el-step>
        </el-steps>
        <!-- step1 -->
        <div class="step1" v-show="stepShow.step1">
          <el-input v-model="forgotPasswordInfo.username" placeholder="输入账号 刷新返回登录" clearable />
          <el-button style="width: 100%; margin-top: 10%" type="primary" @click="findUsername" :disabled="step1Button">
            确定
          </el-button>
        </div>
        <!-- step2 -->
        <div class="step2" v-show="stepShow.step2">
          <el-input v-model="forgotPasswordInfo.password" placeholder="输入密码" clearable />
          <el-input v-model="forgotPasswordInfo.repassword" placeholder="确认密码" clearable />
          <div style="display: flex">
            <el-input :placeholder="phoneNumber" clearable disabled />
            <el-button type="primary" style="width: 30%" :disabled="buttonDisabled.set60sDisabled" @click="set60s">
              <span class="wait_60s">
                {{ wait60sText }}
                <span v-show="wait60sShow">{{ wait60s }} </span>
              </span>
            </el-button>
          </div>
          <el-input v-model="forgotPasswordInfo.code" placeholder="输入验证码" clearable />
          <el-button
            style="width: 100%; margin-top: 10%"
            type="primary"
            @click="updatePassword"
            :disabled="step2Button"
          >
            确定
          </el-button>
        </div>
        <!-- step3 -->
        <div class="step3" v-show="stepShow.step3">
          成功<el-button style="width: 100%; margin-top: 10%" type="primary" @click="nextStep">返回登录</el-button>
        </div>
      </div>
    </transition>
  </div>
</template>
<script lang="ts" setup>
// 登录页
import { Avatar, Unlock } from '@element-plus/icons-vue'; // element ui icon
// eslint-disable-next-line object-curly-newline
import { computed, reactive, ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';
import { Base64 } from 'js-base64';
import { useCookies } from 'vue3-cookies';
import { ElMessage } from 'element-plus';

const { cookies } = useCookies();
const router = useRouter();
const loginCode = ref();
const phoneNumber = ref();
// 表单信息
const user = reactive({
  username: '', // 账户名
  password: '', // 密码
});
// 用户cookie信息
const userCookie = reactive({
  username: '',
  password: '',
  rememberPassword: false,
  adminLogin: false,
  circleUrl: '',
  phone: '',
});
let temp: any;
const showFP = ref(true);
// 当前步骤条
const active = ref(0);
// 忘记密码信息
const forgotPasswordInfo = reactive({
  username: '',
  password: '',
  repassword: '',
  code: '',
  phone: '',
});
// 步骤显示
const stepShow = reactive({
  step1: true,
  step2: false,
  step3: false,
});
// 步骤条下一步
function nextStep() {
  active.value += 1;
  if (active.value === 3) showFP.value = !showFP.value;
  if (active.value > 2) active.value = 0;
  switch (active.value) {
    case 0:
      stepShow.step1 = true;
      stepShow.step2 = false;
      stepShow.step3 = false;
      break;
    case 1:
      stepShow.step1 = false;
      stepShow.step2 = true;
      stepShow.step3 = false;
      break;
    case 2:
      stepShow.step1 = false;
      stepShow.step2 = false;
      stepShow.step3 = true;
      break;
    default:
      break;
  }
}
// step1 查找用户
function findUsername() {
  const result = ref();
  axios
    .get(`api/user/findUsername?username=${forgotPasswordInfo.username}`)
    .then((res) => {
      result.value = res.data;
      phoneNumber.value = res.data.data;
      forgotPasswordInfo.phone = res.data.data;
    })
    .then(() => {
      if (result.value.code === 200) {
        phoneNumber.value = phoneNumber.value.replace(/(\d{3})\d{4}(\d{4})/, '$1****$2'); // 手机号中间4位加密
        if (result.value.data !== '0') nextStep();
        else ElMessage.error('该账户未绑定手机号');
      } else {
        ElMessage.error(result.value.msg);
      }
    });
}
// step2 重置密码
function updatePassword() {
  const resultSMS = ref();
  const resultUpdate = ref();
  axios
    .get(`api/user/getSMSCode?username=${forgotPasswordInfo.username}`)
    .then((res) => {
      resultSMS.value = res.data;
    })
    .then(() => {
      if (resultSMS.value.code === 200) {
        if (forgotPasswordInfo.code !== resultSMS.value.data) ElMessage.error('验证码错误');
        axios
          .post('api/user/updatePassword', forgotPasswordInfo)
          .then((res) => {
            resultUpdate.value = res.data;
          })
          .then(() => {
            if (resultUpdate.value.code === 200) {
              ElMessage.success('密码修改成功');
              nextStep();
            } else ElMessage.error('密码修改失败');
          });
      } else {
        ElMessage.error(resultSMS.value.msg);
      }
    });
}
const wait60s = ref(); // 60秒等待
const wait60sText = ref('获取验证码');
const wait60sShow = ref(true);
const buttonDisabled = reactive({
  set60sDisabled: false,
});
// 60s倒计时 发送验证码
function set60s() {
  wait60s.value = 60;
  wait60sText.value = '';
  buttonDisabled.set60sDisabled = !buttonDisabled.set60sDisabled;
  const time = setInterval(() => {
    wait60s.value -= 1;
    if (wait60s.value === 0) {
      wait60sShow.value = false;
      buttonDisabled.set60sDisabled = !buttonDisabled.set60sDisabled;
      wait60sText.value = '获取验证码';
      clearInterval(time);
    }
  }, 1000);
  const result = ref();
  axios
    .get(`api/user/sendSMSCode?username=${forgotPasswordInfo.username}`)
    .then((res) => {
      result.value = res.data;
      console.log(result.value);
    })
    .then(() => {
      if (result.value.code === 200) ElMessage.success('验证码发送成功');
      else ElMessage.error('验证码发送失败');
    });
}
// 读取cookie
onMounted(() => {
  temp = cookies.get('user');
  if (temp !== null) {
    if (temp.rememberPassword) {
      user.username = temp.username;
      user.password = Base64.decode(temp.password);
      userCookie.rememberPassword = temp.rememberPassword;
      userCookie.adminLogin = temp.adminLogin;
    }
  }
});
// 登录请求
function onSubmit() {
  axios
    .get(`api/user/login?username=${user.username}&password=${user.password}`)
    .then((response) => {
      // console.log(response.data);
      loginCode.value = response.data;
      userCookie.circleUrl = response.data.data.avatar_url;
    })
    .catch((error) => {
      console.log(error);
    })
    .then(() => {
      if (loginCode.value.code === 200) {
        ElMessage.success({
          message: '登录成功',
          onClose: () => {
            userCookie.username = user.username;
            userCookie.password = Base64.encode(user.password);
            userCookie.phone = loginCode.value.data.phone;
            cookies.set('user', JSON.stringify(userCookie), '7d');
            cookies.set('loginFlag', 'true', '1d');
            router.push('/index');
          },
        });
      } else {
        ElMessage.error({
          message: `登录失败 ${loginCode.value.msg}`,
        });
      }
    });
}

// 登录按钮状态
const disabled = computed(() => {
  if (user.password !== '' && user.username !== '') return false;
  return true;
});
// 忘记密码step1按钮状态
const step1Button = computed(() => {
  if (forgotPasswordInfo.username !== '') return false;
  return true;
});
// 忘记密码step2按钮状态
const step2Button = computed(() => {
  if (forgotPasswordInfo.code !== '') return false;
  return true;
});
</script>
<style scoped>
.box {
  /* 背景图片 -- start */
  position: fixed;
  width: 100%;
  height: 100%;
  top: 0;
  right: 0;
  background-size: cover;
  /* http://img.lonelyzhou.cn/RecordAndShare/wallpaper.jpg */
  background-image: url('https://img.lonelyzhou.cn/RecordAndShare/wallpaper.jpg');
  /* 背景图片 -- end */
}
.loginBox,
.forgotPasswordBox {
  width: 31.625rem;
  height: 27.5rem;
  background-color: #fff;
  /* 盒子水平垂直居中 --- start*/
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  margin: auto;
  /* 盒子水平垂直居中 --- end*/
  border-radius: 0.1875rem;
  box-shadow: 0 0.0625rem 0.3125rem 0;
}
.logoImg {
  width: 12.5rem;
  height: 9.125rem;
}
.form-box {
  margin-top: 1.25rem;
}
.form-input {
  width: 21.25rem;
  margin-bottom: 0.125rem;
}
.btn {
  margin-top: 1.25rem;
  width: 21.25rem;
}
.text {
  width: 21.25rem;
}
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
.step1,
.step2,
.step3 {
  width: 80%;
  margin: auto;
  margin-top: 10%;
}
</style>
