import AuthHTTPClient from "src/clients/AuthHTTPClient"

export default {
  async logIn(login, pass) {
    const postData = new URLSearchParams({
      username: login,
      password: pass,
    });
    const response = await AuthHTTPClient
      .post("api/login", postData)
      .catch((err) => {
        return err;
      });
    return response;
  }
}
