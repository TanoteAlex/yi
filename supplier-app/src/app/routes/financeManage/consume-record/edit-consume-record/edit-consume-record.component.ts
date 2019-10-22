import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {SUCCESS} from '../../../models/constants.model';
import {ConsumeRecordService} from '../../../services/consume-record.service';
import {ConsumeRecord} from '../../../models/original/consume-record.model';

@Component({
  selector: 'edit-consume-record',
  templateUrl: './edit-consume-record.component.html',
  styleUrls: ['./edit-consume-record.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditConsumeRecordComponent implements OnInit {

    submitting = false;

consumeRecord: ConsumeRecord;

    constructor(private route: ActivatedRoute,private router:Router,private consumeRecordService: ConsumeRecordService, public msgSrv:
        NzMessageService,private modalService: NzModalService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.consumeRecordService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.consumeRecord = response.data;
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
            formValue.obj.id = this.consumeRecord.id;
            this.consumeRecordService.update(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/consume-record/list']);
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
