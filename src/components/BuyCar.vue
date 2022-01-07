<template>
  <div class="box">
    <nav-bar-page :title="title" :path="path" />
    <div v-for="(v, i) in buyCarList" :key="i">
      <van-row gutter="3">
        <van-col span="8">
          <van-row>
            <van-col span="24"><img :src="imgurl + v.pic" alt="" /></van-col>
            <van-col span="24">
              <span> {{ v.foodname }} </span>
            </van-col>
          </van-row>
        </van-col>
        <van-col span="8">
          <span class="price">￥ {{ v.price }}</span>
          <span>
            <van-button plain type="primary" block @click="toBuy(v.item_id)">
              购买
            </van-button>
          </span>
        </van-col>
        <van-col span="8">
          <span class="num">{{ v.num }} 份</span>
          <span>
            <van-button
              plain
              type="primary"
              block
              @click="deleteFood(v.item_id)"
            >
              删除
            </van-button>
          </span>
        </van-col>
      </van-row>
      <van-divider />
    </div>
  </div>
</template>
<script>
import NavBarPage from "../components/NavBarPage.vue";
import { Col, Row, Divider, Button } from "vant";
import { ref, onMounted, computed } from "vue";
export default {
  name: "购物车页",
  components: {
    NavBarPage,
    [Col.name]: Col,
    [Row.name]: Row,
    [Divider.name]: Divider,
    [Button.name]: Button,
  },
  setup() {
    const axios = require("axios");
    const user = JSON.parse(sessionStorage.getItem("user"));
    const buyCarList = ref();
    const userAddress = ref();

    // 服务器图片前缀
    const imgurl = ref("http://172.24.10.130:8080/foodService/");

    // 从购物车 直接购买
    const toBuy = (item_id) => {
      const foodList = ref();
      axios
        .get(
          "http://172.24.10.130:8080/foodService/getItemsFromCart.do?items=" +
            item_id
        )
        .then((res) => {
          foodList.value = res.data;
          console.log(res);
        })
        .then(() => {
          // 直接购买
          axios
            .get(
              "http://172.24.10.130:8080/foodService/insertOrder2.do?user_id=" +
                user.userid +
                "&address_id=" +
                userAddress.value[0].address_id +
                "&sum=" +
                foodList.value[0].num +
                "&suggesttime=" +
                time2.value +
                "&items=" +
                item_id
            )
            .then((res) => {
              console.log(res);
            });
        });
    };

    // 获取当前时间 +30
    const time2 = computed(() => {
      const date = new Date();
      let h = date.getHours();
      let m = date.getMinutes();
      m += 30;
      if (m > 60) {
        m -= 60;
        h += 1;
      }
      // 小于10 补 0
      if (m < 10) {
        m = "0" + m;
      }
      let h2 = h + 1;
      return h + ":" + m + "-" + h2 + ":" + m;
    });
    // 从购物车删除
    const deleteFood = (item_id) => {
      axios
        .get(
          "http://172.24.10.130:8080/foodService/deleteCartItem.do?item_id=" +
            item_id
        )
        .then(() => {
          getMyCartByUser();
        });
    };

    // 获取用户地址
    const getAddressesByUser = () => {
      return axios.get(
        "http://172.24.10.130:8080/foodService/getAddressesByUser.do?user_id=" +
          user.userid
      );
    };

    // 获取购物车数据
    const getMyCartByUser = () => {
      return axios.get(
        "http://172.24.10.130:8080/foodService/getMyCartByUser.do?user_id=" +
          user.userid
      );
    };

    onMounted(() => {
      axios
        .all([getMyCartByUser(), getAddressesByUser()])
        .then(function (response) {
          // 处理成功情况
          buyCarList.value = response[0].data;
          userAddress.value = response[1].data;
          console.log(response);
        })
        .catch(function (error) {
          // 处理错误情况
          console.log(error);
        })
        .then(function () {
          // 总是会执行
        });
    });

    const title = "购物车";
    const path = "index";
    return { title, path, buyCarList, imgurl, toBuy, deleteFood };
  },
};
</script>
<style scoped>
img {
  border-radius: 1rem;
}
.num,
.price {
  font-size: 1.5rem;
  color: rgb(153, 159, 165);
}
</style>