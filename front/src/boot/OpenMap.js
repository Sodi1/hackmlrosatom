import { boot } from 'quasar/wrappers'
import OpenLayersMap from "vue3-openlayers";
// "async" is optional;
// more info on params: https://v2.quasar.dev/quasar-cli/boot-files
export default boot(async ({ app }) => {
  app.use(OpenLayersMap)
})
