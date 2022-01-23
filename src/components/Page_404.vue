<template>
  <div class="box">
    <el-row>
      <el-col :span="24" :offset="0">
        <span class="text404">404</span>
      </el-col>
      <el-col :span="24" :offset="0">
        <el-link type="primary" :underline="false" href="/" style="font-size: 30px;">返回首页</el-link>
      </el-col>
    </el-row>
  </div>
</template>
<script lang="ts" setup></script>
<style scoped>
.box {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  width: 600px;
  height: 400px;
  margin: auto;
}
.text404 {
  margin-top: 10%;
  font-size: 200px;
  user-select: none;
}
</style>
