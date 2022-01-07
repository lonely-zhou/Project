<template>
  <nav-bar-page :title="title" :path="path" />
  <div class="box">
    <div v-for="(v, i) in comments" :key="i">
      <span class="title">{{ v.shopname }} -- {{ v.foodname }}</span>
      <van-row>
        <van-col span="10">
          <van-row>
            <van-col span="24"><img :src="imgurl + v.pic" alt="" /></van-col>
            <van-col span="24">{{ v.comment_time }}</van-col>
          </van-row>
        </van-col>
        <van-col span="8">
          <van-row>
            <van-col span="24" class="content" @click="showContent(v.content)">
              {{ v.content }}
            </van-col>
          </van-row>
        </van-col>
        <van-col span="6">
          <van-button
            color="#7232dd"
            plain
            block
            class="btn"
            @click="deleteComment(v.item_id)"
          >
            删除
          </van-button>
          <van-button color="#7232dd" plain block @click="showPopup(v.item_id)">
            修改
          </van-button>
        </van-col>
      </van-row>
      <van-divider
        :style="{ color: '#1989fa', borderColor: '#1989fa', padding: '0 16px' }"
      />
    </div>
    <van-popup v-model:show="show1" position="top" :style="{ height: '60%' }">
      <van-cell-group inset>
        <van-field
          v-model="updateCommentValue"
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
        @click="updateComment"
        class="btn"
      >
        确定
      </van-button>
    </van-popup>
    <van-popup v-model:show="show2" :style="{ height: '60%' ,width:'60%' }">
      {{ contentValue }}
    </van-popup>
  </div>
</template>
<script>
import { ref, onMounted } from "vue";
import NavBarPage from "../components/NavBarPage.vue";
import { Col, Row, Button, Divider, Popup, Field, CellGroup } from "vant";
export default {
  name: "评论页",
  components: {
    NavBarPage,
    [Col.name]: Col,
    [Row.name]: Row,
    [Button.name]: Button,
    [Divider.name]: Divider,
    [Popup.name]: Popup,
    [Field.name]: Field,
    [CellGroup.name]: CellGroup,
  },
  setup() {
    const axios = require("axios");
    const user = JSON.parse(sessionStorage.getItem("user"));
    const comments = ref();
    const path = "myPage";
    const item_id = ref();
    const updateCommentValue = ref("");
    const firstText = ref("前");
    const endText = ref("后");

    // 服务器图片前缀
    const imgurl = ref("http://172.24.10.130:8080/foodService");

    // 显示评论
    const show2 = ref(false);
    const contentValue = ref();
    const showContent = (content) => {
      contentValue.value = content;
      show2.value = true;
    };
    // 修改评论
    const updateComment = () => {
      axios
        .get(
          "http://172.24.10.130:8080/foodService/updateComment.do?item_id=" +
            item_id.value +
            "&content=" +
            firstText.value +
            updateCommentValue.value +
            endText.value
        )
        .then((res) => {
          console.log(res.data);
          show1.value = false;
          updateCommentValue.value = "";
          getAllComments();
        });
    };

    // 删除评论
    const deleteComment = (itemid) => {
      axios
        .get(
          "http://172.24.10.130:8080/foodService/deleteComment.do?item_id=" +
            itemid
        )
        .then(() => {
          getAllComments();
        });
    };
    // 获取评论信息
    const getAllComments = onMounted(() => {
      axios
        .get(
          "http://172.24.10.130:8080/foodService/getAllCommentsByUser.do?user_id=" +
            user.userid
        )
        .then(function (response) {
          // 处理成功情况
          comments.value = response.data;
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

    const title = "我的评论";
    const show1 = ref(false);
    const showPopup = (itemid) => {
      show1.value = true;
      item_id.value = itemid;
    };
    return {
      updateCommentValue,
      title,
      comments,
      path,
      imgurl,
      updateComment,
      showPopup,
      show1,
      show2,
      getAllComments,
      deleteComment,
      showContent,
      contentValue,
    };
  },
};
</script>
<style scoped>
img {
  border: 1px solid black;
  border-radius: 1rem;
}
.title {
  margin-bottom: 1rem;
  display: inline-block;
  width: 100%;
  color: #8b8378;
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen,
    Ubuntu, Cantarell, "Open Sans", "Helvetica Neue", sans-serif;
}
.btn {
  margin-bottom: 1rem;
}
.content {
  font-size: small;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 5;
}
</style>