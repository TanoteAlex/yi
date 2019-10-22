import { Component, EventEmitter, Input, Output } from '@angular/core';
import { SUCCESS } from '../../models/constants.model';
import { NzMessageService } from 'ng-zorro-antd';
import { SaleOrderService } from '../../services/sale-order.service';
import { ExpressService } from '../../services/express.service';

@Component({
  selector: 'nz-deliver-goods',
  template: `
    <a style="color: #007bff" *ngIf="showButton" (click)="showModal()">{{buttonName}}
      <ng-content></ng-content>
    </a>
    <button nz-button nzType="primary" type="submit" *ngIf="!showButton" (click)="showModal()">{{buttonName}}

    </button>
    <nz-modal nzWidth="60rem" *ngIf="comment" [(nzVisible)]="isVisible" [nzTitle]="title"
              (nzOnCancel)="handleCancel()" (nzOnOk)="handleOk()">
      <nz-card>
        <nz-form-item>
          <nz-form-label nzXs="3" nzSm="3">订单编号</nz-form-label>
          <nz-form-control nzXs="8" nzSm="8">{{saleOrderView.orderNo}}</nz-form-control>
          <nz-form-label nzXs="3" nzSm="3">下单时间</nz-form-label>
          <nz-form-control nzXs="8" nzSm="8">{{saleOrderView.createTime}}</nz-form-control>
        </nz-form-item>
        <nz-form-item>
          <nz-form-label nzXs="3" nzSm="3">商品信息</nz-form-label>
          <div *ngFor="let commoditys of saleOrderView.saleOrderItems" style="display: table">
            <div style=" float: left">
              <img class="table-img-customer" [src]="commoditys?.commodity.imgPath">&nbsp;&nbsp;
            </div>
            <div style=" float: left">
              {{commoditys?.commodity.commodityShortName}}<br><br>
              ￥{{commoditys.price}}&nbsp;({{commoditys.quantity}}件)
            </div>
          </div>
        </nz-form-item>
        <nz-form-item>
          <nz-form-label nzXs="3" nzSm="3">收货人信息</nz-form-label>
          <nz-form-control nzXs="15" nzSm="15">
            {{saleOrderView?.consignee}},{{saleOrderView?.consigneePhone}},
            {{saleOrderView.consigneeAddr.substring(0, 6)|districtPipe}}
            {{saleOrderView.consigneeAddr.substring(6, 12)|districtPipe}}
            {{saleOrderView.consigneeAddr.substring(12, 18)|districtPipe}}
            {{saleOrderView.consigneeAddr.substring(18, 30)}}
          </nz-form-control>
          <nz-form-control>
            <a [routerLink]="'/dashboard/sale-order/editRecevingAddress/'+saleOrderView.id">修改收货地址</a>
          </nz-form-control>
        </nz-form-item>
      </nz-card>
      <nz-card>
        <nz-col nzMd="24" nzSm="24">
          <nz-form-item>
            <nz-form-label nzXs="3" nzSm="3">物流公司</nz-form-label>
            <nz-form-control nzXs="8" nzSm="8">
              <select nz-input [(ngModel)]="logisticsCompany">
                <option *ngFor="let mt of logistics" [value]="mt.key">{{mt.value}}</option>
              </select>
            </nz-form-control>
          </nz-form-item>
        </nz-col>
        <nz-col nzMd="24" nzSm="24">
          <nz-form-item>
            <nz-form-label nzXs="3" nzSm="3">物流单号</nz-form-label>
            <nz-form-control nzXs="8" nzSm="8">
              <input nz-input [(ngModel)]="logisticsNo" placeholder="请填写物流单号"/>
            </nz-form-control>
          </nz-form-item>
        </nz-col>
      </nz-card>
    </nz-modal>
  `,
  styles: [],
})
export class DeliverGoodsComponent {
  isVisible = false;
  @Input()
  title = '';
  @Input()
  content = '';
  @Input()
  buttonName = 'Show Modal';
  @Input()
  showButton = true;
  @Input()
  comment = false;
  @Input()
  id = 0;
  @Input()
  saleOrderView;

  @Output()
  ok: EventEmitter<any> = new EventEmitter();

  logisticsCompany = '';
  logisticsNo = '';

  constructor(public msg: NzMessageService, public saleOrderService: SaleOrderService, private expressService: ExpressService) {
  }

  showModal(): void {
    this.isVisible = true;
  }

  handleOk(): void {
    this.expressService.queryLogistics(this.logisticsCompany, this.logisticsNo).subscribe(response => {
      if (JSON.parse(response).message == 'ok') {
        this.saleOrderService.delivery(this.id, this.logisticsNo, this.logisticsCompany).subscribe(response => {
          if (response.result == SUCCESS) {
            this.msg.success('发货成功');
            this.ok.emit('ok');
          } else {
            this.msg.error('请求失败', response.message);
          }
        }, error => {
          this.msg.error('请求错误', error.message);
        });
      } else {
        this.msg.error('请填写有效单号', response.message);
      }
    }, error => {
      this.msg.error('请求错误', error.message);
    });
    this.isVisible = false;
  }

  handleCancel(): void {
    this.isVisible = false;
  }

  logistics = [
    { value: '跨越速运', key: 'kuayuesuyun' },
    { value: '安能物流', key: 'annengwuliu' },
    { value: '百世速递', key: 'baishisudi' },
    { value: '邮政小包', key: 'youzhengxiaobao' },
    { value: '天天快递', key: 'tiantian' },
    { value: '邮政包裹', key: 'youzhengguonei' },
    { value: '中通速递', key: 'zhongtong' },
    { value: '申通快递', key: 'shentong' },
    { value: '顺丰快递', key: 'shunfeng' },
    { value: '圆通速递', key: 'yuantong' },
    { value: '韵达快运', key: 'yunda' },
    { value: '德邦物流', key: 'debangwuliu' },
    { value: '国通快递', key: 'guotongkuaidi' },
    { value: '如风达', key: 'rufengda' },
    { value: '邮政国际包裹', key: 'youzhengguoji' },
    { value: 'aae全球专递', key: 'aae' },
    { value: '安捷快递', key: 'anjie' },
    { value: '安信达快递', key: 'anxindakuaixi' },
    { value: '彪记快递', key: 'biaojikuaidi' },
    { value: 'bht', key: 'bht' },
    { value: '百福东方国际物流', key: 'baifudongfang' },
    { value: '中国东方', key: 'coe' },
    { value: '长宇物流', key: 'changyuwuliu' },
    { value: '大田物流', key: 'datianwuliu' },
    { value: 'dhl', key: 'dhl' },
    { value: 'dpex', key: 'dpex' },
    { value: 'd速快递', key: 'dsukuaidi' },
    { value: '递四方', key: 'disifang' },
    { value: 'ems快递', key: 'ems' },
    { value: 'fedex', key: 'fedex' },
    { value: '飞康达物流', key: 'feikangda' },
    { value: '凤凰快递', key: 'fenghuangkuaidi' },
    { value: '飞快达', key: 'feikuaida' },
    { value: '港中能达物流', key: 'ganzhongnengda' },
    { value: '广东邮政物流', key: 'guangdongyouzhengwuliu' },
    { value: '共速达', key: 'gongsuda' },
    { value: '汇通快运', key: 'huitongkuaidi' },
    { value: '恒路物流', key: 'hengluwuliu' },
    { value: '华夏龙物流', key: 'huaxialongwuliu' },
    { value: '海红', key: 'haihongwangsong' },
    { value: '海外环球', key: 'haiwaihuanqiu' },
    { value: '佳怡物流', key: 'jiayiwuliu' },
    { value: '京广速递', key: 'jinguangsudikuaijian' },
    { value: '急先达', key: 'jixianda' },
    { value: '佳吉物流', key: 'jjwl' },
    { value: '加运美物流', key: 'jymwl' },
    { value: '金大物流', key: 'jindawuliu' },
    { value: '嘉里大通', key: 'jialidatong' },
    { value: '晋越快递', key: 'jykd' },
    { value: '快捷速递', key: 'kuaijiesudi' },
    { value: '联邦快递', key: 'lianb' },
    { value: '联昊通物流', key: 'lianhaowuliu' },
    { value: '龙邦物流', key: 'longbanwuliu' },
    { value: '立即送', key: 'lijisong' },
    { value: '乐捷递', key: 'lejiedi' },
    { value: '民航快递', key: 'minghangkuaidi' },
    { value: '美国快递', key: 'meiguokuaidi' },
    { value: '门对门', key: 'menduimen' },
    { value: 'OCS', key: 'ocs' },
    { value: '配思货运', key: 'peisihuoyunkuaidi' },
    { value: '全晨快递', key: 'quanchenkuaidi' },
    { value: '全峰快递', key: 'quanfengkuaidi' },
    { value: '全际通物流', key: 'quanjitong' },
    { value: '全日通快递', key: 'quanritongkuaidi' },
    { value: '全一快递', key: 'quanyikuaidi' },
    { value: '三态速递', key: 'santaisudi' },
    { value: '盛辉物流', key: 'shenghuiwuliu' },
    { value: '速尔物流', key: 'sue' },
    { value: '盛丰物流', key: 'shengfeng' },
    { value: '赛澳递', key: 'saiaodi' },
    { value: '天地华宇', key: 'tiandihuayu' },
    { value: 'tnt', key: 'tnt' },
    { value: 'ups', key: 'ups' },
    { value: '万家物流', key: 'wanjiawuliu' },
    { value: '文捷航空速递', key: 'wenjiesudi' },
    { value: '伍圆', key: 'wuyuan' },
    { value: '万象物流', key: 'wxwl' },
    { value: '新邦物流', key: 'xinbangwuliu' },
    { value: '信丰物流', key: 'xinfengwuliu' },
    { value: '亚风速递', key: 'yafengsudi' },
    { value: '一邦速递', key: 'yibangwuliu' },
    { value: '优速物流', key: 'youshuwuliu' },
    { value: '远成物流', key: 'yuanchengwuliu' },
    { value: '源伟丰快递', key: 'yuanweifeng' },
    { value: '元智捷诚快递', key: 'yuanzhijiecheng' },
    { value: '运通快递', key: 'yuntongkuaidi' },
    { value: '越丰物流', key: 'yuefengwuliu' },
    { value: '源安达', key: 'yad' },
    { value: '银捷速递', key: 'yinjiesudi' },
    { value: '宅急送', key: 'zhaijisong' },
    { value: '中铁快运', key: 'zhongtiekuaiyun' },
    { value: '中邮物流', key: 'zhongyouwuliu' },
    { value: '忠信达', key: 'zhongxinda' },
    { value: '芝麻开门', key: 'zhimakaimen' },

  ];

}
