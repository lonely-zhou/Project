import { ElMessage } from 'element-plus';
import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router';

const routes: Array<RouteRecordRaw> = [
  {
    path: '/admin',
    name: 'AdminPage',
    redirect: '/admin/analysis',
    component: () => import('../components/admin/AdminPage.vue'),
    meta: { title: '首页-管理', roles: ['admin'] },
    children: [
      {
        path: 'account',
        name: 'AccountPage',
        component: () => import('../components/admin/AccountPage.vue'),
        meta: { title: '账户管理', roles: ['admin'] },
      },
      {
        path: 'analysis',
        name: 'AnalysisPage',
        component: () => import('../components/admin/AnalysisPage.vue'),
        meta: { title: '分析页', roles: ['admin'] },
      },
    ],
  },
  {
    path: '/search',
    name: 'SearchPage',
    component: () => import('../components/SearchPage.vue'),
    meta: { title: '搜索', roles: ['admin', 'user', 'guest'] },
  },
  {
    path: '/reportPage',
    name: 'ReportPage',
    component: () => import('../components/ReportPage.vue'),
    meta: { title: '举报', roles: ['admin', 'user', 'guest'] },
  },
  {
    path: '/readNote',
    name: 'ReadNote',
    component: () => import('../components/ReadNote.vue'),
    meta: { title: '阅读全文', roles: ['admin', 'user', 'guest'] },
  },
  {
    path: '/updUserNote',
    name: 'UpdUserNote',
    component: () => import('../components/UpdUserNote.vue'),
    meta: { title: '更新笔记', roles: ['admin', 'user'] },
  },
  {
    path: '/updUserNoteMd',
    name: 'UpdUserNoteMd',
    component: () => import('../components/UpdUserNoteMd.vue'),
    meta: { title: '更新笔记', roles: ['admin', 'user'] },
  },
  {
    path: '/updPE/:showInfo',
    name: 'UpdPE',
    component: () => import('../components/UpdPE.vue'),
    meta: { title: '更新信息', roles: ['admin', 'user'] },
  },
  {
    path: '/personalCenter',
    name: 'PersonalCenter',
    component: () => import('../components/PersonalCenter.vue'),
    meta: { keepAlive: true, title: '个人中心', roles: ['admin', 'user'] },
  },
  {
    path: '/mdEditor',
    name: 'MdEditor',
    component: () => import('../components/MdEditor.vue'),
    meta: { title: '编辑笔记', roles: ['admin', 'user'] },
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../components/Login.vue'),
    meta: { title: '登录', roles: ['admin', 'user', 'guest'] },
  },
  {
    path: '/signup',
    name: 'SignUp',
    component: () => import('../components/SignUp.vue'),
    meta: { title: '注册', roles: ['admin', 'user', 'guest'] },
  },
  {
    path: '/write',
    name: 'WriteNote',
    component: () => import('../components/NoteEditor.vue'),
    meta: { title: '写笔记', roles: ['admin', 'user'] },
  },
  {
    path: '/404',
    name: 'Page_404',
    component: () => import('../components/Page_404.vue'),
    meta: { title: '404', roles: ['admin', 'user', 'guest'] },
  },
  { path: '/:pathMatch(.*)', redirect: '/404' },
  {
    path: '/',
    name: 'Home',
    alias: '/index',
    component: () => import('../components/HomePage.vue'),
    meta: { keepAlive: true, title: '首页', roles: ['admin', 'user', 'guest'] },
  },
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
});
router.beforeEach((to, from, next) => {
  const roles: string[] = to.meta.roles as string[];
  let role = JSON.parse(sessionStorage.getItem('role') as string);
  if (role === null) role = 'guest';
  if (roles.indexOf(role) === -1) {
    ElMessage.error('无权限');
    next(from.path);
  } else {
    document.title = (to.meta.title ? to.meta.title : '记享') as string;
    next();
  }
});

export default router;
