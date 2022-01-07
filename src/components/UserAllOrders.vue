<template>
  <nav-bar-page :title="title" :path="path"></nav-bar-page>
  <div class="box">
    <div v-for="(v, i) in orders" :key="i">
      <span class="title"> {{ v.order_time }}</span>
      <span class="order_id">id: {{ v.order_id }}</span>
      <van-divider :style="{ padding: '0 0 0 8rem' }" />
      <div>
        <van-row>
          <van-col span="10"><img :src="imgurl + v.pic" alt="" /></van-col>
          <van-col span="6">
            <van-row>
              <van-col span="24">{{ v.foodname }}</van-col>
              <van-col span="24">
                <van-button
                  color="#7232dd"
                  plain
                  block
                  @click="showPopup(v.item_id)"
                >
                  评论
                </van-button>
              </van-col>
            </van-row>
          </van-col>
          <van-col span="8">
            <van-row>
              <van-col span="24" class="price">
                <span>￥：{{ v.price }}</span>
              </van-col>
              <van-col span="24">
                <span>x{{ v.sum }}</span>
              </van-col>
            </van-row>
          </van-col>
        </van-row>
      </div>
      <van-divider :style="{ padding: '0 0 0 8rem' }" />
      <van-row>
        <van-col span="24">
          <span>
            共 {{ v.sum }} 份，实付金额：
            <span class="sumPrice">￥ {{ v.sum * v.price }}</span></span
          >
        </van-col>
      </van-row>
      <van-divider :style="{ color: '#1989fa', borderColor: '#1989fa' }" />
    </div>

    <van-popup v-model:show="show" position="top" :style="{ height: '60%' }">
      <van-cell-group inset>
        <van-field
          v-model="insertCommentValue"
          rows="4"
          autosize
          label="评论"
          type="textarea"
          maxlength="200"
          placeholder="请输入评论"
          show-word-limit
          class="btn"
        />
      </van-cell-group>
      <van-button
        color="#7232dd"
        plain
        block
        @click="insertComment"
        class="btn"
      >
        确定
      </van-button>
    </van-popup>
  </div>
</template>
<script>
import { ref } from "vue";
import { Col, Row, Divider, Button, Popup, Field, CellGroup } from "vant";
import NavBarPage from "../components/NavBarPage.vue";
export default {
  name: "订单页",
  components: {
    NavBarPage,
    [Col.name]: Col,
    [Row.name]: Row,
    [Divider.name]: Divider,
    [Button.name]: Button,
    [Popup.name]: Popup,
    [Field.name]: Field,
    [CellGroup.name]: CellGroup,
  },
  setup() {
    const title = "我的订单";
    const axios = require("axios");
    const user = JSON.parse(sessionStorage.getItem("user"));
    const orders = ref([]);
    const insertCommentValue = ref("");
    const item_id = ref("");
    const path = "myPage";
    // 服务器图片前缀
    const imgurl = ref("http://172.24.10.130:8080/foodService");

    // 增加评论
    const insertComment = () => {
      axios
        .get(
          "http://172.24.10.130:8080/foodService/insertComment.do?item_id=" +
            item_id.value +
            "&content=" +
            insertCommentValue.value
        )
        .then((res) => {
          console.log(res);
          console.log(insertCommentValue.value);
          console.log(item_id.value);
          show.value = false;
          insertCommentValue.value = "";
        });
    };

    axios
      .get(
        "http://172.24.10.130:8080/foodService/getAllOrdersByUser.do?user_id=" +
          user.userid
      )
      .then(function (response) {
        // 处理成功情况
        orders.value = response.data;
        console.log(response);
        // for (let i = 0; i <= orders.value.length; i++) {
        //   axios
        //     .get(
        //       "http://172.24.10.130:8080/foodService/getFoodById.do?food_id=" +
        //         orders.value[i].food_id
        //     )
        //     .then((res) => {
        //       imgList.value.push(res.data.pic);
        //     });
        // }
      })
      .catch(function (error) {
        // 处理错误情况
        console.log(error);
      })
      .then(function () {
        // 总是会执行
      });
    const show = ref(false);
    const showPopup = (item_id1) => {
      show.value = true;
      item_id.value = item_id1;
    };
    return {
      orders,
      imgurl,
      title,
      show,
      showPopup,
      insertCommentValue,
      insertComment,
      path,
    };
  },
};
</script>
<style scoped>
.order_id {
  display: inline-block;
  font-family: kaiti;
  width: 100%;
  text-align: left;
  font-size: small;
}
.title {
  text-align: left;
  font-size: 1.2rem;
  display: inline-block;
  font-family: kaiti;
  width: 100%;
}
.price {
  margin-bottom: 3rem;
}
.sumPrice {
  color: red;
}
.btn {
  margin-top: 4rem;
}
img {
  border: 1px solid black;
  border-radius: 1rem;
}
</style>