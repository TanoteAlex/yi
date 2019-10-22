import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {SupplierAccountRecordService} from '../../../services/supplier-account-record.service';
import {SupplierAccountRecord} from "../../../models/original/supplier-account-record.model";
import {SUCCESS} from "../../../models/constants.model";

@Component({
  selector: 'edit-supplier-account-record',
  templateUrl: './edit-supplier-account-record.component.html',
  styleUrls: ['./edit-supplier-account-record.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditSupplierAccountRecordComponent implements OnInit {

  submitting = false;

  supplierAccountRecord: SupplierAccountRecord;

  constructor(private route: ActivatedRoute, private router: Router, private supplierAccountRecordService: SupplierAccountRecordService, public msgSrv:
    NzMessageService, private modalService: NzModalService) {
  }

  ngOnInit() {
    this.route.params.subscribe((params: ParamMap) => {
      this.getById(params["objId"]);
    });
  }

  getById(objId) {
    this.supplierAccountRecordService.getBoById(objId).subscribe(response => {
      if (response.result == SUCCESS) {
        this.supplierAccountRecord = response.data;
      } else {
        this.msgSrv.error('请求失败', response.message);
      }
    }, error => {
      this.msgSrv.error('请求错误', error.message);
    });
  }

  onTransmitFormValueSubscription(event) {
    if (event) {
      this.confirmSub(event)
    }
  }

  confirmSub(formValue) {
    if (formValue) {
      this.submitting = true;
      const messageId = this.msgSrv.loading("正在提交...").messageId;
      formValue.obj.id = this.supplierAccountRecord.id;
      this.supplierAccountRecordService.update(formValue.obj).subscribe(response => {
        if (response.result == SUCCESS) {
          this.msgSrv.success("操作成功");
          this.router.navigate(['/supplier-account-record/list']);
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
