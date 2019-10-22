import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {PlatformSaleStat} from '../../../models/original/platform-sale-stat.model';
import {PlatformSaleStatService} from '../../../services/platform-sale-stat.service';
import {SUCCESS} from '../../../models/constants.model';

@Component({
    selector: 'add-platform-sale-stat',
    templateUrl: './add-platform-sale-stat.component.html',
    styleUrls: ['./add-platform-sale-stat.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddPlatformSaleStatComponent implements OnInit {

    submitting = false;

    platformSaleStat: PlatformSaleStat;

    constructor(private router:Router,private platformSaleStatService: PlatformSaleStatService, public msgSrv: NzMessageService,
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
            this.platformSaleStatService.add(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/platform-sale-stat/list']);
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
