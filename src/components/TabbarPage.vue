<template>
  <div class="box">
    <van-tabbar v-model="active">
      <van-tabbar-item icon="home-o" to="index">首页</van-tabbar-item>
      <van-tabbar-item icon="label-o" to="userAllCollection">
        收藏
      </van-tabbar-item>
      <van-tabbar-item icon="shopping-cart-o" to="buyCar">购物车</van-tabbar-item>
      <van-tabbar-item icon="manager-o" to="myPage">我的</van-tabbar-item>
    </van-tabbar>
  </div>
</template>
<script>
// import { ref } from "vue";
import { computed } from "vue";
import { Tabbar, TabbarItem } from "vant";
export default {
  name: "tabbarPage",
  components: { [Tabbar.name]: Tabbar, [TabbarItem.name]: TabbarItem },
  props: { activeOther: String },
  setup(props) {
    const active = computed(() => {
      return props.activeOther;
    });
    return { active };
  },
};
</script>
<style scoped>
</style>