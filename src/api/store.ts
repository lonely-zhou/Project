import { defineStore } from 'pinia';

const useStore = defineStore({
  id: 'myGlobalState',
  state: () => ({
    jwtToken: '',
    loginFlag: false,
  }),
  getters: {},
  actions: {
    setjwtToken(jwt: string) {
      this.jwtToken = jwt;
    },
    setLoginFlag(flag: boolean) {
      this.loginFlag = flag;
    },
  },
});

export default useStore;
