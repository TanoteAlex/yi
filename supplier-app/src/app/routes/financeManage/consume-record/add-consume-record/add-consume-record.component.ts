import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ConsumeRecord} from '../../../models/original/consume-record.model';
import {ConsumeRecordService} from '../../../services/consume-record.service';
import {SUCCESS} from '../../../models/constants.model';

@Component({
    selector: 'add-consume-record',
    templateUrl: './add-consume-record.component.html',
    styleUrls: ['./add-consume-record.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddConsumeRecordComponent implements OnInit {

    submitting = false;

    consumeRecord: ConsumeRecord;

    constructor(private router:Router,private consumeRecordService: ConsumeRecordService, public msgSrv: NzMessageService,
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
            this.consumeRecordService.add(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/consume-record/list']);
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
