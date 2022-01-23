<template>
  <el-footer>
    <div class="box">
      <el-row>
        <el-col :span="24">© {{ year }} 记录与分享</el-col>
      </el-row>
    </div>
  </el-footer>
</template>
<script lang="ts" setup>
import api from '../api/index';

const year: number = api.dateFormat.getDateRormatY();
</script>
<style scoped>
.el-footer {
  line-height: 60px;
}
</style>
