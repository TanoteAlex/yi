
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { SUCCESS } from '../../../models/constants.model';
import { SalesAreaService } from '../../../services/sales-area.service';
import { SalesAreaBo } from '../../../models/domain/bo/sales-area-bo.model';

@Component({
  selector: 'edit-sales-area',
  templateUrl: './edit-sales-area.component.html',
  styleUrls: ['./edit-sales-area.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditSalesAreaComponent implements OnInit {

    submitting = false;

    salesArea: SalesAreaBo;

    constructor(private route: ActivatedRoute,private router:Router,private salesAreaService: SalesAreaService, public msgSrv:
        NzMessageService,private modalService: NzModalService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.salesAreaService.getBoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.salesArea = response.data;
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
            formValue.obj.id = this.salesArea.id;
            this.salesAreaService.update(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/sales-area/list']);
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
