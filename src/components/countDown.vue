<template>
  <div class="box text-second">
    <h1>
      <font color="#E80017">{{ year.charAt(0) }}</font>
      <font color="#D1002E">{{ year.charAt(1) }}</font>
      <font color="#BA0045">{{ year.charAt(2) }}</font>
      <font color="#A3005C">{{ year.charAt(3) }}</font>
      <font color="#8C0073">年</font>
      <font color="#75008A">&nbsp;-&nbsp;</font>
      <font color="#5E00A1">春</font>
      <font color="#4700B8">节</font>
      <font color="#3000CF">倒</font>
      <font color="#1900E6">计</font>
      <font color="#0200FD">时</font>
    </h1>
    <center>
      <div id="CountMsg" class="HotDate">
        <span id="t_d">{{ time.d }} 天</span><span>&nbsp;</span> <span id="t_h">{{ time.h }} 时</span>
        <span>&nbsp;</span> <span id="t_m">{{ time.m }} 分</span><span>&nbsp;</span>
        <span id="t_s">{{ time.s }} 秒</span><span>&nbsp;</span>
      </div>
    </center>
  </div>
</template>
<script lang="ts" setup>
import { reactive } from 'vue';
import lunarFun from 'lunar-fun';
import api from '../api';

const time = reactive({ d: 0, h: '', m: '', s: '' });
const year = (api.dateFormat.getDateRormatY() + 1).toString();
const newYear = lunarFun.lunalToGregorian(year, 1, 1).toString();
setInterval(() => {
  const newDate = new Date(newYear).getTime() - new Date().getTime();
  time.d = Math.floor(newDate / 1000 / 60 / 60 / 24);
  time.h = zeroPadding(Math.floor((newDate / 1000 / 60 / 60) % 24), 2);
  time.m = zeroPadding(Math.floor((newDate / 1000 / 60) % 60), 2);
  time.s = zeroPadding(Math.floor((newDate / 1000) % 60), 2);
}, 1000);
function zeroPadding(num: string | number, digit: number) {
  var zero = '';
  for (var i = 0; i < digit; i++) {
    zero += '0';
  }
  return (zero + num).slice(-digit);
}
</script>
<style scoped>
.box {
  width: 100%;
  position: fixed;
  bottom: 5rem;
  user-select: none;
}
#t_d {
  color: #982585;
  font-size: 18px;
}
#t_h {
  color: #8f79c1;
  font-size: 18px;
}
#t_m {
  color: #65b4b5;
  font-size: 18px;
}
#t_s {
  color: #83caa3;
  font-size: 18px;
}
</style>
