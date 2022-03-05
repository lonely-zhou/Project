import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  server: {
    open: true,
    port: 80,
    proxy: {
      '/api': {
        target: 'https://api.lonelyzhou.cn/recordAndShare/', // 要转发的地址  你要代理的域名和端口号，要加上http
        // target: 'http://localhost:8080/recordAndShare/',
        ws: false, // 是否启用websockets
        changeOrigin: true, // 跨域
        rewrite: (path) => path.replace(/^\/api/, ''),
      },
    },
  },
});
// 服务器地址 81.70.92.142 https://api.lonelyzhou.cn/recordAndShare/
// 本地 http://localhost:8080/recordAndShare/
