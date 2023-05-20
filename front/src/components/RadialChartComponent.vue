<template>
  <div ref='chartContainer' style="width:100%;height:100%;background-color: darkblue;"></div>
</template>

<script setup>

import { onMounted } from "vue";
import * as echarts from "echarts"
import "echarts-gl"
import { ref } from "vue";

const chartContainer = ref()

function populateData() {
  const data = []

  data.push({
    value: [
      Math.random() * 100,
      Math.random() * 100,
      Math.random() * 100,
      Math.random() * 100,
      Math.random() * 100,
      Math.random() * 100,
      Math.random() * 100,
      Math.random() * 100,
      Math.random() * 100,
    ], name: 'Allocated Budget'
  })
  data.push({
    value: [
      Math.random() * 100,
      Math.random() * 100,
      Math.random() * 100,
      Math.random() * 100,
      Math.random() * 100,
      Math.random() * 100,
      Math.random() * 100,
      Math.random() * 100,
      Math.random() * 100,
    ], name: 'Actual Spending'
  })
  data.push({
    value: [
      Math.random() * 100,
      Math.random() * 100,
      Math.random() * 100,
      Math.random() * 100,
      Math.random() * 100,
      Math.random() * 100,
      Math.random() * 100,
      Math.random() * 100,
      Math.random() * 100,
    ], name: 'lal'
  })

  return data
}

onMounted(() => {
  var dom = chartContainer.value;
  var myChart = echarts.init(dom, null, {
    renderer: 'canvas',
    useDirtyRect: false
  });
  const option = {
    legend: {
      data: ['Allocated Budget', 'Actual Spending', 'lal'],
      textStyle: {
        color: '#fff'
      }

    },
    tooltip: {
      trigger: 'axis'
    },
    radar: [
      {
        indicator: [
          { name: 'Brand', max: 100 },
          { name: 'Content', max: 100 },
          { name: 'Usability', max: 100 },
          { name: 'Atom', max: 100 },
          { name: 'Skill', max: 100 },
          { name: 'Hacko', max: 100 },
          { name: 'Dota2', max: 100 },
          { name: 'Usability', max: 100 },
          { name: 'Function', max: 100 }
        ]
      }
    ],
    series: [
      {
        type: 'radar',
        tooltip: {
          trigger: 'item'
        },
        areaStyle: {},
        data: populateData()
      }
    ]
  };
  myChart.setOption(option);
  window.addEventListener('resize', myChart.resize);
})

</script>

<style scoped>
.chart {
  height: 400px;
}
</style>
