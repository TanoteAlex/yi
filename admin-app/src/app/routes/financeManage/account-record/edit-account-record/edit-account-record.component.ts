
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { SUCCESS } from '../../../models/constants.model';
import { AccountRecordService } from '../../../services/account-record.service';
import { AccountRecord } from '../../../models/original/account-record.model';

@Component({
  selector: 'edit-account-record',
  templateUrl: './edit-account-record.component.html',
  styleUrls: ['./edit-account-record.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditAccountRecordComponent implements OnInit {

    submitting = false;

accountRecord: AccountRecord;

    constructor(private route: ActivatedRoute,private router:Router,private accountRecordService: AccountRecordService, public msgSrv:
        NzMessageService,private modalService: NzModalService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.accountRecordService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.accountRecord = response.data;
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

    confirmSub(formValue){
        if (formValue) {
            this.submitting = true;
            const messageId = this.msgSrv.loading("正在提交...").messageId;
            formValue.obj.id = this.accountRecord.id;
            this.accountRecordService.update(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/account-record/list']);
                } else {
                    this.msgSrv.error('请求失败'+response.message);
                }
                this.msgSrv.remove(messageId);
                this.submitting = false;
            }, error => {
                this.msgSrv.error('请求错误'+error.message);
                this.msgSrv.remove(messageId);
                this.submitting = false;
            });
        }
    }
}
