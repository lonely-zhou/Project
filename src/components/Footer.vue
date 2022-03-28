<template>
  <el-footer>
    <div class="box">
      <el-row>
        <el-col :spqn="24"><img src="https://img.shields.io/badge/version-v0.1.0-orange" alt="版本" /></el-col>
        <el-col :span="24">
          <span>本网站由</span>
          <a
            class="footer-support-logo"
            href="https://www.upyun.com/?utm_source=lianmeng&utm_medium=referral"
            target="blank"
            title="又拍云"
          >
            <img height="20" src="https://cdn.jsdelivr.net/gh/YunYouJun/cdn/img/logo/upyun-logo.png" alt="又拍云" />
          </a>
          <span>提供 CDN 加速</span>
        </el-col>
        <el-col :span="24">
          <img :src="`https://img.shields.io/badge/©${year}-记录与分享-critical`" />
          <a target="_blank" href="https://beian.miit.gov.cn">
            <img src="https://img.shields.io/badge/辽ICP备-2021007788号-blue" />
          </a>
          <a target="_blank" href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=21142102000082">
            <img
              :src="`https://img.shields.io/badge/辽公网安备-21142102000082号-blue?logo=data:image/png;base64,${BAIcon}`"
            />
          </a>
        </el-col>
      </el-row>
    </div>
  </el-footer>
</template>
<script lang="ts" setup>
import axios from 'axios';
import { ref } from 'vue';
import api from '../api/index';

const year: number = api.dateFormat.getDateRormatY();
const BAIcon = ref();
axios
  .get('api/user/selBaIcon')
  .then((res) => {
    BAIcon.value = res.data;
  })
  .then(() => {});
</script>
<style scoped>
.box {
  margin-top: 30px;
}
a {
  margin-left: 9px;
  color: #535353;
}
</style>
