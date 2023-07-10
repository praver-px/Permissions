<template>
  <div>
    <el-row :gutter="10" style="margin-bottom: 20px">
      <el-col :span="6">
        <el-card style="color: #409EFF">
          <div> <i class="el-icon-user-solid"/> 用户总数</div>
          <div style="padding: 10px 0; text-align: center;font-weight: bold">
            100
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #F56C6C">
          <div><i class="el-icon-money"/> 销售总量</div>
          <div style="padding: 10px 0; text-align: center;font-weight: bold">
            ￥ 100000
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #E6A23C">
          <div><i class="el-icon-bank-card"/> 收益总额</div>
          <div style="padding: 10px 0; text-align: center;font-weight: bold">
            ￥1000
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #67C23A">
          <div><i class="el-icon-s-shop"/> 门店总数</div>
          <div style="padding: 10px 0; text-align: center;font-weight: bold">
            1000
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row justify="center">
      <el-col :span="12">
        <el-card id="lineCharts" style="width: 80%;height: 500px"></el-card>
      </el-col>
      <el-col :span="12">
        <el-card id="pieCharts" style="width: 80%;height: 500px"></el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  name: "Home",
  data() {
    return {}
  },
  mounted() {// 页面元素宣传之后在触发
    var lineChartDom = document.getElementById('lineCharts');
    var lineChart = echarts.init(lineChartDom);
    var lineOption = {
      title: {
        text: "各季度入会成员数量统计图",
        subtext: '趋势图',
        left: 'center'
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      xAxis: {
        type: 'category',
        data: ['第一季度', '第二季度', '第三季度', '第四季度',]
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          data: [150, 230, 224, 218, 135, 147, 260],
          type: 'line',
          smooth: true,
        },
        {
          data: [150, 230, 224, 218, 135, 147, 260],
          type: 'bar'
        }
      ]
    };
    this.request.get("/echarts/members").then(res => {
      lineOption.series[0].data = res.data
      lineOption.series[1].data = res.data
      lineChart.setOption(lineOption);

      pieOption.series[0].data = [
        {name: '第一季度', value: res.data[0]},
        {name: '第二季度', value: res.data[1]},
        {name: '第三季度', value: res.data[2]},
        {name: '第四季度', value: res.data[3]},
      ]
      pieChart.setOption(pieOption);
    })


    // 饼图
    var pieChartDom = document.getElementById('pieCharts');
    var pieChart = echarts.init(pieChartDom);
    var pieOption = {
      title: {
        text: "各季度入会成员数量统计图",
        subtext: '比例图',
        left: 'center'
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        top: '5%',
        left: 'left'
      },
      series: [
        {
          type: 'pie',
          radius: ['40%', '70%'],
          avoidLabelOverlap: true,
          itemStyle: {
            borderRadius: 10,
            borderColor: '#fff',
            borderWidth: 2
          },
          label: {
            show: false,
            position: 'center',
            normal: {
              // show: true,
              // position: "inner",
              textStyle: {
                fontWeight: 300,
                fontSize: 20
              },
              formatter: '{d}%'
            }
          },
          emphasis: {
            label: {
              show: true,
              fontSize: 40,
              fontWeight: 'bold'
            }
          },
          data: [], // {value: 1048, name: 'Search Engine'},
        }
      ]
    };
  }
}
</script>
