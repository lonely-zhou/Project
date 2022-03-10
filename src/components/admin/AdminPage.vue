<template>
  <div class="box">
    <el-container>
      <el-aside width="auto">
        <el-menu
          class="menu"
          active-text-color="#ffd04b"
          background-color="#545c64"
          :default-active="$route.path"
          text-color="#fff"
          router
          :collapse="store.isCollapse"
        >
          <el-menu-item><span class="title">记享</span></el-menu-item>
          <el-menu-item index="/admin/analysis">
            <el-icon><files /></el-icon>
            <span>网站数据</span>
          </el-menu-item>
          <el-sub-menu index="2">
            <template #title>
              <el-icon><setting /></el-icon>
              <span>系统管理</span>
            </template>
            <el-menu-item index="/admin/account"></el-menu-item>
          </el-sub-menu>
          <!--  <el-sub-menu index="2">
            <template #title>
              <el-icon><setting /></el-icon>
              <span>系统管理</span>
            </template>
            <el-menu-item index="/admin/account">账号管理</el-menu-item>
            <el-menu-item index="1-2">角色管理</el-menu-item>
          </el-sub-menu>
        <el-menu-item index="3">
            <el-icon><edit-pen /></el-icon>
            <span>笔记管理</span>
          </el-menu-item>
          <el-menu-item index="4">
            <el-icon><chat-dot-round /></el-icon>
            <span>反馈管理</span>
          </el-menu-item>
          <el-menu-item index="5">
            <el-icon><span class="iconfont icon-flag" /></el-icon>
            <span>举报管理</span>
          </el-menu-item> -->
        </el-menu>
      </el-aside>
      <el-container>
        <el-header style="display: flex; align-items: center">
          <div>
            <el-button type="text" v-if="showButton" @click="changeIsCollapse">
              <span class="iconfont icon-indent" />
            </el-button>
            <el-button type="text" v-if="!showButton" @click="changeIsCollapse">
              <span class="iconfont icon-outdent" />
            </el-button>
          </div>
          <el-breadcrumb separator="/">
            <el-breadcrumb-item v-for="(item, index) in $route.path.split('/')" :key="index">
              {{ item }}
            </el-breadcrumb-item>
          </el-breadcrumb>
        </el-header>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>
<script lang="ts" setup>
// import { EditPen, Setting, ChatDotRound, Files } from '@element-plus/icons-vue';
import anime from 'animejs';
import { ref } from 'vue';
import api from '../../api';
import router from '../../router';

const store = api.store();
// const isCollapse = ref();
const showButton = ref(false);
const menuList = router.getRoutes().filter((item) => item.meta.menu);
console.log(menuList);

function changeIsCollapse() {
  store.setIsCollapse();
  // isCollapse.value = !isCollapse.value;
  showButton.value = !showButton.value;
  anime({
    targets: '.el-aside',
    duration: 1000,
  });
}
</script>
<style scoped>
.box {
  text-align: left;
}
.title {
  font-size: x-large;
  font-family: 'Courier New', Courier, monospace;
}
.menu:not(.el-menu--collapse) {
  height: 100vh;
  width: 200px;
}
/* .el-aside {
  transition: width 0.25s;
  -webkit-transition: width 0.25s;
  -moz-transition: width 0.25s;
  -webkit-transition: width 0.25s;
  -o-transition: width 0.25s;
} */
</style>
