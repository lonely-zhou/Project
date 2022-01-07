<template>
  <div class="box">
    {{ title }}
    <!-- 详情 d1 -->
    <div class="d1">
      <van-tabs v-model:active="active2" type="card" swipeable animated>
        <van-tab title="店铺">
          <div
            class="shopList"
            v-for="(v, i) in collectionList.shopList"
            :key="i"
          >
            <van-row>
              <van-col span="20" @click="toDetail(v.shop_id)">
                <div class="d1_shop">
                  <img :src="imgurl + v.pic" alt="" :onerror="imgError" />
                  <div>
                    <span class="shopname">{{ v.shopname }}</span>
                    <span class="address">{{ v.address }}</span>
                  </div>
                </div>
              </van-col>
              <van-col span="4">
                <van-button
                  type="primary"
                  @click="delCollectionShop(v.shop_id)"
                >
                  删除
                </van-button>
              </van-col>
            </van-row>
            <van-divider />
          </div>
        </van-tab>
        <van-tab title="菜品">
          <div
            class="foodList"
            v-for="(v, i) in collectionList.foodList"
            :key="i"
          >
            <van-row>
              <van-col span="20" @click="toDishesDetail(v.shop_id, v.food_id)">
                <div class="d1_shop">
                  <img :src="imgurl + v.pic" :onerror="imgError" />
                  <div>
                    <span class="foodname">{{ v.foodname }}</span>
                    <span>￥：{{ v.price }}</span>
                  </div>
                </div>
              </van-col>
              <van-col span="4">
                <van-button
                  type="primary"
                  @click="delCollectionFood(v.food_id)"
                >
                  删除
                </van-button>
              </van-col>
            </van-row>
            <van-divider />
          </div>
        </van-tab>
      </van-tabs>
    </div>
  </div>
  <tabbar-page :activeOther="active"></tabbar-page>
</template>
<script>
import TabbarPage from "../components/TabbarPage.vue";
import { useRouter } from "vue-router";
import { reactive, ref, onMounted } from "vue";
import { Col, Row, Icon, Tab, Tabs, Divider, Button, Rate } from "vant";
export default {
  name: "收藏页",
  components: {
    TabbarPage,
    [Col.name]: Col,
    [Row.name]: Row,
    [Icon.name]: Icon,
    [Tab.name]: Tab,
    [Tabs.name]: Tabs,
    [Divider.name]: Divider,
    [Button.name]: Button,
    [Rate.name]: Rate,
  },
  setup() {
    const router = useRouter();
    const axios = require("axios");
    const user = JSON.parse(sessionStorage.getItem("user"));
    const title = "收藏列表";

    // 服务器图片前缀
    const imgurl = ref("http://172.24.10.130:8080/foodService/");

    // 图片加载失败替换图片
    const imgError = ref(
      'this.src="' + require("../assets/imgError.jpg") + '"'
    );

    //收藏状态
    const collectionState = reactive({
      shopFlag: 0,
      foodFlag: 1,
    });

    // 收藏信息
    const collectionList = reactive({
      shopList: "",
      foodList: "",
    });

    // 获取用户店铺收藏
    const getUserAllCollectionShop = () => {
      return axios.get(
        "http://172.24.10.130:8080/foodService/getAllUserCollection.do?user_id=" +
          user.userid +
          "&flag=" +
          collectionState.shopFlag
      );
    };

    // 获取用户菜品收藏
    const getUserAllCollectionFood = () => {
      return axios.get(
        "http://172.24.10.130:8080/foodService/getAllUserCollection.do?user_id=" +
          user.userid +
          "&flag=" +
          collectionState.foodFlag
      );
    };

    // 跳转店铺详情页
    const toDetail = (shop_id) => {
      router.push({
        path: "shopDetail",
        name: "shopDetail",
        params: { shop_id },
      });
    };

    // 跳转菜品详情页
    const toDishesDetail = (shop_id, food_id) => {
      router.push({
        path: "dishesDetail",
        name: "dishesDetail",
        params: { shop_id, food_id },
      });
    };

    // 取消店铺收藏
    const delCollectionShop = (shop_id) => {
      axios
        .get(
          "http://172.24.10.130:8080/foodService/userCollectShop.do?user_id=" +
            user.userid +
            "&shop_id=" +
            shop_id
        )
        .then((res) => {
          console.log(res);
          //   window.location.reload();
          getCollection();
        });
    };

    // 取消菜品收藏
    const delCollectionFood = (food_id) => {
      axios
        .get(
          "http://172.24.10.130:8080/foodService/userCollectFood.do?user_id=" +
            user.userid +
            "&food_id=" +
            food_id
        )
        .then((res) => {
          console.log(res);
          //   window.location.reload();
          getCollection();
        });
    };

    // 获取用户收藏信息
    const getCollection = onMounted(() => {
      axios
        .all([getUserAllCollectionShop(), getUserAllCollectionFood()])
        .then(function (response) {
          // 处理成功情况
          collectionList.shopList = response[0].data;
          collectionList.foodList = response[1].data;
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
    const active = 1;
    const active2 = 0;
    return {
      collectionList,
      imgurl,
      title,
      toDetail,
      toDishesDetail,
      delCollectionShop,
      delCollectionFood,
      imgError,
      active,
      active2,
    };
  },
};
</script>
<style scoped>
.box {
  margin-bottom: 3rem;
}
img {
  /* width: 124px;
  height: 75px; */
  border-radius: 1rem;
  border: 1px solid black;
}
.d1_shop,
.d1_shop > div {
  display: flex;
}
.d1_shop > div {
  flex-flow: column;
  justify-content: space-between;
  flex-grow: 1;
}
.shopList,
.foodList {
  margin-bottom: 1rem;
  margin-top: 1rem;
}
.shopname,
.foodname {
  color: DimGrey;
}
</style>