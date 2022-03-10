// import { ElMessage } from 'element-plus';
import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router';

const publicRoutes: Array<RouteRecordRaw> = [
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
    path: '/404',
    name: 'Page_404',
    component: () => import('../components/Page_404.vue'),
    meta: { title: '404', roles: ['admin', 'user', 'guest'] },
  },
  // { path: '/:pathMatch(.*)', redirect: '/404' },
  {
    path: '/',
    name: 'Home',
    alias: '/index',
    component: () => import('../components/HomePage.vue'),
    meta: { keepAlive: true, title: '首页', roles: ['admin', 'user', 'guest'] },
  },
];
export const asyncRoutes: Array<RouteRecordRaw> = [
  {
    path: '/admin',
    name: 'AdminPage',
    redirect: '/admin/analysis',
    component: () => import('../components/admin/AdminPage.vue'),
    meta: { title: '系统管理', roles: ['admin'], menu: true },
    children: [
      {
        path: 'account',
        name: 'AccountPage',
        component: () => import('../components/admin/AccountPage.vue'),
        meta: { title: '账户管理', roles: ['su-admin'], menu: true },
      },
      {
        path: 'analysis',
        name: 'AnalysisPage',
        component: () => import('../components/admin/AnalysisPage.vue'),
        meta: { title: '分析页', roles: ['admin'], menu: false },
      },
    ],
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
    path: '/write',
    name: 'WriteNote',
    component: () => import('../components/NoteEditor.vue'),
    meta: { title: '写笔记', roles: ['admin', 'user'] },
  },
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: publicRoutes,
});
export function setRoutes(role: string) {
  for (let i = 0; i < asyncRoutes.length; i += 1) {
    const roles: string[] = asyncRoutes[i].meta!.roles as string[];
    if (roles.indexOf(role) !== -1) {
      router.addRoute(asyncRoutes[i]);
    }
    if (asyncRoutes[i].children) {
      const childrenRouter: RouteRecordRaw[] = asyncRoutes[i].children as RouteRecordRaw[];
      const tempRouter: RouteRecordRaw[] = [];
      for (let j = 0; j < childrenRouter.length; j += 1) {
        const childrenRoles: string[] = childrenRouter[j].meta!.roles as string[];
        if (childrenRoles.indexOf(role) !== -1) {
          tempRouter.push(childrenRouter[j]);
        }
      }
      router.addRoute({
        name: asyncRoutes[i].name as string,
        path: asyncRoutes[i].path,
        meta: asyncRoutes[i].meta,
        component: asyncRoutes[i].component as any,
        children: tempRouter,
      });
    }
  }
  router.addRoute({ path: '/:pathMatch(.*)', name: 'redirect404', redirect: '/404' });
}

router.beforeEach((to, from, next) => {
  const myGlobalState = JSON.parse(sessionStorage.getItem('myGlobalState') as string);
  let role: string;
  if (myGlobalState === null) {
    role = 'guest';
  } else {
    role = myGlobalState.role as string;
  }

  // const roles: string[] = to.meta.roles as string[];
  // for (let i = 0; i < asyncRoutes.length; i += 1) {
  //   const roles: string[] = asyncRoutes[i].meta!.roles as string[];
  //   if (roles.indexOf(role) !== -1) {
  //     router.addRoute(asyncRoutes[i]);
  //   }
  // }
  setRoutes(role);
  if (to.matched.length === 0) next({ path: to.path });
  else next();

  // console.log(router.getRoutes());
  document.title = (to.meta.title ? to.meta.title : '记享') as string;
  // next();
  // if (roles.indexOf(myGlobalState.role) === -1) {
  //   ElMessage.error('无权限');
  //   next(from.path);
  // } else {
  //   document.title = (to.meta.title ? to.meta.title : '记享') as string;
  //   next();
  // }
});

export default router;
