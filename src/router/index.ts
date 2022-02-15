import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router';

const routes: Array<RouteRecordRaw> = [
  { path: '/updPE/:showInfo', name: 'UpdPE', component: () => import('../components/UpdPE.vue') },
  { path: '/personalCenter', name: 'PersonalCenter', component: () => import('../components/PersonalCenter.vue') },
  {
    path: '/mdEditor',
    name: 'MdEditor',
    component: () => import('../components/MdEditor.vue'),
  },
  {
    path: '/',
    name: 'Home',
    alias: '/index',
    component: () => import('../components/HomePage.vue'),
    meta: { title: '首页' },
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../components/Login copy.vue'),
    meta: { title: '登录' },
  },
  {
    path: '/signup',
    name: 'SignUp',
    component: () => import('../components/SignUp.vue'),
    meta: { title: '注册' },
  },
  {
    path: '/write',
    name: 'WriteNote',
    component: () => import('../components/NoteEditor.vue'),
    meta: { title: '写笔记' },
  },
  {
    path: '/404',
    name: 'Page_404',
    component: () => import('../components/Page_404.vue'),
  },
  {
    path: '/:pathMatch(.*)',
    redirect: '/404',
  },
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
});

export default router;
