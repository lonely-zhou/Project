import { ref, reactive } from 'vue';
import axios from 'axios';

const weather = reactive({
  text: '',
  temp: '',
  city: '',
});
const weatherLocationCode = ref();

// 获取天气
function getWeatherInfo() {
  axios
    .get(`https://devapi.qweather.com/v7/weather/now?location=${weatherLocationCode.value}&key=b4d7259714ca43769d3f35d59d7b320a`)
    .then((response) => {
      weather.text = response.data.now.text;
      weather.temp = response.data.now.temp;
    });
}

// 获取 locationCode
function getLocationCode() {
  axios
    .get(`https://geoapi.qweather.com/v2/city/lookup?location=${weather.city}&key=b4d7259714ca43769d3f35d59d7b320a`)
    .then((res) => {
      weatherLocationCode.value = res.data.location[0].id;
      getWeatherInfo();
    })
    .catch((error) => {
      console.log(error);
    })
    .then();
}
// eslint-disable-next-line @typescript-eslint/no-explicit-any
function getWeather(): any {
  axios
    .get('https://restapi.amap.com/v3/ip?key=263f925502d95dc11a2af0a381f421dd')
    .then((response) => {
      weather.city = response.data.city;
      getLocationCode();
    })
    .catch((error) => {
      console.log(error);
    })
    .then();
  return weather;
}

export default { getWeather };
