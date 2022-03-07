import { defineStore } from 'pinia';

const useStore = defineStore({
  id: 'myGlobalState',
  state: () => ({
    isLogin: false,
    user: null,
    userNote: {},
  }),
  getters: {
    getUserNote(): any {
      return this.userNote;
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
  },
});

export default useStore;
