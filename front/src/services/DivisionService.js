import CoreHTTPClient from "src/clients/CoreHTTPClient"

export default {
  async getDivisions() {
    const resp = await CoreHTTPClient.get('division')
    return resp
  }
}
