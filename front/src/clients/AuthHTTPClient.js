import axios from "axios"

export default axios.create({
  baseURL: "https://jwt-api.kovalev.team/",
  headers: {
    "Content-Type": "application/x-www-form-urlencoded",
  },
});
