import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {Coupon} from '../../../models/original/coupon.model';
import {CouponService} from '../../../services/coupon.service';
import {SUCCESS} from '../../../models/constants.model';

@Component({
    selector: 'add-coupon',
    templateUrl: './add-buy-coupons.component.html',
    styleUrls: ['./add-buy-coupons.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddBuyCouponsComponent implements OnInit {

    submitting = false;

    coupon: Coupon;

    constructor(private router:Router,private couponService: CouponService, public msgSrv: NzMessageService,
        private modalService: NzModalService) {
    }

    ngOnInit() {
    }

    onTransmitFormValueSubscription(event) {
        if (event) {
            this.confirmSub(event)
        }
    }

    confirmSub(formValue){
        if (formValue) {
            this.submitting = true;
            formValue.obj.couponType=2;
            const messageId = this.msgSrv.loading("正在提交...").messageId;
            this.couponService.add(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/buy-coupons/list']);
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
