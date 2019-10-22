
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { SUCCESS } from '../../../models/constants.model';
import { IntegralCashService } from '../../../services/integral-cash.service';
import { IntegralCashBo } from '../../../models/domain/bo/integral-cash-bo.model';

@Component({
  selector: 'edit-integral-cash',
  templateUrl: './edit-integral-cash.component.html',
  styleUrls: ['./edit-integral-cash.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditIntegralCashComponent implements OnInit {

    submitting = false;

    integralCash: IntegralCashBo;

    constructor(private route: ActivatedRoute,private router:Router,private integralCashService: IntegralCashService, public msgSrv:
        NzMessageService,private modalService: NzModalService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.integralCashService.getBoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.integralCash = response.data;
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
            formValue.obj.id = this.integralCash.id;
            this.integralCashService.update(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/integral-cash/list']);
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
