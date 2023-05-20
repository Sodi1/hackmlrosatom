import { defineStore } from "pinia";
import AuthService from "src/services/AuthService";

function parseToken(token) {
  const base64Url = token.split(".")[1];
  const base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
  const jsonPayload = decodeURIComponent(
    window
      .atob(base64)
      .split("")
      .map(function (c) {
        return "%" + ("00" + c.charCodeAt(0).toString(16)).slice(-2);
      })
      .join("")
  );

  return JSON.parse(jsonPayload);
}

export const useUserStore = defineStore("user", {
  state: () => ({
    user: {
      username: '',
      roles: [],
      accessToken: '',
      refreshToken: ''
    },
  }),
  getters: {
    isSignedIn(state) {
      return (
        !!state.user.accessToken?.length && !!state.user.refreshToken?.length
      );
    },
  },
  actions: {
    async login(login, password) {
      const resp = await AuthService.logIn(login, password);
      if (resp?.data?.access_token) {
        console.log(resp)
        const parsedToken = parseToken(resp.data.access_token);
        this.user = {
          username: parsedToken.sub,
          roles: parsedToken.roles,
          accessToken: resp.data.access_token,
          refreshToken: resp.data.refresh_token
        }
        console.log(this.user)
      }
    },
    dropUser() {
      this.user = {
        username: '',
        roles: [],
        accessToken: '',
        refreshToken: ''
      }
    }
  },
  persist: {
    enabled: true,
  },
});
