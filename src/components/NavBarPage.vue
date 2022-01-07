<template>
  <div class="box">
    <van-nav-bar
      :title="title"
      left-text="返回"
      left-arrow
      @click-left="onClickLeft(path, flag)"
      fixed
    />
  </div>
</template>
<script>
import { NavBar } from "vant";
import { useRouter } from "vue-router";
export default {
  name: "上导航",
  components: { [NavBar.name]: NavBar },
  props: { path: String, title: String, flag: String },
  watch: {
    $route(to, from) {
      // 对路由变化作出响应...
      if (to != from) {
        // this.$router.go(0);
        window.location.reload();
      }
    },
  },
  setup() {
    const router = useRouter();
    const onClickLeft = (path, flag) => {
      console.log(flag);
      if (flag != 1) {
        // router.push(path);
        router.push({
          path: "/" + path,
          name: path,
          // params: { id },
        });
      } else {
        // 只能返回上一页
        // const onClickLeft = () => {
        window.history.go(-1);
        // };
      }
    };

    return { onClickLeft };
  },
};
</script>
<style scoped>
.box {
  margin-bottom: 4rem;
}
</style>