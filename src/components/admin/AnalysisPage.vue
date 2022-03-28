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
    <div id="echarts" style="width: 100%; height: 400px"></div>
  </div>
</template>
<script lang="ts" setup>
import anime from 'animejs';
import axios from 'axios';
import { onMounted, ref } from 'vue';
import * as echarts from 'echarts';
import Result from '../../api/common';

const count = ref({ commentCount: 0, noteCount: 0, userCount: 0, lookCount: 0 });
const count2 = ref({ commentCount: 0, noteCount: 0, userCount: 0, lookCount: 0 });
const analysis = ref({ userNum: [], noteNum: [], commentNum: [], lookNum: [] });
function selAnalysis() {
  const myChart = echarts.init(document.getElementById('echarts') as HTMLElement);
  // 绘制图表
  myChart.setOption({
    tooltip: {},
    xAxis: {
      data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
    },
    yAxis: {},
    series: [
      {
        name: '用户数',
        type: 'line',
        data: [],
      },
      {
        name: '笔记数',
        type: 'line',
        data: [],
      },
      {
        name: '评论数',
        type: 'line',
        data: [],
      },
      {
        name: '浏览数',
        type: 'line',
        data: [],
      },
    ],
  });
  let result: Result;
  axios
    .get('/api/analysis/selAnalysis')
    .then((res) => {
      result = res.data;
    })
    .then(() => {
      analysis.value = result.data;
      myChart.setOption({
        series: [
          {
            name: '用户数',
            data: analysis.value.userNum,
          },
          {
            name: '笔记数',
            data: analysis.value.noteNum,
          },
          {
            name: '评论数',
            data: analysis.value.commentNum,
          },
          {
            name: '浏览数',
            data: analysis.value.lookNum,
          },
        ],
      });
    });
}
onMounted(() => {
  const result = ref();
  axios
    .get('/api/admin/selAnalysis')
    .then((res) => {
      result.value = res.data;
      count.value = res.data.data;
    })
    .then(() => {
      selAnalysis();
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
