import {Component, OnInit} from '@angular/core';
import {NzMessageService} from "ng-zorro-antd";
import {Homepage} from "../models/original/home-data.model";
import {HomepageService} from "../services/homepage-service";
import {EChartOption} from 'echarts';

declare var echarts;

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.less'],
})
export class DashboardComponent implements OnInit {

  homepage: Homepage = new Homepage();
  chartOption: EChartOption;
  XDate = []; //折线图X轴数据集合
  orderNum = []; //折线图Y轴新增订单集合
  memberNum = []; //折线图Y轴新增会员集合
  supplierNum = []; //折线图Y轴新增供应商集合

  constructor(public msg: NzMessageService, public homepageService: HomepageService) {
  }

  getData() {
    this.XDate = [],
      this.orderNum = [],
      this.memberNum = [],
      this.supplierNum = [],
      this.homepageService.queryPlatformData().subscribe(response => {
        this.homepage = response.data;
        this.homepage.dailyAddNums.forEach(e => {
          this.XDate.push(e.date)
          this.orderNum.push(e.dailyAddOrderNum)
          this.memberNum.push(e.dailyAddMemberNum)
          this.supplierNum.push(e.dailyAddSupplierNum)
        });
        this.chartOption = {
          title: {
            text: '最近7天数据统计',
          },
          tooltip: {
            trigger: 'axis'
          },
          legend: {
            data: ['订单数', '新增会员数', '新增供应商数']
          },
          toolbox: {
            show: true,
            feature: {
              mark: {show: true},
              dataView: {show: true, readOnly: false},
              magicType: {show: true, type: ['line', 'bar', 'stack', 'tiled']},
              restore: {show: true},
              saveAsImage: {show: true}
            }
          },
          calculable: true,
          xAxis: [
            {
              type: 'category',
              boundaryGap: false,
              data: this.XDate
            }
          ],
          yAxis: [
            {
              type: 'value'
            }
          ],
          series: [
            {
              name: '订单数',
              type: 'line',
              data: this.orderNum
            },
            {
              name: '新增会员数',
              type: 'line',
              data: this.memberNum
            },
            {
              name: '新增供应商数',
              type: 'line',
              data: this.supplierNum
            }
          ]
        };
        echarts.init(document.getElementById('echart')
        ).setOption(this.chartOption);
      }, error => {
        this.msg.error('请求错误' + error.message);
      });
  }

  ngOnInit() {
    // let frist = true;
    // if(frist){
      this.getData();
    //   frist = false;
    // }
    // setInterval(()=>{
    //   this.getData();
    // },60000)
  }
}
