<script lang="ts" setup>
import { onMounted } from 'vue';
import api from './api/index';

const store = api.store();
onMounted(() => {
  // 在页面加载时读取sessionStorage里的状态信息
  if (sessionStorage.getItem('loginFlag')) {
    store.setLoginFlag(JSON.parse(sessionStorage.getItem('loginFlag') as string));
  }

  // 在页面刷新时将vuex里的信息保存到sessionStorage里
  window.addEventListener('beforeunload', () => {
    sessionStorage.setItem('loginFlag', JSON.stringify(store.loginFlag));
  });
});
</script>
<template>
  <router-view v-slot="{ Component }">
    <transition name="fade">
      <component :is="Component" />
    </transition>
  </router-view>
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  background-color: #f5f5f5;
}
a {
  text-decoration: none;
  color: rgb(0, 0, 0);
}
* {
  margin: 0;
  padding: 0;
}
@font-face {
  font-family: OPPO Sans;
  src: url('./assets/Font/Font-OPPOSans/OPPOSans-R.ttf');
}
@font-face {
  font-family: 思源黑体;
  src: url('./assets/Font/SubsetOTF/SourceHanSansCN-Bold.otf');
}
/* 渐变过渡 */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
