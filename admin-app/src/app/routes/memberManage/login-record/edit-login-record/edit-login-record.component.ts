
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { LoginRecordService } from '../../../services/login-record.service';
import { LoginRecordBo } from '../../../models/domain/bo/login-record-bo.model';
import {SUCCESS} from "../../../models/constants.model";

@Component({
  selector: 'edit-login-record',
  templateUrl: './edit-login-record.component.html',
  styleUrls: ['./edit-login-record.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditLoginRecordComponent implements OnInit {

    submitting = false;

    loginRecord: LoginRecordBo;

    constructor(private route: ActivatedRoute,private router:Router,private loginRecordService: LoginRecordService, public msgSrv:
        NzMessageService,private modalService: NzModalService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.loginRecordService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.loginRecord = response.data;
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
            formValue.obj.id = this.loginRecord.id;
            this.loginRecordService.update(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/login-record/list']);
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
