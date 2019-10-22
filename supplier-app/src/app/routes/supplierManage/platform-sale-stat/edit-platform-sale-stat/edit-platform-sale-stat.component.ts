import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {SUCCESS} from '../../../models/constants.model';
import {PlatformSaleStatService} from '../../../services/platform-sale-stat.service';
import {PlatformSaleStat} from '../../../models/original/platform-sale-stat.model';

@Component({
  selector: 'edit-platform-sale-stat',
  templateUrl: './edit-platform-sale-stat.component.html',
  styleUrls: ['./edit-platform-sale-stat.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditPlatformSaleStatComponent implements OnInit {

    submitting = false;

platformSaleStat: PlatformSaleStat;

    constructor(private route: ActivatedRoute,private router:Router,private platformSaleStatService: PlatformSaleStatService, public msgSrv:
        NzMessageService,private modalService: NzModalService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.platformSaleStatService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.platformSaleStat = response.data;
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
            formValue.obj.id = this.platformSaleStat.id;
            this.platformSaleStatService.update(formValue.obj).subscribe(response => {
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
