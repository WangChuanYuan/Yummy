<template>
  <div :id="id" :style="{ height: height + 'px', width: width + 'px' }"></div>
</template>

<script>
import echarts from 'echarts/lib/echarts';
import 'echarts/lib/chart/line';
import 'echarts/lib/chart/bar';
import 'echarts/lib/chart/pie';
import 'echarts/lib/component/title';
import 'echarts/lib/component/tooltip';
import 'echarts/lib/component/legend';
import uuidv1 from 'uuid/v1';

export default {
  name: 'LinearChart',
  props: {
    'type': {
      type: String,
      default: 'line' // line || bar || pie
    },
    'chartData': {
      type: Array,
      default: function () {
        return [];
      }
    },
    'titleText': {
      type: String,
      default: 'title'
    },
    'subText': {
      type: String,
      default: 'sub-title'
    },
    'xText': {
      type: String,
      default: 'x-text'
    },
    'yText': {
      type: String,
      default: 'y-text'
    },
    'height': {
      type: Number,
      default: 300
    },
    'width': {
      type: Number,
      default: 300
    }
  },
  data () {
    return {
      id: ''
    };
  },
  created () {
    this.id = uuidv1();
  },
  mounted () {
    this.drawChart();
  },
  watch: {
    chartData (val) {
      this.drawChart();
    }
  },
  computed: {
    xAxisData () {
      return this.chartData.map(function (item) {
        return item['key'].toString();
      });
    },
    yAxisData () {
      return this.chartData.map(function (item) {
        return item['value'];
      });
    }
  },
  methods: {
    drawChart () {
      let chart = echarts.init(document.getElementById(this.id));
      switch (this.type) {
        case 'line':
          chart.setOption(this.generatorLineOption(), true);
          break;
        case 'bar':
          chart.setOption(this.generatorBarOption(), true);
          break;
        case 'pie':
          chart.setOption(this.generatorPieOption(), true);
          break;
        default:
          console.error(`The type of chart ${this.type} is invalid`);
          break;
      }
    },
    generatorLineOption () {
      return {
        title: {
          text: this.titleText,
          // subtext: this.subText,
          left: 'center',
          top: 20
        },
        tooltip: {
          trigger: 'item',
          formatter: '{b} : {c}'
        },
        xAxis: {
          type: 'category',
          data: this.xAxisData
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: this.yAxisData,
            type: 'line',
            smooth: true
          }
        ]
      };
    },
    generatorBarOption () {
      return {
        title: {
          text: this.titleText,
          // subtext: this.subText,
          left: 'center',
          top: 20
        },
        color: ['#3398DB'],
        tooltip: {
          trigger: 'axis',
          axisPointer: { // 坐标轴指示器，坐标轴触发有效
            type: 'shadow' // 默认为直线，可选为：'line' || 'shadow'
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: [
          {
            type: 'category',
            data: this.xAxisData,
            axisTick: {
              alignWithLabel: true
            }
          }
        ],
        yAxis: [
          {
            type: 'value'
          }
        ],
        series: [
          {
            name: this.yText,
            type: 'bar',
            barWidth: '60%',
            data: this.yAxisData
          }
        ]
      };
    },
    generatorPieOption () {
      let pieData = [];
      for (let index in this.xAxisData) {
        pieData.push({
          value: this.yAxisData[index],
          name: this.xAxisData[index]
        });
      }
      return {
        title: {
          text: this.titleText,
          // subtext: this.subText,
          left: 'center',
          top: 20
        },
        tooltip: {
          trigger: 'item',
          formatter: '{b} : {c} ({d}%)'
        },
        legend: {
          bottom: 10,
          left: 'center',
          data: this.xAxisData
        },
        series: [
          {
            type: 'pie',
            radius: '65%',
            center: ['50%', '50%'],
            data: pieData.sort((a, b) => { return a.value - b.value; }),
            roseType: 'radius',
            itemStyle: {
              emphasis: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      };
    }
  }
};
</script>

<style scoped>

</style>
