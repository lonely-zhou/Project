import { createApp } from 'vue';
import ElementPlus from 'element-plus';
import mavonEditor from 'mavon-editor';
import store from './pinia';
import App from './App.vue';
import router from './router';
import 'mavon-editor/dist/css/index.css';

// element ui
import 'element-plus/dist/index.css';
// 字体图标
import './assets/icon/iconfont.css';

createApp(App)
  .use(router)
  .use(ElementPlus)
  .use(store)
  .use(mavonEditor)
  .mount('#app');
