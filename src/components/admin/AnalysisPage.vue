<template>
  <div class="box">
    <div class="analysis">
      <el-card class="box-card">
        <template #header>
          <div class="card-header">
            <span>用户数</span>
          </div>
        </template>
        {{ count2.userCount }}
      </el-card>
      <el-card class="box-card">
        <template #header>
          <div class="card-header">
            <span>笔记数</span>
          </div>
        </template>
        {{ count2.noteCount }}
      </el-card>
      <el-card class="box-card">
        <template #header>
          <div class="card-header">
            <span>评论数</span>
          </div>
        </template>
        {{ count2.commentCount }}
      </el-card>
      <el-card class="box-card">
        <template #header>
          <div class="card-header">
            <span>浏览数</span>
          </div>
        </template>
        {{ count2.lookCount }}
      </el-card>
    </div>
  </div>
</template>
<script lang="ts" setup>
import anime from 'animejs';
import axios from 'axios';
import { onMounted, ref } from 'vue';

const count = ref({ commentCount: 0, noteCount: 0, userCount: 0, lookCount: 0 });
const count2 = ref({ commentCount: 0, noteCount: 0, userCount: 0, lookCount: 0 });

onMounted(() => {
  const result = ref();
  axios
    .get('/api/admin/selAnalysis')
    .then((res) => {
      result.value = res.data;
      count.value = res.data.data;
    })
    .then(() => {
      anime({
        targets: count2.value,
        userCount: count.value.userCount,
        noteCount: count.value.noteCount,
        commentCount: count.value.commentCount,
        lookCount: count.value.lookCount,
        round: 1,
        easing: 'linear',
      });
    });
});
</script>
<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.box-card {
  width: 480px;
  margin-right: 10px;
}
.analysis {
  display: flex;
}
</style>
