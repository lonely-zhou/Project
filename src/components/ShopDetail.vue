<template>
  <!-- 上导航 -->
  <nav-bar-page :path="path"></nav-bar-page>

  <div class="box">
    <!-- 店铺简介 d1 -->
    <div class="d1">
      <span class="d1_span">
        {{ shop_list.shopname }}
        <span @click="collectShop116(shop_list.shop_id)">
          <van-icon name="like" :color="collect.color" />
        </span>
      </span>
      <img :src="imgurl + shop_list.pic" />
      <div class="d1_top">
        <span> {{ shop_list.intro }} </span>
        <span> level: {{ shop_list.level }} </span>
      </div>
      <div class="d1_bottom">
        <span> {{ shop_list.phonenum }} </span>
        <span> {{ shop_list.address }} </span>
      </div>
    </div>

    <!-- 菜品列表 d2 -->
    <div class="d2" v-for="(v, i) in food_list" :key="i">
      <van-row>
        <van-col span="8" @click="toDishesDetail(v.shop_id, v.food_id)">
          <img :src="imgurl + v.pic" :alt="v.foodname" :onerror="imgError" />
        </van-col>
        <van-col span="8" class="d2_center">
          <span>{{ v.foodname }}</span>
          <span>{{ v.intro }}</span>
          <span>￥: {{ v.price }}</span>
        </van-col>
        <van-col span="8" class="d2_bottom">
          <span>
            {{ recommand(v.recommand) }}
            <van-icon name="good-job" :color="collect.color_recommand" />
          </span>
          <span>
            <!-- <van-stepper v-model="value" /> -->
            <!-- <van-icon v-if="num" class="add_o" name="minus" />
            <span class="add_o">
              {{ num }}
            </span>
            <van-icon class="add_o" name="plus" /> -->
          </span>
        </van-col>
      </van-row>
    </div>
    <!-- 购买 d3 -->
    <div class="d3" v-if="false">
      <van-tabbar>
        <van-row>
          <van-col span="6">数量|0</van-col>
          <van-col span="6">价格|0</van-col>
          <van-col span="6">
            <van-button type="primary" block>加入购物车</van-button>
          </van-col>
          <van-col span="6">
            <van-button type="primary" block>购买</van-button>
          </van-col>
        </van-row>
      </van-tabbar>
    </div>
  </div>
</template>
<script>
import { useRoute, useRouter } from "vue-router";
import { ref, reactive } from "vue";
import {
  Icon,
  Col,
  Row,
  Toast,
  Button,
  Tabbar,
  TabbarItem,
  Stepper,
} from "vant";
import navBarPage from "./NavBarPage.vue";
import Swal from "sweetalert2";
export default {
  name: "详情页",
  components: {
    navBarPage,
    [Icon.name]: Icon,
    [Col.name]: Col,
    [Row.name]: Row,
    [Toast.name]: Toast,
    [Button.name]: Button,
    [TabbarItem.name]: TabbarItem,
    [Tabbar.name]: Tabbar,
    [Stepper.name]: Stepper,
  },
  setup() {
    const route = useRoute();
    const router = useRouter();
    const axios = require("axios");

    // 图片加载失败替换图片
    const imgError = ref(
      'this.src="' + require("../assets/imgError.jpg") + '"'
    );

    // 获取index页 shop_id 传值
    const shop_id = route.params.shop_id;

    const shop_list = ref();
    const food_list = ref();
    const num = ref(0);
    const path = ref("index");

    // 收藏
    const collect = reactive({
      user: JSON.parse(sessionStorage.getItem("user")),
      collected: "",
      flag: "0",
      color: "",
      color_recommand: "",
      success: "",
    });
    const collectShop116 = (shop_id) => {
      axios
        .get(
          "http://172.24.10.130:8080/foodService/userCollectShop.do?user_id=" +
            collect.user.userid +
            "&shop_id=" +
            shop_id
        )
        .then(function (response) {
          // 处理成功情况
          collect.success = response.data.success;

          console.log(response.data.success);
        })
        .catch(function (error) {
          // 处理错误情况
          console.log(error);
        })
        .then(function () {
          // 总是会执行
          if (collect.success == "1") {
            if (collect.collected == "0") {
              // Toast("收藏成功");
              Swal.fire({
                title: "收藏成功",
                timer: 1000,
              });
              collect.color = "red";
              collect.collected = "1";
            } else if (collect.collected == "1") {
              Swal.fire({
                title: "取消收藏",
                timer: 1000,
              });
              collect.color = "";
              collect.collected = "0";
            }
          }
        });
    };
    
    // 服务器图片前缀
    const imgurl = ref("http://172.24.10.130:8080/foodService/");

    // 菜品列表
    const getFoodByShop = () => {
      return axios.get(
        "http://172.24.10.130:8080/foodService/getFoodByShop.do?shop_id=" +
          shop_id
      );
    };

    // 店铺详情
    const getShopById = () => {
      return axios.get(
        "http://172.24.10.130:8080/foodService/getShopById.do?shop_id=" +
          shop_id
      );
    };

    // 判断是否收藏
    const isCollected = () => {
      return axios.get(
        "http://172.24.10.130:8080/foodService/isCollected.do?user_id=" +
          collect.user.userid +
          "&shop_food_id=" +
          shop_id +
          "&flag=" +
          collect.flag
      );
    };
    axios
      .all([getShopById(), getFoodByShop(), isCollected()])
      .then(function (response) {
        // 处理成功情况
        shop_list.value = response[0].data; //店铺简介
        food_list.value = response[1].data; //菜品列表
        collect.collected = response[2].data.collected; //判断收藏
        console.log(response[2].data.collected);
      })
      .catch(function (error) {
        // 处理错误情况
        console.log(error);
      })
      .then(function () {
        // 总是会执行
        if (collect.collected == "1") {
          collect.color = "red";
        }
      });

    // 是否推荐
    const recommand = (recommand) => {
      if (recommand == "1") {
        collect.color_recommand = "red";
      }
    };

    // 跳转菜品详情页
    const toDishesDetail = (shop_id, food_id) => {
      router.push({
        path: "dishesDetail",
        name: "dishesDetail",
        params: { shop_id, food_id },
      });
    };
    return {
      shop_list,
      food_list,
      imgurl,
      num,
      path,
      imgError,
      collect,
      collectShop116,
      toDishesDetail,
      recommand,
    };
  },
};
</script>
<style scoped>
.d1,
.d1_top,
.d1_bottom,
.left,
.d2_center,
.d2_bottom {
  display: flex;
}
.d1,
.d2_center,
.d2_bottom {
  flex-flow: column;
}
.d1_top,
.d1_bottom,
.d2_bottom {
  justify-content: space-between;
}
.d1 > span:first-child {
  font-size: 1.5rem;
  text-align: left;
}
.d1_top > span:last-child {
  margin: 2rem;
}
.d1_bottom > span:first-child {
  margin-left: 2rem;
}
.d1_bottom > span:last-child {
  margin-top: 2rem;
}
.d1 img {
  width: 100%;
  height: 100%;
}
li {
  margin: 1rem;
  font-size: small;
}
.d2 img {
  /* width: 100%;
  height: 100%; */
  border-radius: 1rem;
}
.d2 {
  margin-bottom: 2rem;
  margin-top: 2rem;
  background-color: rgb(240, 239, 239);
  border-radius: 1rem;
}
.d2_center span:nth-child(1) {
  font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
  color: rgb(14, 80, 58);
  margin-bottom: 0.5rem;
}
.d2_center span:nth-child(2) {
  font-size: small;
  flex-grow: 1;
  text-indent: 2em;
}
.d2_center span:nth-child(3) {
  color: red;
}
.d2_bottom > span:first-child {
  font-size: 2rem;
  margin-top: 1rem;
}
.d1_span {
  display: flex;
  justify-content: space-between;
  margin-right: 1rem;
}
.add_o {
  font-size: 1.5rem;
  margin-right: 1rem;
}
.d2_bottom span:last-child {
  text-align: right;
}
.van-tabbar {
  flex-direction: column;
}
.d3 {
  margin-top: 4rem;
}
</style>