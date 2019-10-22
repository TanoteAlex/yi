import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {NzMessageService} from 'ng-zorro-antd';
import {_HttpClient} from '@delon/theme';
import {SUCCESS} from "../../../models/constants.model";
import {ReturnReasonService} from "../../../services/return-reason.service";
import {ReturnReason} from "../../../models/original/return-reason.model";
import {Location} from "@angular/common";

@Component({
  selector: 'view-return-reason',
  templateUrl: './view-return-reason.component.html',
  styleUrls: ['./view-return-reason.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewReturnReasonComponent implements OnInit {

returnReason: ReturnReason=new ReturnReason;

    constructor(private route: ActivatedRoute,private location: Location,private returnReasonService: ReturnReasonService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.returnReasonService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.returnReason = response.data;
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
