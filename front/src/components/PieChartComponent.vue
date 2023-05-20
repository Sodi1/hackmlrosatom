<template>
  <div ref='chartContainer' style="width:100%;height:100%;background-color: darkblue;"></div>
</template>

<script setup>
import * as echarts from "echarts"
import "echarts-gl"
import { onMounted, defineProps, ref } from "vue";

const props = defineProps({
  data: Array,
  title: String
})
const chartContainer = ref()
const option = {
  title: {
    text: '',
    textStyle: {
      color: '#fff'
    }
  },
  tooltip: {
    trigger: 'item'
  },
  emphasis: {
    label: {
      show: true,
      fontSize: 40,
      fontWeight: 'bold'
    }
  },
  datkMode: true,
  series: [
    {
      name: 'Nightingale Chart',
      type: 'pie',
      radius: ['30%', '70%'],
      center: ['50%', '50%'],
      avoidLabelOverlap: false,
      itemStyle: {
        borderRadius: 10,
        borderColor: '#fff',
        borderWidth: 2
      },
      label: {
        show: false,
        position: 'center'
      },
      labelLine: {
        show: false
      },
      data: [
      ]
    }
  ]
}

onMounted(() => {
  if (props?.data?.length) {
    const dom = chartContainer.value;
    const myChart = echarts.init(dom, null, {
      renderer: 'canvas',
      useDirtyRect: false
    });
    option.series[0].data = props.data
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
