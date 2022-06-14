import { reactive } from 'vue';

const date = new Date();
const dateNow = reactive({
  year: 0,
  month: 0,
  day: 0,
  week: '',
  hour: 0,
  minutes: 0,
  seconds: 0,
  weeks: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
});

function getDateInfo() {
  dateNow.year = date.getFullYear();
  dateNow.month = date.getMonth() + 1;
  dateNow.day = date.getDate();
  dateNow.week = dateNow.weeks[date.getDay()];
  dateNow.hour = date.getHours();
  dateNow.minutes = date.getMinutes();
  dateNow.seconds = date.getSeconds();
}
// @return '年'
function getDateRormatY(): number {
  getDateInfo();
  return dateNow.year;
}
// @return '年 月 日'
function getDateFormatYHD(): string {
  getDateInfo();
  return `${dateNow.year}年${dateNow.month}月${dateNow.day}日`;
}
// @return 年 月 日 星期
function getDateFormatYHDW(): string {
  getDateInfo();
  return (`${dateNow.year}年${dateNow.month}月${dateNow.day}日 ${dateNow.week}`);
}
export default { getDateFormatYHD, getDateFormatYHDW, getDateRormatY };
