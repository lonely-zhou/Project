<template>
  <div class="box">
    <!-- 动画fade -->
    <transition name="fade">
      <!-- 登录 外盒子 -->
      <div class="loginBox" v-show="showFP">
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
            <el-checkbox v-model="loginState.rememberPassword" label="自动登录" size="large" />
          </el-col>
          <el-col :span="6">
            <el-checkbox v-model="loginState.adminLogin" label="管理员登录" size="large" />
          </el-col>
        </el-row>
        <el-button type="primary" class="btn" @click="onSubmit" :disabled="loginDisabled"> 登录 </el-button>
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
        <el-steps :active="forgotPasswordActive" finish-status="success" simple style="margin-top: 20px">
          <el-step title="确认账号"></el-step>
          <el-step title="重置密码"></el-step>
          <el-step title="重置成功"></el-step>
        </el-steps>
        <!-- step1 -->
        <div class="step1" v-show="stepShow.step1">
          <el-input v-model="forgotPasswordInfo.username" placeholder="输入账号 刷新返回登录" clearable />
          <el-button style="width: 100%; margin-top: 10%" type="primary" @click="selUserName" :disabled="step1Button">
            确定
          </el-button>
        </div>
        <!-- step2 -->
        <div class="step2" v-show="stepShow.step2">
          <el-input v-model="forgotPasswordInfo.password" placeholder="输入密码" clearable />
          <el-input v-model="forgotPasswordInfo.repassword" placeholder="确认密码" clearable />
          <div style="display: flex">
            <el-button type="primary" style="width: 50%" @click="showPOrE('phone')" v-if="showPOrEButton">
              手机
            </el-button>
            <el-button type="primary" style="width: 50%" @click="showPOrE('email')" v-if="showPOrEButton">
              邮箱
            </el-button>
            <el-input :placeholder="phoneNumber" clearable disabled v-show="showPhone" />
            <el-input :placeholder="email" clearable disabled v-show="showEmail" />
            <el-button type="primary" style="width: 30%" :disabled="set60sDisabled" @click="set60s" v-show="showButton">
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
import { computed, reactive, ref } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';
// import { Base64 } from 'js-base64';
// import { useCookies } from 'vue3-cookies';
import { ElMessage } from 'element-plus';
import api from '../api';

const user = reactive({
  username: '',
  password: '',
});
const loginState = ref({ adminLogin: false, rememberPassword: false, password: '' });
const store = api.store();
// const { cookies } = useCookies();
const showFP = ref(true);
const showPhone = ref(false);
const showEmail = ref(false);
const showButton = ref(false);
const showPOrEButton = ref();
const phoneNumber = ref();
const email = ref();
const set60sDisabled = ref(false);
const wait60s = ref(); // 60秒等待
const wait60sText = ref('获取验证码');
const wait60sShow = ref(true);
const router = useRouter();
// 登录按钮状态
const loginDisabled = computed(() => {
  if (user.password !== '' && user.username !== '') return false;
  return true;
});
// 当前步骤条
const forgotPasswordActive = ref(0);
// 步骤显示
const stepShow = reactive({
  step1: true,
  step2: false,
  step3: false,
});
// 忘记密码信息
const forgotPasswordInfo = reactive({
  username: '',
  password: '',
  repassword: '',
  code: '',
  phone: '',
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
// let temp: any;

// 登录按钮
function onSubmit() {
  const result = ref();
  // 登录
  axios
    .post(`api/user/login?isAdmin=${loginState.value.adminLogin}`, user)
    .then((res) => {
      result.value = res.data;
    })
    .then(() => {
      if (result.value.code === 200) {
        ElMessage.success({
          message: '登录成功',
          onClose: () => {
            store.setIsLogin(true);
            axios.get('api/user/isLogin').then((res) => {
              store.setIsLogin(res.data.data.isLogin);
              store.setUser(res.data.data.user);
            });
            // store.setjwtToken(result.value.data.Authorization);
            // loginState.value.password = Base64.encode(user.password);
            // cookies.set('userInfo', JSON.stringify(result.value.data.UserInfo), '7d');
            // cookies.set('loginState', JSON.stringify(loginState.value), '7d');
            // store.setLoginFlag(true);
            if (loginState.value.adminLogin) router.push('admin');
            else router.push('/index');
          },
        });
      } else {
        ElMessage.error({
          message: `登录失败 ${result.value.msg}`,
        });
      }
    });
}
// 步骤条下一步
function nextStep() {
  forgotPasswordActive.value += 1;
  if (forgotPasswordActive.value === 3) showFP.value = !showFP.value;
  if (forgotPasswordActive.value > 2) forgotPasswordActive.value = 0;
  switch (forgotPasswordActive.value) {
    case 1:
      stepShow.step1 = false;
      stepShow.step2 = true;
      break;
    case 2:
      stepShow.step2 = false;
      stepShow.step3 = true;
      break;
    default:
      stepShow.step3 = false;
      break;
  }
}
function selUserName() {
  const result = ref();
  axios
    .get(`api/user/selUser?username=${forgotPasswordInfo.username}`)
    .then((res) => {
      result.value = res.data;
      console.log(res);
    })
    .then(() => {
      if (result.value.code === 200) {
        if (result.value.data.phone !== '0' || result.value.data.email !== '0') {
          nextStep();
          email.value = result.value.data.email;
          phoneNumber.value = result.value.data.phone.replace(/(\d{3})\d{4}(\d{4})/, '$1****$2'); // 手机号中间4位加密
          if (phoneNumber.value !== '0' && email.value !== '0') showPOrEButton.value = true;
          else if (phoneNumber.value !== '0') showPhone.value = true;
          else showEmail.value = true;
        } else ElMessage.error('该账户未绑定手机号或者邮箱');
      } else {
        ElMessage.error(result.value.msg);
      }
    });
}
function set60s() {
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
  const result = ref();
  if (showPhone.value) {
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
  } else {
    axios
      .get(`api/user/sendEmail?email=${email.value}`)
      .then((res) => {
        result.value = res.data;
        console.log(result.value);
      })
      .then(() => {
        if (result.value.code === 200) ElMessage.success('验证码发送成功');
        else ElMessage.error('验证码发送失败');
      });
  }
}
function updatePassword() {
  const resultSMS = ref();
  const resultEmail = ref();
  const resultUpdate = ref();
  if (showPhone.value) {
    console.log('phone');

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
  } else {
    axios
      .get(`api/user/getEmailCode?username=${forgotPasswordInfo.username}`)
      .then((res) => {
        resultEmail.value = res.data;
      })
      .then(() => {
        if (resultEmail.value.code === 200) {
          if (forgotPasswordInfo.code !== resultEmail.value.data) ElMessage.error('验证码错误');
          axios
            .post('api/user/updPassword', forgotPasswordInfo)
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
          ElMessage.error(resultEmail.value.msg);
        }
      });
  }
}
function showPOrE(str: string) {
  if (str === 'phone') showPhone.value = true;
  if (str === 'email') showEmail.value = true;
  showPOrEButton.value = false;
  showButton.value = true;
}
// 记住密码 读取cookie
// let loginStateTemp: any;
// onMounted(() => {
//   temp = cookies.get('userInfo');
//   loginStateTemp = cookies.get('loginState');
//   if (temp !== null) {
//     if (loginStateTemp.rememberPassword) {
//       user.username = temp.username;
//       user.password = Base64.decode(loginStateTemp.password);
//       loginState.value.rememberPassword = loginStateTemp.rememberPassword;
//       loginState.value.adminLogin = loginStateTemp.adminLogin;
//     }
//   }
// });
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
