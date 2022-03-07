<script lang="ts" setup>
import axios from 'axios';
import { onMounted, ref } from 'vue';
import api from './api/index';
import router from './router';

const store = api.store();
const percentage = ref();
const showProgress = ref(false);

router.beforeEach(() => {
  percentage.value = Math.ceil(Math.random() * 100);
  showProgress.value = true;
});
router.afterEach(() => {
  percentage.value = 100;
  showProgress.value = false;
});
onMounted(() => {
  const result = ref();
  axios
    .get('/api/user/isLogin')
    .then((res) => {
      result.value = res.data;
    })
    .then(() => {
      if (result.value.code === 200) {
        store.setIsLogin(result.value.data.isLogin);
        store.setUser(result.value.data.user);
      }
    });
  // // 在页面加载时读取sessionStorage里的状态信息
  // store.setUser(JSON.parse(sessionStorage.getItem('user') as string));
  // // }

  // // 在页面刷新时将vuex里的信息保存到sessionStorage里
  // window.addEventListener('beforeunload', () => {
  //   sessionStorage.setItem('user', JSON.stringify(store.user));
  // });
});
</script>
<template>
  <div id="id">
    <el-affix>
      <el-progress
        :percentage="percentage"
        :indeterminate="true"
        :show-text="false"
        :stroke-width="3"
        v-show="showProgress"
      />
    </el-affix>
    <!-- <router-view v-slot="{ Component }">
    <transition name="fade"></transition>
      <component :is="Component" />
    </transition>
  </router-view> -->
    <router-view v-slot="{ Component }">
      <keep-alive :max="3">
        <component :is="Component" v-if="$route.meta.keepAlive" :key="$route.name" />
      </keep-alive>
      <component :is="Component" v-if="!$route.meta.keepAlive" :key="$route.name" />
    </router-view>

    <!-- <router-view /> -->
  </div>
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  /* background-color: #f5f5f5; */
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
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
