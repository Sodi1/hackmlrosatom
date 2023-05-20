import CoreHTTPClient from "src/clients/CoreHTTPClient"

export default {
  async CompetenceToCount(gender, div, qual) {
    let resp
    resp = await CoreHTTPClient.get('statistic/groupByCompetence', { params: { gender: gender, divisionId: div, qualificationId: qual } })
    return resp
  },
  async AgeToCount(gender, div, qual) {
    let resp
    resp = await CoreHTTPClient.get('statistic/employeeByAge', { params: { gender: gender, divisionId: div, qualificationId: qual } })
    return resp
  },
  async GenderToCount(div, qual) {
    let resp
    resp = await CoreHTTPClient.get('statistic/groupByGender', { params: { divisionId: div, qualificationId: qual } })
    return resp
  },
  async OrgToCount(gender, div, qual) {
    let resp
    resp = await CoreHTTPClient.get('statistic/groupPeopleByOrg', { params: { gender: gender, divisionId: div, qualificationId: qual } })
    return resp
  },
}
