<template>
  <div class="box">{{ search }}</div>
</template>
<script>
import { useRoute } from "vue-router";
export default {
  name: "搜索页",
  components: {},
  setup() {
    const axios = require("axios");
    const route = useRoute();
    const search = route.params.search;
    function getFoodBySearch() {
      axios
        .get(
          "http://172.24.10.130:8080/foodService/getFoodBySearch.do?search=" +
            search
        )
        .then(function (response) {
          // 处理成功情况
          console.log(response);
        })
        .catch(function (error) {
          // 处理错误情况
          console.log(error);
        })
        .then(function () {
          // 总是会执行
        });
    }
    return { getFoodBySearch, search };
  },
};
</script>