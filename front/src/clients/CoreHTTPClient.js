import axios from "axios"

export default axios.create({
  baseURL: "https://rosatom2023-api.kovalev.team/api/v1/",
  headers: {
    "Content-Type": "application/json",
  },
});
