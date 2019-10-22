import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {OrderSetting} from '../../../models/original/order-setting.model';
import {OrderSettingService} from '../../../services/order-setting.service';
import {SUCCESS} from '../../../models/constants.model';

@Component({
    selector: 'add-order-setting',
    templateUrl: './add-order-setting.component.html',
    styleUrls: ['./add-order-setting.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddOrderSettingComponent implements OnInit {

    submitting = false;

    orderSetting: OrderSetting;

    constructor(private router:Router,private orderSettingService: OrderSettingService, public msgSrv: NzMessageService,
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
            const messageId = this.msgSrv.loading("正在提交...").messageId;
            this.orderSettingService.add(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/order-setting/list']);
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
