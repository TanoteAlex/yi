
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { AfterSaleReasonService } from '../../../services/after-sale-reason.service';
import { AfterSaleReasonBo } from '../../../models/domain/bo/after-sale-reason-bo.model';
import {SUCCESS} from "../../../models/constants.model";

@Component({
  selector: 'edit-after-sale-reason',
  templateUrl: './edit-after-sale-reason.component.html',
  styleUrls: ['./edit-after-sale-reason.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditAfterSaleReasonComponent implements OnInit {

    submitting = false;

    afterSaleReason: AfterSaleReasonBo;

    constructor(private route: ActivatedRoute,private router:Router,private afterSaleReasonService: AfterSaleReasonService, public msgSrv:
        NzMessageService,private modalService: NzModalService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.afterSaleReasonService.getBoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.afterSaleReason = response.data;
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
            formValue.obj.id = this.afterSaleReason.id;
            this.afterSaleReasonService.update(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/after-sale-reason/list']);
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
