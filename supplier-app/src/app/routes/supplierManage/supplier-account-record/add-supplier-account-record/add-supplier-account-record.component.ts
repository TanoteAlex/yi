import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {SupplierAccountRecordService} from '../../../services/supplier-account-record.service';
import {SupplierAccountRecord} from "../../../models/original/supplier-account-record.model";
import {SUCCESS} from "../../../models/constants.model";

@Component({
  selector: 'add-supplier-account-record',
  templateUrl: './add-supplier-account-record.component.html',
  styleUrls: ['./add-supplier-account-record.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class AddSupplierAccountRecordComponent implements OnInit {

  submitting = false;

  supplierAccountRecord: SupplierAccountRecord;

  constructor(private router: Router, private supplierAccountRecordService: SupplierAccountRecordService, public msgSrv: NzMessageService,
              private modalService: NzModalService) {
  }

  ngOnInit() {
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
      this.supplierAccountRecordService.add(formValue.obj).subscribe(response => {
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
