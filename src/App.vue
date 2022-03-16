<script lang="ts" setup></script>
<template>
  <div id="id">
    <!-- <router-view v-slot="{ Component }">
    <transition name="fade"></transition>
      <component :is="Component" />
    </transition>
  </router-view> -->
    <router-view v-slot="{ Component }">
      <keep-alive>
        <component :is="Component" v-if="$route.meta.keepAlive" :key="$route.name" />
      </keep-alive>
      <component :is="Component" v-if="!$route.meta.keepAlive" :key="$route.name" />
    </router-view>
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
