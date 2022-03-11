import { defineStore } from 'pinia';

const useStore = defineStore({
  id: 'myGlobalState',
  state: () => ({
    isLogin: false,
    user: {
      avatarUrl: '',
      email: '',
      id: '',
      lastTime: '',
      nickname: '',
      phone: '',
      sex: '',
      username: '',
    },
    userNote: {},
    isCollapse: false, // 侧边栏展开or折叠
    role: '',
  }),
  getters: {
    getUserNote(): any {
      return this.userNote;
    },
    getRole(): string {
      return this.role;
    },
  },
  actions: {
    setIsLogin(flag: boolean) {
      this.isLogin = flag;
    },
    setUserNote(note: string) {
      this.userNote = note;
    },
    setUser(user: any) {
      this.user = user;
    },
    setIsCollapse() {
      this.isCollapse = !this.isCollapse;
    },
    setRole(role: string) {
      this.role = role;
    },
  },
  persist: {
    enabled: true,
  },
});

export default useStore;
