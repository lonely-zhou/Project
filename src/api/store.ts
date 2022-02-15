import { defineStore } from 'pinia';

const useStore = defineStore({
  id: 'myGlobalState',
  state: () => ({
    jwtToken: '',
    loginFlag: false,
    userNote: {},
  }),
  getters: {},
  actions: {
    setjwtToken(jwt: string) {
      this.jwtToken = jwt;
    },
    setLoginFlag(flag: boolean) {
      this.loginFlag = flag;
    },
    setUserNote(note: string) {
      this.userNote = note;
    },
  },
});

export default useStore;
