
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import {SUCCESS} from '../../../models/constants.model';
import { CouponGrantRecordService } from '../../../services/coupon-grant-record.service';
import { CouponGrantRecordBo } from '../../../models/domain/bo/coupon-grant-record-bo.model';

@Component({
  selector: 'edit-voucher-grant-record',
  templateUrl: './edit-voucher-grant-record.component.html',
  styleUrls: ['./edit-voucher-grant-record.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditVoucherGrantRecordComponent implements OnInit {

    submitting = false;

    couponGrantRecord: CouponGrantRecordBo;

    constructor(private route: ActivatedRoute,private router:Router,private couponGrantRecordService: CouponGrantRecordService, public msgSrv:
        NzMessageService,private modalService: NzModalService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.couponGrantRecordService.getBoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.couponGrantRecord = response.data;
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
            formValue.obj.id = this.couponGrantRecord.id;
            this.couponGrantRecordService.update(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/coupon-grant-record/list']);
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
