import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {SUCCESS} from '../../../models/constants.model';
import {BannerService} from '../../../services/banner.service';
import {Banner} from '../../../models/original/banner.model';
import {Advertisement} from "../../../models/original/advertisement.model";
import {AdvertisementService} from "../../../services/advertisement.service";

@Component({
    selector: 'edit-banner',
    templateUrl: './edit-advertisement.component.html',
    styleUrls: ['./edit-advertisement.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class EditAdvertisementComponent implements OnInit {

    submitting = false;

    advertisement: Advertisement;
    
    constructor(private route: ActivatedRoute, private router: Router, private advertisementService: AdvertisementService, public msgSrv:
        NzMessageService, private modalService: NzModalService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId) {
        this.advertisementService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.advertisement = response.data;
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

    confirmSub(formValue) {
        if (formValue) {
            this.submitting = true;
            const messageId = this.msgSrv.loading("正在提交...").messageId;
            formValue.obj.id = this.advertisement.id;
            this.advertisementService.update(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/advertisement/list']);
                } else {
                    this.msgSrv.error('请求失败' + response.message);
                }
                this.msgSrv.remove(messageId);
                this.submitting = false;
            }, error => {
                this.msgSrv.error('请求错误' + error.message);
                this.msgSrv.remove(messageId);
                this.submitting = false;
            });
        }
    }
}
