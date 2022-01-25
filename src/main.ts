import { createApp } from 'vue';
import ElementPlus from 'element-plus';
import { createPinia } from 'pinia';
import App from './App.vue';
import router from './router';

// element ui
import 'element-plus/dist/index.css';
// 字体图标
import './assets/icon/iconfont.css';

createApp(App)
  .use(router)
  .use(ElementPlus)
  .use(createPinia())
  .mount('#app');
