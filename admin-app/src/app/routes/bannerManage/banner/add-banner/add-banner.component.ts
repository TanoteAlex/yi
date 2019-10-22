import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {Banner} from '../../../models/original/banner.model';
import {BannerService} from '../../../services/banner.service';
import {SUCCESS} from '../../../models/constants.model';

@Component({
    selector: 'add-banner',
    templateUrl: './add-banner.component.html',
    styleUrls: ['./add-banner.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddBannerComponent implements OnInit {

    submitting = false;

    banner: Banner;

    constructor(private router:Router,private bannerService: BannerService, public msgSrv: NzMessageService,
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
            this.bannerService.add(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/banner/list']);
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
