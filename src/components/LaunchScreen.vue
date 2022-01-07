<template>
  <div class="box">
    <span>校园订餐</span>
  </div>
</template>
<script>
import { useRouter } from "vue-router";
export default {
  name: "启动页",
  components: {},
  setup() {
    const router = useRouter();
    setTimeout(() => {
      router.push("/login");
    }, 1000);
    return {};
  },
};
</script>
<style scoped>
.box {
  display: flex;
  justify-content: center;
  font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
  font-size: large;
  flex-flow: column;
  background-image: linear-gradient(120deg, #84fab0 0%, #8fd3f4 100%);
  /* background-size: 100%; */
  width: 100%;
  height: 100%;
  position: fixed;
}
</style>