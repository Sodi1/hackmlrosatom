import CoreHTTPClient from "src/clients/CoreHTTPClient"

export default {
  async uploadPeople(file) {
    const bodyFormData = new FormData();
    bodyFormData.append('file', file)
    const resp = await CoreHTTPClient.post('file/csv/employee', bodyFormData)
    return resp
  },
  async uploadQuals(file, qualId) {
    const bodyFormData = new FormData();
    bodyFormData.append('file', file)
    const resp = await CoreHTTPClient.post('file/csv/competence/' + qualId, bodyFormData)
    return resp
  },
  async dropFile(id) {
    const resp = await CoreHTTPClient.delete('file/' + id)
    return resp
  },
  async getUploaded() {
    const resp = await CoreHTTPClient.get('file')
    return resp
  }
}
