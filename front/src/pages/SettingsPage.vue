<template>
  <q-page class="flex justify-center q-pt-lg">
    <div class="wrapper-settings">
      <q-file color="primary" class="q-mb-lg" :loading="peopleLoading" @update:model-value="uploadPeople"
        v-model="filePeople" accept=".csv" label-color="secondary" label="Загрузить таблицу с участниками">
        <template v-slot:append>
          <q-icon name="add_circle_outline" size="30px" color="secondary" />
        </template>
      </q-file>
      <q-select v-model="qual" :options="quals" label-color="secondary" label="Компетенция" />
      <q-file :disable="!(!!qual.length)" color="primary" class="q-mb-xl" :loading="competitionsLoading"
        @update:model-value="uploadCompetitions" v-model="fileCompetitions" accept=".csv" label-color="secondary"
        label="Загрузить таблицу с компетенциями">
        <template v-slot:append>
          <q-icon name="add_circle_outline" size="30px" color="secondary" />
        </template>
      </q-file>
      <div>
        <div class="table shadow-3">
          <div class="head shadow-2">
            Загруженные файлы
          </div>
          <div v-for="file in files" :key="file.id" class="t-row">
            <div>
              {{ file.fileName }}
            </div>
            <q-space></q-space>
            <div class="row">
              <div class="row items-center justify-center" style="width:120px">{{ (file.size / 1024).toFixed(1) + " кб" }}
              </div>
              <div class="row items-center q-mx-lg">{{ file.created_at }}</div>
              <div class="row items-center q-mx-lg">{{ file.file_type }}</div>
              <div>
                <q-btn size="15px" @click="router.push({ name: 'login' })" flat round color="secondary"
                  icon="description" />
                <q-btn size="15px" style="margin-right: -7px;" @click="dropFile(file.id)" flat round color="secondary"
                  icon="delete" />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </q-page>
</template>

<script setup>
import { ref } from 'vue';
import FileService from 'src/services/FileService'
import QualificationService from 'src/services/QualificationService'
import { Notify } from 'quasar'
import { onMounted } from 'vue';

const peopleLoading = ref(false)
const competitionsLoading = ref(false)
const fileCompetitions = ref()
const filePeople = ref()
const quals = ref([])
const qualsDefault = []
const qual = ref('')
const files = ref([])

onMounted(async () => {
  const resp = await QualificationService.getQualifications()
  qualsDefault.push(...resp.data)
  if (resp?.data) {
    quals.value = resp.data.map((v) => {
      return v.name
    })
  }
  files.value = (await FileService.getUploaded()).data
})
function checkResp(resp, message = 'Данные добавлены') {
  if (resp.Error) {
    Notify.create({
      color: 'negative',
      message: 'Произошла ошибка'
    })
  } else {
    Notify.create({
      color: 'positive',
      message: message
    })
  }
}
async function uploadPeople(v) {
  peopleLoading.value = true
  const resp = await FileService.uploadPeople(v)
  peopleLoading.value = false
  checkResp(resp)
  files.value = (await FileService.getUploaded()).data
}
async function dropFile(id) {
  const resp = await FileService.dropFile(id)
  files.value = (await FileService.getUploaded()).data
  checkResp(resp, 'файл удалён')
}
async function uploadCompetitions(v) {
  const qualId = qualsDefault.find((v) => {
    return v.name === qual.value
  }).id
  competitionsLoading.value = true
  const resp = await FileService.uploadQuals(v, qualId)
  checkResp(resp)
  competitionsLoading.value = false
  files.value = (await FileService.getUploaded()).data
}
</script>
<style scoped lang="scss">
.wrapper-settings {
  min-width: 70%;
  width: 800px;
}

.table {
  color: black;
  background-color: $accent;
  border-radius: 4px;

  .head {
    padding: 8px;
    font-weight: 400;
    font-size: 22px;
    border-bottom: 1px solid black;
  }

  .t-row {
    font-size: 16px;
    padding: 8px;
    display: flex;
    border-bottom: 1px solid black;
    align-items: center;
  }
}
</style>
