<template>
  <div class="box">
    <!-- 上导航 -->
    <nav-bar-page :path="shopInfo.path"></nav-bar-page>

    <!-- 菜品图片 -->
    <div class="d1">
      <img
        :src="imgurl + shopInfo.foodInfo.pic"
        alt="v.foodname"
        :onerror="imgError"
      />
    </div>

    <!-- 菜品详情 -->
    <div class="d2">
      <van-row>
        <van-col span="12" class="d2_v1">
          <span @click="collectShop()">
            <span>{{ shopInfo.foodInfo.foodname }}</span>
            <van-icon name="like" :color="collect.color" />
          </span>
          <span>{{ shopInfo.foodInfo.intro }}</span>
        </van-col>
        <van-col span="12" class="d2_v2">
          <span>价格：{{ shopInfo.foodInfo.price }}</span>
          <span>推荐：<van-icon name="smile" /></span>
        </van-col>
      </van-row>
    </div>
    <!-- 评价 d3 -->
    <div class="d3">
      <span>评论</span>
      <ul v-for="(v, i) in page.pageShow" :key="i">
        <li>
          <van-row>
            <van-col span="24">
              <van-row>
                <van-col span="12">
                  <span>用户：{{ v.username }} </span>
                </van-col>
                <van-col span="12">
                  <span>下单时间：{{ v.order_time }} </span>
                </van-col>
              </van-row>
            </van-col>
            <van-col span="24">
              <van-row>
                <van-col span="12">
                  <span>评论：{{ v.content }}</span>
                </van-col>
                <van-col span="12">
                  <span>评论时间：{{ v.comment_time }}</span>
                </van-col>
              </van-row>
            </van-col>
          </van-row>
        </li>
        <van-divider />
      </ul>
    </div>
    <div>
      <van-row>
        <van-col span="12">
          <van-button block @click="up()"> 上一页 </van-button>
        </van-col>
        <van-col span="12">
          <van-button block @click="down()"> 下一页 </van-button>
        </van-col>
      </van-row>
    </div>

    <van-tabbar v-model="active" placeholder>
      <van-button type="primary" block @click="showPopup()">
        加入购物车
      </van-button>
      <van-button type="primary" block @click="toBuy(shopInfo.food_id)">
        购买
      </van-button>
    </van-tabbar>

    <!-- 购买弹出 -->
    <van-popup v-model:show="show" position="bottom" :style="{ height: '30%' }">
      <van-row>
        <van-col span="24">
          <van-cell-group inset>
            <van-field
              v-model="buyCarNum"
              label="数量"
              placeholder="请输入购买数量"
            />
          </van-cell-group>
          <van-button type="primary" block @click="addCart()">
            确定
          </van-button>
        </van-col>
      </van-row>
    </van-popup>
  </div>
</template>
<script>
import navBarPage from "./NavBarPage.vue";
import { ref, reactive, onMounted } from "vue";
import {
  Col,
  Row,
  Icon,
  Toast,
  Button,
  Tabbar,
  TabbarItem,
  Divider,
  Pagination,
  Popup,
  Field,
  CellGroup,
} from "vant";
import { useRoute, useRouter } from "vue-router";
import Swal from "sweetalert2";
export default {
  name: "菜品详情",
  components: {
    navBarPage,
    [Col.name]: Col,
    [Row.name]: Row,
    [Icon.name]: Icon,
    [Toast.name]: Toast,
    [Button.name]: Button,
    [Tabbar.name]: Tabbar,
    [TabbarItem.name]: TabbarItem,
    [Divider.name]: Divider,
    [Pagination.name]: Pagination,
    [Popup.name]: Popup,
    [Field.name]: Field,
    [CellGroup.name]: CellGroup,
  },
  setup() {
    const route = useRoute();
    const router = useRouter();
    const axios = require("axios");

    const shopInfo = reactive({
      shop_id: route.params.shop_id,
      food_id: route.params.food_id,
      foodInfo: "",
      path: "shopDetail",
      comments: "",
    });
    const buyCarNum = ref();
    const collect = reactive({
      user: JSON.parse(sessionStorage.getItem("user")),
      collected: "",
      flag: "1",
      color: "",
      success: "",
    });

    // 服务器图片前缀
    const imgurl = ref("http://172.24.10.130:8080/foodService");

    // 图片加载失败替换图片
    const imgError = ref(
      'this.src="' + require("../assets/imgError.jpg") + '"'
    );
    const getFoodById = () => {
      return axios.get(
        "http://172.24.10.130:8080/foodService/getFoodById.do?food_id=" +
          shopInfo.food_id
      );
    };
    // 评论信息
    const getAllCommentsByFood = () => {
      return axios.get(
        "http://172.24.10.130:8080/foodService/getAllCommentsByFood.do?food_id=" +
          shopInfo.food_id
      );
    };

    // 判断是否收藏
    const isCollected = () => {
      return axios.get(
        "http://172.24.10.130:8080/foodService/isCollected.do?user_id=" +
          collect.user.userid +
          "&shop_food_id=" +
          shopInfo.food_id +
          "&flag=" +
          collect.flag
      );
    };

    // 收藏店铺
    const collectShop = () => {
      axios
        .get(
          "http://172.24.10.130:8080/foodService/userCollectFood.do?user_id=" +
            collect.user.userid +
            "&food_id=" +
            shopInfo.food_id
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
              // Toast("取消收藏");
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

    onMounted(() => {
      // 菜品信息
      axios
        .all([getFoodById(), getAllCommentsByFood(), isCollected()])
        .then(function (response) {
          shopInfo.foodInfo = response[0].data;
          shopInfo.comments = response[1].data;
          collect.collected = response[2].data.collected;
          console.log(response[2].data);
        })
        .catch(function (error) {
          console.log(error);
        })
        .then(function () {
          if (collect.collected == "1") {
            collect.color = "red";
          }
          pageList();
        });
    });

    // 跳转至 购买 页面
    const toBuy = (food_id) => {
      router.push({
        path: "buy",
        name: "buy",
        params: { food_id },
      });
    };

    // 分页
    const page = reactive({
      pageData: [], //评论数据
      pageSize: 9, //评论每页显示最大数
      pageNum: 1, //页数
      pageShow: "", //显示的评数据
      currentPage: 0, //当前页下标
    });
    const pageList = () => {
      page.pageNum = Math.ceil(shopInfo.comments.length / page.pageSize) || 1;
      for (let i = 0; i < page.pageNum; i++) {
        page.pageData[i] = shopInfo.comments.slice(
          page.pageSize * i,
          page.pageSize * (i + 1)
        );
      }
      // 获取到数据后显示第一页内容
      page.pageShow = page.pageData[page.currentPage];
    };

    // 评论翻页 上一页
    const up = () => {
      if (page.currentPage != 0) {
        page.currentPage--;
      } else {
        page.currentPage = 0;
      }
      page.pageShow = page.pageData[page.currentPage];
    };

    // 评论翻页 下一页
    const down = () => {
      if (page.currentPage != page.pageNum) {
        page.currentPage++;
      } else {
        page.currentPage = page.pageNum;
      }
      page.pageShow = page.pageData[page.currentPage];
    };

    // 加入购物车
    const addCart = () => {
      axios
        .get(
          "http://172.24.10.130:8080/foodService/addCart.do?user_id=" +
            collect.user.userid +
            "&food_id=" +
            shopInfo.food_id +
            "&num=" +
            buyCarNum.value
        )
        .then(() => {
          show.value = false;
        });
    };
    const show = ref(false);
    const showPopup = () => {
      show.value = true;
    };
    return {
      show,
      showPopup,
      page,
      shopInfo,
      imgurl,
      collect,
      collectShop,
      toBuy,
      imgError,
      addCart,
      buyCarNum,
      up,
      down,
    };
  },
};
</script>
<style scoped>
.d1 {
  margin: 0.3rem 1rem;
}
.d1 img {
  width: 100%;
  height: 15rem;
  border-radius: 2rem;
}
.d2_v1,
.d2_v2,
.d3 li {
  display: flex;
  flex-flow: column;
}
.d2_v1 span:first-child {
  font-size: x-large;
  text-align: left;
  margin-bottom: 2rem;
}
.d3 li {
  text-align: left;
}
.d2_v2 span:first-child {
  margin-bottom: 2rem;
  color: red;
}
.d2_v2 span:last-child {
  text-align: right;
  margin-right: 4rem;
  font-size: x-large;
}
.d3 {
  margin-top: 3rem;
}
.d3 > span {
  font-size: x-large;
}
.d3 li span {
  font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
}
</style>