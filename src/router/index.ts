import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router';

const routes: Array<RouteRecordRaw> = [
  { path: '/search', name: 'SearchPage', component: () => import('../components/SearchPage.vue') },
  { path: '/reportPage', name: 'ReportPage', component: () => import('../components/ReportPage.vue') },
  { path: '/readNote', name: 'ReadNote', component: () => import('../components/ReadNote.vue') },
  { path: '/updUserNote', name: 'UpdUserNote', component: () => import('../components/UpdUserNote.vue') },
  { path: '/updPE/:showInfo', name: 'UpdPE', component: () => import('../components/UpdPE.vue') },
  {
    path: '/personalCenter',
    name: 'PersonalCenter',
    component: () => import('../components/PersonalCenter.vue'),
    meta: { keepAlive: true },
  },
  { path: '/mdEditor', name: 'MdEditor', component: () => import('../components/MdEditor.vue') },
  { path: '/login', name: 'Login', component: () => import('../components/Login.vue') },
  { path: '/signup', name: 'SignUp', component: () => import('../components/SignUp.vue') },
  { path: '/write', name: 'WriteNote', component: () => import('../components/NoteEditor.vue') },
  { path: '/404', name: 'Page_404', component: () => import('../components/Page_404.vue') },
  { path: '/:pathMatch(.*)', redirect: '/404' },
  {
    path: '/',
    name: 'Home',
    alias: '/index',
    component: () => import('../components/HomePage.vue'),
    meta: { keepAlive: true },
  },
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
});

export default router;
