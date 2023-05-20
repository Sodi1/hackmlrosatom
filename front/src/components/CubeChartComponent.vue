<template>
  <div ref='chartContainer' style="width:100%;height:100%;background-color: darkblue;"></div>
</template>

<script setup>

import { onMounted } from "vue";
import * as echarts from "echarts"
import "echarts-gl"
import { ref } from "vue";

const chartContainer = ref()
const colors = [
  '#f00',
  '#0f0',
  '#00f',
  '#ff0',
  '#0ff',
  '#f0f',
]
function populateRandomData() {
  const series = []
  for (let i = 0; i < 6; i++) {
    series.push(
      {
        name: 'lal' + (i + 1),
        type: 'scatter3D',
        itemStyle: {
          borderWidth: 1,
          borderColor: '#fff',
          color: [0, 1, 0, 0.6],
        },
        emphasis: {
          itemStyle: {
            color: [1, 1, 1, 0.8],
          },
          label: {
            show: false
          }
        },
        data: [],
        tooltip: {},
      }
    )
    const [aroundX, aroundY, aroundZ] = [
      Math.random() * 6 + 2,
      Math.random() * 6 + 2,
      Math.random() * 6 + 2
    ]
    for (let j = 0; j < 48; j++) {
      series[i].data.push(
        {
          value: [
            (Math.random() - 0.5) * 2 + aroundX,
            (Math.random() - 0.5) * 2 + aroundY,
            (Math.random() - 0.5) * 2 + aroundZ,
            Math.ceil(Math.random() * 30)
          ],
          itemStyle: {
            color: colors[i % 6]
          }
        }
      )
    }
  }
  return series
}

onMounted(() => {
  const series = populateRandomData()
  var dom = chartContainer.value;
  var myChart = echarts.init(dom, null, {
    renderer: 'canvas',
    useDirtyRect: false
  });
  var option;
  const data = [
    {
      value: [1, 2, 2],
      itemStyle: {
        color: '#f00'
      }
    },
    [3, 3, 3],
  ]
  option = {
    legend: {
      data: ['lal1', 'lal2', 'lal3', 'lal4', 'lal5', 'lal6'],
      textStyle: {
        color: '#fff'
      }
    },
    grid3D: {
      viewControl: {
        center: [0, -12, 6]
      },
      axisLine: {
        lineStyle: {
          color: '#fff'
        }
      },
      axisPointer: {
        lineStyle: {
          color: [1, 0.8, 0, 1]
        }
      },
    },
    xAxis3D: {
      name: 'X'
    },
    yAxis3D: {
      name: 'Y'
    },
    zAxis3D: {
      name: 'Z'
    },
    visualMap: [
      {
        type: 'continuous',
        show: false,
        min: 0,
        max: 30,
        dimension: 3, // the fourth dimension of series.data (i.e. value[3]) is mapped
        inRange: {
          symbolSize: [10, 20] // Defines the mapping range for the graphic size.
          // The minimum value of the data is mapped to 30, // and the maximum value is mapped to 100.
          // The maximum value is mapped to 100.
          // The rest is calculated linearly automatically.
        },
        outOfRange: {
          // Check the out of range visual configuration
          symbolSize: [30, 100]
        }
      }
      // ...
    ],
    tooltip: {},
    series: series
  };
  myChart.setOption(option);


  if (option && typeof option === 'object') {
    myChart.setOption(option);
  }

  window.addEventListener('resize', myChart.resize);
})

</script>

<style scoped>
.chart {
  height: 400px;
}
</style>
