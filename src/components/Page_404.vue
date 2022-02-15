<template>
  <div class="box">
    <!-- <el-row>
      <el-col :span="24" :offset="0">
        <span class="text404">404</span>
      </el-col>
      <el-col :span="24" :offset="0">
        <el-link type="primary" :underline="false" href="/" style="font-size: 30px;">返回首页</el-link>
      </el-col>
    </el-row> -->
    <el-result icon="error" title="404" sub-title="当前页面不存在">
      <template #extra>
        <router-link to="/index">
          <el-button type="primary">返回首页</el-button>
        </router-link>
      </template>
    </el-result>
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
  margin: auto;
  width: 600px;
  height: 400px;
}
</style>
