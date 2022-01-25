import { defineStore } from 'pinia';

const useStore = defineStore({
  id: 'myGlobalState',
  state: () => ({
    loginFlag: false,
  }),
  getters: {},
  actions: {
    setLoginFlag(flag: boolean) {
      this.loginFlag = flag;
    },
  },
});

export default useStore;
