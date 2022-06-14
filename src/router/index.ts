import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router';

const publicRoutes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'index',
    component: () => import('../components/time.vue'),
  },
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: publicRoutes,
});

export default router;
