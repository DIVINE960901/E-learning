<script setup>
import * as echarts from "echarts";
import { onMounted, onUnmounted, ref } from "vue";
import { getDataService, getDataGroupedByDateService } from "@/api/exam.js"
import { useRoute, useRouter } from 'vue-router';
const route = useRoute();
const router = useRouter();
const categoryXAxis = []
const categorySeries = []
const gaugeSeriesData = ref(0)


const categoryResult = async () => {
    try {
        let result = await getDataGroupedByDateService(route.params.id);
    for (let i = 0; i < result.data.length; i++) {
        categoryXAxis.push(result.data[i].dateGroup);
        // console.log(result.data[i].dateGroup);
        categorySeries.push(result.data[i].totalCount);
        // console.log(result.data[i].totalCount);

    }
    // console.log(categoryXAxis);
    // console.log(categorySeries);
    // console.log(result.data.length);
    } catch (error) {
        categoryXAxis=[0,0,0,0]
        categorySeries=[0,0,0,0]
    }
}

let setEcharts = echarts;
const echartsData = {
    xAxis: {
        name: '日期',
        type: 'category',
        data: categoryXAxis
    },
    yAxis: {
        name: '人数',
        type: 'value'
    },
    series: [
        {
            data: categorySeries,
            type: 'line'
        }
    ]
}
const gaugeData = {
    series: [
        {
            type: 'gauge',
            startAngle: 180,
            endAngle: 0,
            center: ['50%', '75%'],
            radius: '90%',
            min: 0,
            max: 100,
            splitNumber: 10,
            axisLine: {
                lineStyle: {
                    width: 6,
                    color: [
                        [0.25, '#FF6E76'],
                        [0.5, '#FDDD60'],
                        [0.75, '#58D9F9'],
                        [1, '#7CFFB2']
                        // [1,'#000000']
                    ]
                }
            },
            pointer: {
                icon: 'path://M12.8,0.7l12,40.1H0.7L12.8,0.7z',
                length: '12%',
                width: 20,
                offsetCenter: [0, '-60%'],
                itemStyle: {
                    color: 'auto'
                }
            },
            axisTick: {
                length: 12,
                lineStyle: {
                    color: 'auto',
                    width: 2
                }
            },
            splitLine: {
                length: 20,
                lineStyle: {
                    color: 'auto',
                    width: 5
                }
            },
            axisLabel: {
                color: '#464646',
                fontSize: 20,
                distance: -60,
                rotate: 'tangential',

            },
            title: {
                offsetCenter: [0, '-10%'],
                fontSize: 20
            },
            detail: {
                fontSize: 30,
                offsetCenter: [0, '-35%'],
                valueAnimation: true,
                formatter: function (value) {
                    return Math.round(value) + '%';
                },
                color: 'inherit'
            },
            data: [
                {
                    value: 0 * 100,
                    name: '试题正确率'
                }
            ]
        }
    ]
};
const gaugeResult = async () => {
    try {
        let result = await getDataService(route.params.id);
    // console.log(result.code);
    // console.log(result.data.toFixed(2));
        gaugeSeriesData.value = result.data.toFixed(2);
    // console.log(typeof(Number(gaugeSeriesData.value)));
    // console.log(typeof(gaugeData.series[0].data[0].value));
        gaugeData.series[0].data[0].value = Number(gaugeSeriesData.value) * 100
    } catch (error) {
        
    }


    // console.log(route.params.id);
}
async function initCategoryChart() {
    await categoryResult();
    let chart = setEcharts.init(document.getElementById("chartsOne"))
    chart.setOption(echartsData)
}
async function initGaugeChart() {
    await gaugeResult();
    //console.log(gaugeData.series[0].data.value);
    let chartS = setEcharts.init(document.getElementById("chartsTwo"))
    chartS.setOption(gaugeData)
}
onMounted(() => {
    initCategoryChart();
    initGaugeChart();
});
onUnmounted(() => {
    setEcharts.dispose;
});
const goBack = () => {
  router.back();
}


</script>
<template>
    <el-container style="max-width: 1200px; margin: auto;">
        <el-header width="100%" height="60px">
          <el-page-header @back="goBack()" class="pageHeader">
            <template #content>
              <span>试题数据</span>
            </template>
          </el-page-header>
        </el-header>
        <el-main>
            <div class="chart-container">
                <div id="chartsOne" style="width: 500px;height: 500px;"></div>
                <div id="chartsTwo" style="width: 600px;height: 500px;"></div>
            </div>
        </el-main>
    </el-container>
</template>
<style scoped>
.pageHeader {
  display: flex;
  align-items: center;
  justify-content: flex-start;
}
.chart-container {
    display: flex;
    justify-content: center;
}

#chartsOne,
#chartsTwo {
    margin: 10px;
}

</style>