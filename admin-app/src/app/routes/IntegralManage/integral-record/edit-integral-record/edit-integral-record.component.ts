
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { SUCCESS } from '../../../models/constants.model';
import { IntegralRecordService } from '../../../services/integral-record.service';
import { IntegralRecord } from '../../../models/original/integral-record.model';

@Component({
  selector: 'edit-integral-record',
  templateUrl: './edit-integral-record.component.html',
  styleUrls: ['./edit-integral-record.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditIntegralRecordComponent implements OnInit {

    submitting = false;

integralRecord: IntegralRecord;

    constructor(private route: ActivatedRoute,private router:Router,private integralRecordService: IntegralRecordService, public msgSrv:
        NzMessageService,private modalService: NzModalService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.integralRecordService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.integralRecord = response.data;
            } else {
                this.msgSrv.error('请求失败', response.message);
            }
        }, error => {
            this.msgSrv.error('请求错误', error.message);
        });
    }

    onTransmitFormValueSubscription(event) {
        if (event) {
            this.submitForm(event)
        }
    }

    submitForm(formValue) {
        this.modalService.confirm({
            nzTitle  : '<i>提示！</i>',
            nzContent: '<b>确认要提交吗？</b>',
            nzOnOk   : () => this.confirmSub(formValue)
        });
    }

    confirmSub(formValue){
        if (formValue) {
            this.submitting = true;
            const messageId = this.msgSrv.loading("正在提交...").messageId;
            formValue.obj.id = this.integralRecord.id;
            this.integralRecordService.update(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/integral-record/list']);
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
