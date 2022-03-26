<template>
  <el-input
    v-model="search"
    placeholder="输入关键字 搜索"
    :suffix-icon="Search"
    class="search"
    @keydown.enter="toSearch"
    clearable
  />
</template>
<script lang="ts" setup>
import { ref } from 'vue';
import { Search } from '@element-plus/icons-vue';
import router from '../router';
import api from '../api';

const search = ref();
const store = api.store();
let routerFlag: boolean = true;

// 跳转搜索结果页
function toSearch() {
  store.setQ(search.value);
  if (routerFlag) {
    router.push({ name: 'SearchPage', query: { q: search.value } });
    routerFlag = false;
  } else router.replace({ name: 'SearchPage', query: { q: search.value } });
}
</script>
<style scoped></style>
