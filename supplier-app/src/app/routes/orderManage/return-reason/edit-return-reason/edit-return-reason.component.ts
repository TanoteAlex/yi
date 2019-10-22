import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {SUCCESS} from '../../../models/constants.model';
import {ReturnReasonService} from '../../../services/return-reason.service';
import {ReturnReason} from '../../../models/original/return-reason.model';

@Component({
  selector: 'edit-return-reason',
  templateUrl: './edit-return-reason.component.html',
  styleUrls: ['./edit-return-reason.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditReturnReasonComponent implements OnInit {

    submitting = false;

returnReason: ReturnReason;

    constructor(private route: ActivatedRoute,private router:Router,private returnReasonService: ReturnReasonService, public msgSrv:
        NzMessageService,private modalService: NzModalService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.returnReasonService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.returnReason = response.data;
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
            formValue.obj.id = this.returnReason.id;
            this.returnReasonService.update(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/return-reason/list']);
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
