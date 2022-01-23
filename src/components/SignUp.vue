<template>
  <div class="box">
    <div class="signup-box">
      <!-- https://img.lonelyzhou.cn/RecordAndShare/logo_recordAndShare.jpg -->
      <img class="logo" src="https://img.lonelyzhou.cn/RecordAndShare/logo_recordAndShare.jpg" alt="logo" />
      <form action="" class="form-box">
        <el-input class="form-input" v-model="user.username" placeholder="请输入账号" :prefix-icon="Avatar" clearable />
        <el-input class="form-input" v-model="user.nickname" placeholder="请输入昵称" :prefix-icon="Avatar" clearable />
        <el-input
          class="form-input"
          v-model="user.password"
          placeholder="请输入密码"
          :prefix-icon="Unlock"
          clearable
          show-password
        />
        <el-input
          class="form-input"
          v-model="user.repassword"
          placeholder="再次输入密码"
          :prefix-icon="Unlock"
          clearable
          show-password
        />
      </form>
      <el-button type="primary" class="btn" @click="onSubmit" :disabled="disabled"> 注册 </el-button>
    </div>
  </div>
</template>
<script lang="ts" setup>
import { Avatar, Unlock } from '@element-plus/icons-vue'; // element ui icon
import { computed, reactive, ref } from 'vue';
// import { useRouter } from "vue-router";
import Swal from 'sweetalert2';
import axios from 'axios';

// export default {
// name: '注册',
// components: {},
// eslint-disable-next-line @typescript-eslint/explicit-module-boundary-types
// setup() {
const res = ref();
// const router = useRouter();
// 表单信息
const user = reactive({
  username: '', // 账户名
  nickname: '', // 昵称
  password: '', // 密码1
  repassword: '', // 密码2
});

// 注册请求
function onSubmit() {
  axios
    .post('api/user/signup', user)
    .then((response) => {
      // 处理成功情况
      console.log(response.data);
      res.value = response.data;
    })
    .catch((error) => {
      // 处理错误情况
      console.log(error);
    })
    .then(() => {
      // 总是会执行
      if (res.value.code === 200) {
        Swal.fire({
          title: '注册成功',
          icon: 'success',
          timer: 3000,
        });
      } else {
        Swal.fire({
          title: '失败',
          text: res.value.msg,
          icon: 'error',
          timer: 3000,
        });
      }
    });
}

// 注册按钮状态 disabled
const disabled = computed(() => {
  if (user.password === '' || user.username === '') return true;
  if (user.password === user.repassword) return false;
  return true;
});

// return {
//   Avatar,
//   Unlock,
//   user,
//   disabled,
//   onSubmit,
// };
// },
// };
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
  /* https://img.lonelyzhou.cn/RecordAndShare/wallpaper.jpg */
  background-image: url('https://img.lonelyzhou.cn/RecordAndShare/wallpaper.jpg');
  /* 背景图片 -- end */
}
.signup-box {
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
  border-radius: .1875rem;
  box-shadow: 0 .0625rem .3125rem 0;
}
.logo {
  width: 12.5rem;
  height: 9.125rem;
}
.form-box {
  margin-top: 1.25rem;
}
.form-input {
  width: 21.25rem;
  margin-bottom: .125rem;
}
.btn {
  margin-top: 3.75rem;
  width: 21.25rem;
}
</style>
