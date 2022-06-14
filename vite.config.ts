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
        target: 'https://api.lonelyzhou.cn/recordAndShare/',
        ws: false, // 是否启用websockets
        changeOrigin: true, // 跨域
        rewrite: (path) => path.replace(/^\/api/, ''),
      },
    },
  },
});
