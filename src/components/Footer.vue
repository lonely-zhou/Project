<template>
  <el-footer>
    <div class="box">
      <el-row>
        <el-col :spqn="24"><img src="https://img.shields.io/badge/version-v0.1.0-orange" alt="版本" /></el-col>
        <el-col :span="24">
          © {{ year }} 记录与分享 <a target="_blank" href="https://beian.miit.gov.cn"> 辽ICP备2021007788号 </a>
          <img src="https://img.lonelyzhou.cn/RecordAndShare/%E5%A4%87%E6%A1%88%E5%9B%BE%E6%A0%87.png" />
          <a target="_blank" href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=21142102000082">
            辽公网安备 21142102000082号
          </a>
        </el-col>
      </el-row>
    </div>
  </el-footer>
</template>
<script lang="ts" setup>
import api from '../api/index';

const year: number = api.dateFormat.getDateRormatY();
</script>
<style scoped>
.box{
  margin-top: 30px;
}
a {
  margin-left: 9px;
  color: #535353;
}
</style>
