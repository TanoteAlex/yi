
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import {SUCCESS} from '../../../models/constants.model';
import { CouponGrantConfigService } from '../../../services/coupon-grant-config.service';
import { CouponGrantConfigBo } from '../../../models/domain/bo/coupon-grant-config-bo.model';

@Component({
  selector: 'edit-voucher-grant-config',
  templateUrl: './edit-voucher-grant-config.component.html',
  styleUrls: ['./edit-voucher-grant-config.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditVoucherGrantConfigComponent implements OnInit {

    submitting = false;

    couponGrantConfig: CouponGrantConfigBo;

    constructor(private route: ActivatedRoute,private router:Router,private couponGrantConfigService: CouponGrantConfigService, public msgSrv:
        NzMessageService,private modalService: NzModalService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.couponGrantConfigService.getById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.couponGrantConfig = response.data;
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
            formValue.obj.id = this.couponGrantConfig.id;
            this.couponGrantConfigService.update(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/voucher-grant-config/list']);
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
