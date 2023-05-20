<template>
  <div ref='chartContainer' style="width:100%;height:100%;"></div>
</template>

<script setup>
import * as echarts from "echarts"
import "echarts-gl"
import { onMounted, defineProps, ref } from "vue";

const props = defineProps({
  yAxis: Array,
  xAxis: Array,
  title: String
})
const chartContainer = ref()
const chart = ref()
const option = {
  title: {
    text: '',
  },
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      type: 'shadow'
    }
  },
  legend: {
    data: [],
    title: '12',
    textStyle: {
      color: '#fff'
    }
  },
  xAxis: [
    {
      show: false,
      type: 'category',
      axisTick: { show: false },
      data: []
    }
  ],
  yAxis: [
    {
      type: 'value'
    }
  ],
  series: [
    {
      name: 'кол-во участников',
      type: 'bar',
      barGap: 0,
      label: {},
      emphasis: {
        focus: 'series'
      },
      data: []
    },
  ]
}

onMounted(() => {
  if (props.yAxis.length && props.xAxis.length) {
    const dom = chartContainer.value;
    const myChart = echarts.init(dom, null, {
      renderer: 'canvas',
      useDirtyRect: false
    });
    option.series[0].data = props.yAxis
    option.xAxis[0].data = props.xAxis
    option.title.text = props.title
    myChart.setOption(option);
    window.addEventListener('resize', myChart.resize);
  }
})

</script>

<style scoped>
.chart {
  height: 400px;
}
</style>
