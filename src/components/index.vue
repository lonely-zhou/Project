<template>
  <div>
    <!-- 搜索 d1-->
    <div class="d1">
      <van-search
        v-model="search"
        placeholder="请输入搜索关键词"
        :shape="shape"
        @keydown.enter="toSearch"
      />
    </div>
    <!-- 轮播图 d2 -->
    <div class="d2">
      <van-swipe class="my-swipe" :autoplay="3000" indicator-color="white">
        <van-swipe-item v-for="(v, i) in carouselChart" :key="i">
          <img :src="img + v.src" alt="" />
        </van-swipe-item>
      </van-swipe>
    </div>
    <van-divider
      :style="{
        color: '#1989fa',
        borderColor: '#1989fa',
        padding: '0 1rem 0 8rem',
      }"
    />
    <!-- 店铺列表 d3 -->
    <div class="d3" v-for="(v, i) in shopList" :key="i">
      <ul>
        <li>
          <div @click="toDetail(v.shop_id)">
            <h3>{{ v.shopname }}</h3>
            <span class="sp1"> {{ v.intro }} </span>
            <van-rate v-model="v.level" color="#FF0000" />
            <!-- <van-rate v-model="v.level" disabled /> -->
          </div>
          <div @click="toDetail(v.shop_id)">
            <img :src="img + v.pic" />
          </div>
          <div>
            <span @click="collectShop(v.shop_id)">
              {{ getCollect(v.shop_id) }}
              <van-icon :id="'collectColor' + v.shop_id" name="add" />
            </span>
            <span> {{ v.address }} </span>
          </div>
        </li>
      </ul>
    </div>
  </div>
  <tabbar-page></tabbar-page>
</template>
<script>
import { Search, Swipe, SwipeItem, Icon, Rate, Divider } from "vant";
import { ref, reactive } from "vue";
import { useRouter } from "vue-router";
import TabbarPage from "./TabbarPage";
export default {
  components: {
    TabbarPage,
    [Search.name]: Search,
    [Swipe.name]: Swipe,
    [SwipeItem.name]: SwipeItem,
    [Icon.name]: Icon,
    [Rate.name]: Rate,
    [Divider.name]: Divider,
  },
  setup() {
    const axios = require("axios");
    const router = useRouter();

    // 搜索
    const search = ref("");
    const shape = ref("round");

    const shopList = ref([]);

    // 服务器图片前缀
    const img = ref("http://172.24.10.130:8080/foodService/");

    // 轮播图
    const carouselChart = ref();

    // 收藏
    const collect = reactive({
      user: JSON.parse(sessionStorage.getItem("user")),
      collected: "",
      flag: "0",
      color: "",
    });

    // 收藏---判断收藏
    const getCollect = (shop_id) => {
      axios
        .get(
          "http://172.24.10.130:8080/foodService/isCollected.do?user_id=" +
            collect.user.userid +
            "&shop_food_id=" +
            shop_id +
            "&flag=" +
            collect.flag
        )
        .then(function (response) {
          // 处理成功情况
          collect.collected = response.data.collected;
          console.log(response.data);
        })
        .catch(function (error) {
          // 处理错误情况
          console.log(error);
        })
        .then(function () {
          // 总是会执行
          const collectColor = document.getElementById(
            "collectColor" + shop_id
          );
          if (collect.collected == "1") {
            collectColor.style.color = "red";
          } else {
            collectColor.style.color = "";
          }
        });
    };

    // 收藏---收藏/取消收藏
    const collectShop = (shop_id) => {
      axios
        .get(
          "http://172.24.10.130:8080/foodService/userCollectShop.do?user_id=" +
            collect.user.userid +
            "&shop_id=" +
            shop_id
        )
        .then(function (response) {
          // 处理成功情况
          // collect.success = response.data.success;
          console.log(response.data.success);
        })
        .catch(function (error) {
          // 处理错误情况
          console.log(error);
        })
        .then(function () {
          // 总是会执行
          getCollect(shop_id);
        });
    };

    // 跳转详情页
    const toDetail = (shop_id) => {
      router.push({
        path: "shopDetail",
        name: "shopDetail",
        params: { shop_id },
      });
    };

    // 跳转搜索页
    const toSearch = () => {
      router.push({
        path: "search",
        name: "search",
        params: { search },
      });
    };

    // 轮播图
    const getCarousels = () => {
      return axios.get("http://172.24.10.130:8080/foodService/getCarousels.do");
    };

    // 店铺列表
    const getAllShops = () => {
      return axios.get("http://172.24.10.130:8080/foodService/getAllShops.do");
    };
    axios
      .all([getAllShops(), getCarousels()])
      .then(function (response) {
        // 处理成功情况
        shopList.value = response[0].data;
        carouselChart.value = response[1].data;
        console.log(response);
      })
      .catch(function (error) {
        // 处理错误情况
        console.log(error);
      })
      .then(function () {
        // 总是会执行
      });
    return {
      search,
      shape,
      shopList,
      img,
      toDetail,
      collectShop,
      carouselChart,
      collect,
      getCollect,
      toSearch,
    };
  },
};
</script>
<style scoped>
h3 {
  margin: 0;
  padding: 0;
  text-align: left;
}
.d2 img {
  /* width: 90%;
  height: 90%; */
  width: 360px;
  height: 240px;
  border: none;
  border-radius: 10%;
}
.d3 {
  margin-top: 2rem;
}
.d3 div:first-child {
  display: flex;
  flex-flow: column;
  justify-content: space-between;
}
.d3:last-child {
  margin-bottom: 4rem;
}
li {
  display: flex;
  border: 1px solid black;
  border-radius: 1rem;
  margin: 0 0.4rem 2rem 0.4rem;
  padding: 0.4rem;
}
li div:first-child {
  display: flex;
  flex-flow: column;
  width: 60%;
  margin-right: 0.3rem;
}
.sp1 {
  font-size: small;
  text-align: right;
}
li div:nth-child(2) {
  max-height: 35%;
  max-width: 35%;
}
li > div:nth-child(3) {
  width: 20%;
  display: flex;
  flex-flow: column;
  justify-content: space-between;
}
li img {
  max-width: 100%;
  max-height: 100%;
  margin: 0.5rem 1rem 0 0;
  border-radius: 1rem;
  border: 1px solid black;
}
li div:last-child span {
  font-size: small;
}
.van-icon-add {
  font-size: 2rem;
}
</style>