<template>
  <div class="box">
    <div class="title">
      <van-row>
        <van-col span="24">个人中心</van-col>
      </van-row>
    </div>

    <div class="userInfo">
      <van-row>
        <van-col span="6">
          <van-image
            round
            width="4rem"
            height="4rem"
            src="https://z3.ax1x.com/2021/05/22/gLRhkT.jpg"
          />
        </van-col>
        <van-col span="18" class="userInfo_um">
          <van-col span="24">
            <span>{{ userInfo.username }}</span>
          </van-col>
          <van-col span="24">
            <span>{{ userInfo.mobilenum }}</span>
          </van-col>
        </van-col>
      </van-row>
    </div>

    <div class="setting">
      <ul>
        <li @click="toUserAllOrders">
          <span>我的订单</span><van-icon name="arrow" />
        </li>
        <van-divider />
        <li @click="toComments">
          <span>我的评论</span><van-icon name="arrow" />
        </li>
        <van-divider class="sp" />
        <li @click="toCollection">
          <span>我的收藏</span><van-icon name="arrow" />
        </li>
        <van-divider />
        <van-cell is-link @click="showPopup">
          <li><span>修改信息</span><van-icon name="arrow" /></li>
          <van-divider class="sp" />
        </van-cell>
        <li @click="logOut"><span>退出登录</span><van-icon name="arrow" /></li>
        <van-divider />
      </ul>
    </div>

    <!-- 修改用户信息 -->
    <div>
      <van-popup v-model:show="show" position="top" :style="{ height: '80%' }">
        <div class="updataUser">
          <van-row>
            <van-cell-group inset>
              <van-col span="24">
                <van-field
                  v-model="getUser.username"
                  label="用户名"
                  :placeholder="userInfo.username"
                />
              </van-col>
              <van-col span="24">
                <van-field
                  v-model="getUser.userpass"
                  label="密码"
                  type="password"
                  placeholder="请输入密码"
                />
              </van-col>
              <van-col span="24">
                <van-field
                  v-model="getUser.mobilenum"
                  label="电话"
                  :placeholder="userInfo.mobilenum"
                  type="number"
                />
              </van-col>
              <van-col span="24">
                <van-field
                  v-model="getUser.address"
                  label="地址"
                  :placeholder="userInfo.address"
                />
              </van-col>
            </van-cell-group>
          </van-row>
          <van-button type="primary" block @click="updateUserById">
            确定
          </van-button>
        </div>
      </van-popup>
    </div>
  </div>
  <tabbar-page :activeOther="active"></tabbar-page>
</template>
<script>
const axios = require("axios");
import TabbarPage from "../components/TabbarPage.vue";
import {
  Col,
  Row,
  Image as VanImage,
  Icon,
  Divider,
  Popup,
  Field,
  CellGroup,
  Button,
} from "vant";
import { reactive, ref, onMounted } from "vue";
import Swal from "sweetalert2";
import { useRouter } from "vue-router";
export default {
  name: "个人中心页",
  components: {
    TabbarPage,
    [Col.name]: Col,
    [Row.name]: Row,
    [VanImage.name]: VanImage,
    [Icon.name]: Icon,
    [Divider.name]: Divider,
    [Popup.name]: Popup,
    [Field.name]: Field,
    [CellGroup.name]: CellGroup,
    [Button.name]: Button,
  },
  setup() {
    const router = useRouter();
    // 获取userid
    const user = JSON.parse(sessionStorage.getItem("user"));

    const userInfo = ref({});
    const getUser = reactive({
      username: "",
      userpass: "",
      mobilenum: "",
      address: "",
    });

    // 退出登录
    const logOut = () => {
      sessionStorage.removeItem("user");
      router.push("login");
    };
    const updateUserById = () => {
      axios
        .get(
          "http://172.24.10.130:8080/foodService/updateUserById.do?user_id=" +
            user.userid +
            "&username=" +
            getUser.username +
            "&userpass=" +
            getUser.userpass +
            "&mobilenum=" +
            getUser.mobilenum +
            "&address=" +
            getUser.address
        )
        .then((res) => {
          show.value = false;
          getUserInfo();
          Swal.fire({ title: "修改成功！", type: "success", timer: 1000 });
          console.log(res);
        });
    };
    //   获取用户信息
    const getUserInfo = onMounted(() => {
      axios
        .get(
          "http://172.24.10.130:8080/foodService/getUserById.do?user_id=" +
            user.userid
        )
        .then(function (response) {
          // 处理成功情况
          console.log(response);
          userInfo.value = response.data;
        })
        .catch(function (error) {
          // 处理错误情况
          console.log(error);
        })
        .then(function () {
          // 总是会执行
        });
    });
    const active = 3;
    const show = ref(false);
    const showPopup = () => {
      show.value = true;
    };

    // 跳转收藏页
    const toCollection = () => {
      router.push("userAllCollection");
    };

    // 跳转评论页
    const toComments = () => {
      router.push("comments");
    };

    // 跳转订单页
    const toUserAllOrders = () => {
      router.push("userAllOrders");
    };
    return {
      toCollection,
      active,
      user,
      userInfo,
      showPopup,
      show,
      getUser,
      updateUserById,
      toComments,
      toUserAllOrders,
      logOut,
    };
  },
};
</script>
<style scoped>
.title {
  font-size: 1.5rem;
}
.userInfo_um {
  text-align: left;
}
.userInfo_um div {
  margin-top: 1rem;
}
.setting {
  margin-top: 6rem;
}
.setting li {
  display: flex;
  justify-content: space-between;
  margin: 0 1rem;
}
.sp {
  margin-bottom: 3rem;
}
.updataUser {
  margin-top: 3rem;
}
</style>