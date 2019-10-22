import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {SUCCESS} from '../../../models/constants.model';
import {CouponService} from '../../../services/coupon.service';
import {Coupon} from '../../../models/original/coupon.model';

@Component({
    selector: 'edit-voucher',
    templateUrl: './edit-voucher.component.html',
    styleUrls: ['./edit-voucher.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class EditVoucherComponent implements OnInit {

    submitting = false;

    coupon: Coupon;

    constructor(private route: ActivatedRoute, private router: Router, private couponService: CouponService, public msgSrv:
        NzMessageService, private modalService: NzModalService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId) {
        this.couponService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.coupon = response.data;
            } else {
                this.msgSrv.error(response.message ? response.message : "请求失败");
            }
        }, error => {
            this.msgSrv.error(error.message ? error.message : "请求错误");
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
            formValue.obj.id = this.coupon.id;
            this.couponService.update(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/voucher/list']);
                } else {
                    this.msgSrv.error(response.message ? response.message : "请求失败");
                }
                this.msgSrv.remove(messageId);
                this.submitting = false;
            }, error => {
                this.msgSrv.error(error.message ? error.message : "请求错误");
                this.msgSrv.remove(messageId);
                this.submitting = false;
            });
        }
    }
}
