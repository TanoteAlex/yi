import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {Banner} from '../../../models/original/banner.model';
import {AdvertisementService} from '../../../services/advertisement.service';
import {SUCCESS} from '../../../models/constants.model';
import {Advertisement} from "../../../models/original/advertisement.model";

@Component({
    selector: 'add-banner',
    templateUrl: './add-advertisement.component.html',
    styleUrls: ['./add-advertisement.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddAdvertisementComponent implements OnInit {

    submitting = false;

  advertisement: Advertisement;

    constructor(private router:Router,private advertisementService: AdvertisementService, public msgSrv: NzMessageService,
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
            this.advertisementService.add(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/advertisement/list']);
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
