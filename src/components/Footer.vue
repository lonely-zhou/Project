<template>
  <el-footer>
    <div class="box">
      <el-row>
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
.el-footer {
  line-height: 60px;
}
.box_beian {
  width: 100%;
  height: 30px;
}
.box_beian,
.box_a2 {
  display: flex;
}
a {
  margin-left: 9px;
  color: #535353;
}
.box_beian {
  position: absolute;
  bottom: 0;
  width: 100%;
  justify-content: center;
}
</style>
