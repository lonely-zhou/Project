<script setup lang="ts">
import { reactive } from 'vue';

const nowTime = reactive({ time: '', date: '' });
var week = ['星期天', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'];
setInterval(updateTime, 1000);
updateTime();
function updateTime() {
  var cd = new Date();
  nowTime.time =
    zeroPadding((cd.getHours() + 24) % 12 || 12, 2) +
    ':' +
    zeroPadding(cd.getMinutes(), 2) +
    ':' +
    zeroPadding(cd.getSeconds(), 2);
  nowTime.date =
    zeroPadding(cd.getFullYear(), 4) +
    '-' +
    zeroPadding(cd.getMonth() + 1, 2) +
    '-' +
    zeroPadding(cd.getDate(), 2) +
    ' ' +
    week[cd.getDay()];
}
function amORpm() {
  return new Date().getHours() < 12 ? 'AM' : 'PM';
}
function zeroPadding(num: string | number, digit: number) {
  var zero = '';
  for (var i = 0; i < digit; i++) {
    zero += '0';
  }
  return (zero + num).slice(-digit);
}
</script>
<template>
  <div id="clock">
    <p class="date">{{ nowTime.date }}</p>
    <p class="time">
      {{ nowTime.time }} <span class="amORpm">{{ amORpm() }}</span>
    </p>
  </div>
</template>
<style scoped>
#clock {
  margin-top: 8rem;
}
.time {
  font-size: 6rem;
  font-weight: bold;
  margin-top: 4rem;
  color: #333;
}
.amORpm {
  font-size: 4rem;
}
.date {
  font-size: 2rem;
  color: #999;
}
</style>
