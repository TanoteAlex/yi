
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { SUCCESS } from '../../../models/constants.model';
import { AreaColumnService } from '../../../services/area-column.service';
import { AreaColumnBo } from '../../../models/domain/bo/area-column-bo.model';

@Component({
  selector: 'edit-area-column',
  templateUrl: './edit-area-column.component.html',
  styleUrls: ['./edit-area-column.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditAreaColumnComponent implements OnInit {

    submitting = false;

    areaColumn: AreaColumnBo;

    constructor(private route: ActivatedRoute,private router:Router,private areaColumnService: AreaColumnService, public msgSrv:
        NzMessageService,private modalService: NzModalService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.areaColumnService.getBoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.areaColumn = response.data;
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
            formValue.obj.id = this.areaColumn.id;
            this.areaColumnService.update(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/area-column/list']);
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
