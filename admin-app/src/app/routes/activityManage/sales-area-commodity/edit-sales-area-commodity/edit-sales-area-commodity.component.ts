import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { SUCCESS } from '../../../models/constants.model';
import { SalesAreaCommodityService } from '../../../services/sales-area-commodity.service';
import { SalesAreaCommodityBo } from '../../../models/domain/bo/sales-area-commodity-bo.model';
import { SalesAreaCommodityIdBo } from '../../../models/domain/bo/sales-area-commodity-id-bo';

@Component({
  selector: 'edit-sales-area-commodity',
  templateUrl: './edit-sales-area-commodity.component.html',
  styleUrls: ['./edit-sales-area-commodity.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class EditSalesAreaCommodityComponent implements OnInit {

  submitting = false;

  salesAreaCommodity: SalesAreaCommodityBo;

  constructor(private route: ActivatedRoute, private router: Router, private salesAreaCommodityService: SalesAreaCommodityService, public msgSrv:
    NzMessageService, private modalService: NzModalService) {
  }

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.getById(params);
    });
  }

  getById(objId) {
    this.salesAreaCommodityService.postData("getBoById",objId).subscribe(response => {
      if (response.result == SUCCESS) {
        this.salesAreaCommodity = response.data;
      } else {
        this.msgSrv.error('请求失败', response.message);
      }
    }, error => {
      this.msgSrv.error('请求错误', error.message);
    });
  }

  onTransmitFormValueSubscription(event) {
    if (event) {
      this.confirmSub(event);
    }
  }

  confirmSub(formValue) {
    if (formValue) {
      this.submitting = true;
      const messageId = this.msgSrv.loading('正在提交...').messageId;
      formValue.obj.salesAreaCommodityId = this.salesAreaCommodity.salesAreaCommodityId;
      this.salesAreaCommodityService.update(formValue.obj).subscribe(response => {
        if (response.result == SUCCESS) {
          this.msgSrv.success('操作成功');
          this.router.navigate(['/dashboard/sales-area-commodity/list']);
        } else {
          this.msgSrv.error('请求失败' + response.message);
        }
        this.msgSrv.remove(messageId);
        this.submitting = false;
      }, error => {
        this.msgSrv.error('请求错误' + error.message);
        this.msgSrv.remove(messageId);
        this.submitting = false;
      });
    }
  }
}
