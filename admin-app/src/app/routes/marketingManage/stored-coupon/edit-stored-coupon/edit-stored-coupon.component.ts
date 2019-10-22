import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {SUCCESS} from '../../../models/constants.model';
import {CouponService} from '../../../services/coupon.service';
import {Coupon} from '../../../models/original/coupon.model';

@Component({
  selector: 'edit-stored-coupon',
  templateUrl: './edit-stored-coupon.component.html',
  styleUrls: ['./edit-stored-coupon.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditStoredCouponComponent implements OnInit {

    submitting = false;

coupon: Coupon;

    constructor(private route: ActivatedRoute,private router:Router,private couponService: CouponService, public msgSrv:
        NzMessageService,private modalService: NzModalService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.couponService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.coupon = response.data;
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
            formValue.obj.id = this.coupon.id;

            this.couponService.update(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/stored-coupon/list']);
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
