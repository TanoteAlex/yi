import {Component, OnInit, NO_ERRORS_SCHEMA} from '@angular/core';
import {NzMessageService, NzModalService} from "ng-zorro-antd";
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

    loginSupplier;
    supplierId = 0;
    supplierName: string = '美周壹壹优选';

    constructor(public msg: NzMessageService, public homepageService: HomepageService) {
    }

    getData() {

    }

    ngOnInit() {

        this.loginSupplier = JSON.parse(localStorage.getItem("loginSupplier"));
        if (this.loginSupplier != null) {
            this.supplierName = this.loginSupplier.supplierName;
            this.supplierId = this.loginSupplier.id;
        }

        this.homepageService.queryForSupplier().subscribe(response => {
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
                    data: ['订单数']
                },
                toolbox: {
                    show: true,
                    feature: {
                        mark: { show: true },
                        dataView: { show: true, readOnly: false },
                        magicType: { show: true, type: ['line', 'bar', 'stack', 'tiled'] },
                        restore: { show: true },
                        saveAsImage: { show: true }
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
                    }
                ]
            };
            echarts.init(document.getElementById('echart')
            ).setOption(this.chartOption);
        }, error => {
            this.msg.error(error.message ? error.message : '请求错误');
        });

    }
}
