<template>
  <nav-bar-page :title="title" :flag="flag"></nav-bar-page>
  <div class="box">
    <div class="d1">
      <span>购买人：{{ user.username }}</span>
      <span>配送地址：{{ userAddress[0].address }}</span>
      <span>送达时间：{{ time2 }}</span>
    </div>
    <div class="d2">
      <span>{{ foodInfo.foodname }}</span>
      <span>￥：{{ foodInfo.price }}</span>
      <span><van-stepper v-model="num" /></span>
      <span>总计：{{ getPrice }}</span>
      <span> 下单时间：{{ time }} </span>
    </div>
    <van-button plain type="primary" block @click="insertOrder()">
      确定
    </van-button>
  </div>
</template>
<script>
import NavBarPage from "../components/NavBarPage.vue";
import { ref, computed } from "vue";
import { Stepper, Button } from "vant";
import { useRoute, useRouter } from "vue-router";
import Swal from "sweetalert2";
export default {
  name: "购买页面",
  components: {
    NavBarPage,
    [Stepper.name]: Stepper,
    [Button.name]: Button,
  },
  setup() {
    const axios = require("axios");
    const route = useRoute();
    const router = useRouter();
    const user = JSON.parse(sessionStorage.getItem("user"));
    const userAddress = ref();
    const food_id = route.params.food_id;
    const foodInfo = ref();
    const title = "购买";
    const flag = 1;

    // 获取用户地址
    const getAddressesByUser = () => {
      return axios.get(
        "http://172.24.10.130:8080/foodService/getAddressesByUser.do?user_id=" +
          user.userid
      );
    };

    // 菜品详情
    const getAllCommentsByFood = () => {
      return axios.get(
        "http://172.24.10.130:8080/foodService/getFoodById.do?food_id=" +
          food_id
      );
    };

    // 直接购买
    const insertOrder = () => {
      return axios
        .get(
          "http://172.24.10.130:8080/foodService/insertOrder.do?user_id=" +
            user.userid +
            "&food_id=" +
            food_id +
            "&num=" +
            num.value +
            "&sum=" +
            getPrice.value +
            "&suggesttime=" +
            time2.value +
            "&address_id=" +
            userAddress.value[0].address_id
        )
        .then(function (response) {
          // 处理成功情况
          const shop_id = foodInfo.value.shop_id;
          Swal.fire({ title: "购买成功" });
          router.push({
            path: "shopDetail",
            name: "shopDetail",
            params: { shop_id },
          });
          console.log(response);
        })
        .catch(function (error) {
          // 处理错误情况
          console.log(error);
        })
        .then(function () {
          // 总是会执行
        });
    };
    axios
      .all([getAddressesByUser(), getAllCommentsByFood()])
      .then(function (response) {
        // 处理成功情况
        userAddress.value = response[0].data;
        foodInfo.value = response[1].data;
        console.log(response);
      })
      .catch(function (error) {
        // 处理错误情况
        console.log(error);
      })
      .then(function () {
        // 总是会执行
      });
    let num = ref(1);
    const getPrice = computed(() => {
      return foodInfo.value.price * num.value;
    });
    const time = computed(() => {
      const date = new Date();
      let h = date.getHours();
      let m = date.getMinutes();
      if (m < 10) {
        m = "0" + m;
      }
      return h + ": " + m;
    });
    const time2 = computed(() => {
      const date = new Date();
      let h = date.getHours();
      let m = date.getMinutes();
      m += 30;
      if (m > 60) {
        m -= 60;
        h += 1;
      }
      if (m < 10) {
        m = "0" + m;
      }
      let h2 = h + 1;
      return h + ":" + m + "-" + h2 + ":" + m;
    });
    return {
      user,
      userAddress,
      foodInfo,
      getPrice,
      num,
      time,
      time2,
      title,
      flag,
      insertOrder,
    };
  },
};
</script>
<style scoped>
.d1,
.d2 {
  display: flex;
  flex-flow: column;
  text-align: left;
  margin-bottom: 3rem;
}
</style>