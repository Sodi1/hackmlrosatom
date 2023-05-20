<template>
  <q-page class="column flex-center">
    <div style="width:60%" class="row justify-around items-center">
      <div style="width:10%">
        <q-select :options="['Женщина', 'Мужчина', 'Не указано']" @update:model-value="genderFilter" v-model="gender"
          label="Пол"></q-select>
      </div>
      <div style="width:30%">
        <q-select :options="divisions" @update:model-value="setDivision" v-model="division" label="Дивизион"></q-select>
      </div>
      <div style="width:30%">
        <q-select :options="quals" @update:model-value="setQual" v-model="qual" label="Компетенция"></q-select>
      </div>
      <div>
        <q-btn class="q-mr-xl" @click="dropFilters()" flat round color="secondary" icon="close" />
      </div>
    </div>
    <div>
      <h4>Группировки по количеству участников</h4>
    </div>
    <div class="row justify-around" style="width:100%">
      <div class="chart-card">
        <BarComponent :y-axis="bar1Yaxis" title="Кол-во участников по компетенциям" :x-axis="bar1Xaxis" :key="bar1Key">
        </BarComponent>
      </div>
      <div class="chart-card">
        <BarComponent :title="bar2Title" :y-axis="bar2Yaxis" :x-axis="bar2Xaxis" :key="bar2Key">
        </BarComponent>
      </div>
      <div class="chart-card">
        <BarComponent title="Кол-во участников по гендеру" :y-axis="bar3Yaxis" :x-axis="bar3Xaxis" :key="bar3Key">
        </BarComponent>
      </div>
      <div class="chart-card">
        <PieChartComponent :data="pieData" title="Кол-во участников по предприятиям" :key="pieKey"></PieChartComponent>
      </div>
      <div class="chart-card">
        <LineComponent></LineComponent>
      </div>
      <div class="chart-card">
        <RadialComponent></RadialComponent>
      </div>
      <div class="chart-card">
        <CubeComponent></CubeComponent>
      </div>
    </div>
  </q-page>
</template>

<script setup>
import CubeComponent from 'src/components/CubeChartComponent.vue';
import RadialComponent from 'src/components/RadialChartComponent.vue';
import LineComponent from 'src/components/LineChartComponent.vue';
import BarComponent from 'src/components/BarChartComponent.vue';
import BarChartService from 'src/services/BarChartService';
import DivisionService from "src/services/DivisionService"
import { onMounted, ref, computed } from 'vue';
import PieChartComponent from 'src/components/PieChartComponent.vue';
import QualificationService from 'src/services/QualificationService';

const gender = ref('')
const genderRet = ref('')
const bar1Xaxis = ref([])
const bar1Yaxis = ref([])
const bar1Key = ref(0)

const bar2Xaxis = ref([])
const bar2Yaxis = ref([])
const bar2Title = ref('Кол-во участников по годам')
const bar2Key = ref(0)

const bar3Xaxis = ref([])
const bar3Yaxis = ref([])
const bar3Key = ref(0)

const divisions = ref([])
const division = ref('')
let divisionId = ''
let divisionsDefault = []

let qualsDefault = []
const quals = ref([])
const qual = ref('')
let qualId = ''

const pieData = ref([])
const pieKey = ref(0)

function setQual(v) {
  qualId = qualsDefault.find((v2) => v2.name == v).id
  setCharts()
}

function setDivision(v) {
  divisionId = divisionsDefault.find((v2) => v2.name == v).id
  setCharts()
}

function genderFilter(gender_) {
  if (gender_ === "Мужчина") {
    gender_ = 'MALE'
  }
  if (gender_ === "Женщина") {
    gender_ = 'FEMALE'
  }
  if (gender_ === "Не указано") {
    gender_ = ''
  }
  genderRet.value = gender_
  setCharts()
}

function setBar1(v) {
  BarChartService.CompetenceToCount(...v).then((bar) => {
    bar1Xaxis.value = bar.data.map((v) => {
      return v.competence
    })
    bar1Yaxis.value = bar.data.map((v) => {
      return v.count
    })
    bar1Key.value++
  })
}
function setBar2(v) {
  BarChartService.AgeToCount(...v).then((bar_) => {
    let bar2NotStated = 0
    const bar = bar_.data.filter((v) => {
      if (v.age == null || v.age < 18) {
        bar2NotStated += v.count
        return false
      }
      return true
    })
    if (bar2Title.value.includes('не указано')) {
      bar2Title.value = bar2Title.value.slice(0, 26)
    }
    bar2Title.value += `\n(не указано: ${bar2NotStated})`
    bar2Xaxis.value = bar.map((v) => {
      return v.age
    })
    bar2Yaxis.value = bar.map((v) => {
      return v.count
    })
    bar2Key.value++
  })
}
function setBar3(v) {
  BarChartService.GenderToCount(...v).then((bar_) => {
    bar3Xaxis.value = bar_.data.map((v) => {
      if (v.gender === 0) {
        return "Мужчина"
      }
      return "Женщина"
    })
    bar3Yaxis.value = bar_.data.map((v) => {
      return v.count
    })
    bar3Key.value++
  })
}

function dropFilters() {
  [genderRet.value, divisionId, qualId] = ['', '', '']
  [qual.value, division.value, gender.value] = ['', '', '']
  setCharts()
}

function setCharts(charts) {
  setBar1([genderRet.value, divisionId, qualId])
  setBar2([genderRet.value, divisionId, qualId])
  setBar3([divisionId, qualId])
  setPie([genderRet.value, divisionId, qualId])
}
function setPie(v) {
  BarChartService.OrgToCount(...v).then((c) => {
    pieData.value = c.data.map((val) => {
      if (val.name != '') {
        return {
          value: val.count,
          name: val.name
        }
      }
    })
    pieKey.value++
  })

}
onMounted(async () => {
  setCharts()

  divisionsDefault = (await DivisionService.getDivisions()).data
  divisions.value = divisionsDefault.map((v) => {
    return v.name
  })

  qualsDefault = (await QualificationService.getQualifications()).data
  quals.value = qualsDefault.map((v) => {
    return v.name + ' ' + v.type
  })
})

</script>
<style scoped lang="scss">
.chart-card {
  margin: 10px;
  border-radius: 20px;
  overflow: hidden;
  width: 30vw;
  height: calc(30vw);
}
</style>
