<template>
  <div class="box">
    <div class="bc"><img src="../assets/login.png" alt="" /></div>
    <div class="d1">
      <div>{{ loginText }}</div>
    </div>
    <div class="d2">
      <van-cell-group inset>
        <van-field
          v-model="user.username"
          left-icon="manager"
          placeholder="用户系统登录名称"
        />
        <van-field
          v-model="user.password"
          left-icon="lock"
          type="password"
          placeholder="用户系统登录密码"
        />
      </van-cell-group>
    </div>
    <div class="d3">
      <van-button round type="default" block @click="login(user)">
        确定
      </van-button>
      <van-button round type="default" block @click="register()">
        注册
      </van-button>
    </div>
  </div>
</template>
<script>
import { Field, CellGroup, Icon, Button } from "vant";
import { reactive, ref } from "vue";
import { useRouter } from "vue-router";
export default {
  name: "登录页",
  components: {
    [Field.name]: Field,
    [CellGroup.name]: CellGroup,
    [Icon.name]: Icon,
    [Button.name]: Button,
  },
  setup() {
    const axios = require("axios");
    const router = useRouter();

    // 用户信息
    const user = reactive({
      username: "",
      password: "",
      userid: "",
    });

    const loginText = ref("登录");

    // 登录
    const login = (user) => {
      axios
        .get(
          "http://172.24.10.130:8080/foodService/userLogin.do?username=" +
            user.username +
            "&userpass=" +
            user.password
        )
        .then(function (response) {
          // 处理成功情况
          user.userid = response.data.userid;
          console.log(response);
        })
        .catch(function (error) {
          // 处理错误情况
          console.log(error);
        })
        .then(function () {
          // 总是会执行
          if (user.userid != "0") {
            sessionStorage.setItem("user", JSON.stringify(user));
            router.push("/index");
          } else {
            loginText.value = "用户名或密码错误！";
          }
        });
    };

    // 跳转注册页
    const register = () => {
      router.push("/register");
    };

    return { user, login, loginText, register };
  },
};
</script>
<style scoped>
.d1 div {
  width: 80%;
  border: 1px solid black;
  border-radius: 1rem;
  min-height: 3rem;
  line-height: 3rem;
  font-size: 1.5rem;
  margin: 1rem 0 6rem 0;
  text-shadow: 8px 4px 8px black;
  letter-spacing: 0.2rem;
  font-family: kaiti;
}
.d2 {
  margin-bottom: 6rem;
}
.d1,
.d3 {
  display: flex;
  justify-content: center;
}
.bc img {
  width: 100%;
  max-height: 10rem;
  border-radius: 8rem 4rem 5rem 4rem;
  opacity: 0.6;
}
</style>