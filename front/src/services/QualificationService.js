import CoreHTTPClient from "src/clients/CoreHTTPClient"

export default {
  async getQualifications() {
    const resp = await CoreHTTPClient.get('qualification')
    return resp
  }
}
