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
    userNote: [{}],
    isCollapse: false, // 侧边栏展开or折叠
    role: '',
    q: undefined,
    classification: '请选择',
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
    setUserNote(note: any) {
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
    setQ(q: any) {
      this.q = q;
    },
    setClassification(classification: string) {
      this.classification = classification;
    },
  },
  persist: {
    enabled: true,
  },
});

export default useStore;
