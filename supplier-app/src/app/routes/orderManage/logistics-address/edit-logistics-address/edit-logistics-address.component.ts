
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { SUCCESS } from '../../../models/constants.model';
import { LogisticsAddressService } from '../../../services/logistics-address.service';
import { LogisticsAddress } from '../../../models/original/logistics-address.model';

@Component({
  selector: 'edit-logistics-address',
  templateUrl: './edit-logistics-address.component.html',
  styleUrls: ['./edit-logistics-address.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditLogisticsAddressComponent implements OnInit {

    submitting = false;

logisticsAddress: LogisticsAddress;

    constructor(private route: ActivatedRoute,private router:Router,private logisticsAddressService: LogisticsAddressService, public msgSrv:
        NzMessageService,private modalService: NzModalService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.logisticsAddressService.getVoByIdForSupplier(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.logisticsAddress = response.data;
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
            formValue.obj.id = this.logisticsAddress.id;
            this.logisticsAddressService.updateForSupplier(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/logistics-address/list']);
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
