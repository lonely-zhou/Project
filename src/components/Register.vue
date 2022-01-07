<template>
  <div class="box">
    <div class="bc"><img src="../assets/login.png" alt="" /></div>
    <div class="d1">
      <div>{{ regText }}</div>
    </div>
    <div class="d2">
      <van-cell-group inset>
        <van-field
          v-model="state.username"
          left-icon="manager"
          placeholder=" 注册用户名称"
        />
        <van-field
          v-model="state.userpass"
          left-icon="lock"
          type="password"
          placeholder="注册用户密码"
        />
        <van-field
          v-model="state.mobilenum"
          left-icon="phone"
          placeholder="注册用户电话号码"
        />
        <van-field
          v-model="state.address"
          left-icon="location"
          placeholder="送餐地址"
        />
        <van-field
          v-model="state.comment"
          left-icon="comment"
          placeholder="备注说明"
        />
      </van-cell-group>
    </div>
    <div class="d3">
      <van-button round type="default" block @click="register(state)">
        确定
      </van-button>
    </div>
  </div>
</template>
<script>
import { Field, CellGroup, Icon, Button } from "vant";
import { reactive, ref } from "vue";
import { useRouter } from "vue-router";
export default {
  name: "注册页",
  components: {
    [Field.name]: Field,
    [CellGroup.name]: CellGroup,
    [Icon.name]: Icon,
    [Button.name]: Button,
  },
  setup() {
    const axios = require("axios");
    const router = useRouter();

    // 注册信息
    const state = reactive({
      username: "",
      userpass: "",
      mobilenum: "",
      address: "",
      comment: "",
    });

    const success = ref();
    const regText = ref("注册");

    // 注册
    const register = (state) => {
      axios
        .post(
          "http://172.24.10.130:8080/foodService/userRegister.do?username=" +
            state.username +
            "&userpass=" +
            state.userpass +
            "&mobilenum=" +
            state.mobilenum +
            "&address=" +
            state.address +
            "&comment=" +
            state.comment
        )
        .then(function (response) {
          // 处理成功情况
          success.value = response.data.success;
          console.log(response);
        })
        .catch(function (error) {
          // 处理错误情况
          console.log(error);
        })
        .then(function () {
          // 总是会执行
          if (success.value == "1") {
            regText.value = "注册成功！";
            setTimeout(() => {
              router.push("/index");
            }, 3000);
          } else {
            regText.value = "注册失败！";
          }
        });
    };
    return { state, regText, register };
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