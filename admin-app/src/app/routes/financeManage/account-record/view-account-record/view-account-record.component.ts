import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {NzMessageService} from 'ng-zorro-antd';
import {_HttpClient} from '@delon/theme';
import {SUCCESS} from "../../../models/constants.model";
import {AccountRecordService} from "../../../services/account-record.service";
import {AccountRecord} from "../../../models/original/account-record.model";
import {Location} from "@angular/common";

@Component({
  selector: 'view-account-record',
  templateUrl: './view-account-record.component.html',
  styleUrls: ['./view-account-record.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewAccountRecordComponent implements OnInit {

  accountRecord: AccountRecord = new AccountRecord;

  constructor(private route: ActivatedRoute, private location: Location, private accountRecordService: AccountRecordService,
              public msgSrv: NzMessageService, public http: _HttpClient, public msg: NzMessageService,) {
  }

  ngOnInit() {
    this.route.params.subscribe((params: ParamMap) => {
      this.getById(params["objId"]);
    });
  }

  getById(objId) {
    this.accountRecordService.getVoById(objId).subscribe(response => {
      if (response.result == SUCCESS) {
        this.accountRecord = response.data;
      } else {
        this.msg.error('请求失败', response.message);
      }
    }, error => {
      this.msg.error('请求错误', error.message);
    });
  }

  goBack() {
    this.location.back();
  }

}
