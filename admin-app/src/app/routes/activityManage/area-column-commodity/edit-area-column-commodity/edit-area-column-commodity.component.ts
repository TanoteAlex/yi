import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { SUCCESS } from '../../../models/constants.model';
import { AreaColumnCommodityService } from '../../../services/area-column-commodity.service';
import { AreaColumnCommodityBo } from '../../../models/domain/bo/area-column-commodity-bo.model';

@Component({
  selector: 'edit-area-column-commodity',
  templateUrl: './edit-area-column-commodity.component.html',
  styleUrls: ['./edit-area-column-commodity.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class EditAreaColumnCommodityComponent implements OnInit {

  submitting = false;

  areaColumnCommodity: AreaColumnCommodityBo;

  constructor(private route: ActivatedRoute, private router: Router, private areaColumnCommodityService: AreaColumnCommodityService, public msgSrv:
    NzMessageService, private modalService: NzModalService) {
  }

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.getById(params);
    });
  }

  getById(objId) {
    this.areaColumnCommodityService.postData('getBoById', objId).subscribe(response => {
      if (response.result == SUCCESS) {
        this.areaColumnCommodity = response.data;
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
      // formValue.obj.id = this.areaColumnCommodity.id;
      this.areaColumnCommodityService.update(formValue.obj).subscribe(response => {
        if (response.result == SUCCESS) {
          this.msgSrv.success('操作成功');
          this.router.navigate(['/dashboard/area-column-commodity/list']);
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
