import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {NzMessageService} from 'ng-zorro-antd';
import {_HttpClient} from '@delon/theme';
import {SUCCESS} from "../../../models/constants.model";
import {ConsumeRecordService} from "../../../services/consume-record.service";
import {ConsumeRecord} from "../../../models/original/consume-record.model";
import {Location} from "@angular/common";

@Component({
  selector: 'view-consume-record',
  templateUrl: './view-consume-record.component.html',
  styleUrls: ['./view-consume-record.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewConsumeRecordComponent implements OnInit {

consumeRecord: ConsumeRecord=new ConsumeRecord;

    constructor(private route: ActivatedRoute,private location: Location,private consumeRecordService: ConsumeRecordService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

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
                this.msg.error('请求失败', response.message);
            }
        }, error => {
            this.msg.error('请求错误', error.message);
        });
    }

    goBack(){
        this.location.back();
    }

}
