import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {NzMessageService} from 'ng-zorro-antd';
import {_HttpClient} from '@delon/theme';
import {SUCCESS} from "../../../models/constants.model";
import {MemberLevelService} from "../../../services/member-level.service";
import {MemberLevel} from "../../../models/original/member-level.model";
import {Location} from "@angular/common";

@Component({
  selector: 'view-member-level',
  templateUrl: './view-member-level.component.html',
  encapsulation: ViewEncapsulation.None
})
export class ViewMemberLevelComponent implements OnInit {

memberLevel: MemberLevel=new MemberLevel;

    constructor(private route: ActivatedRoute,private location: Location,private memberLevelService: MemberLevelService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.memberLevelService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.memberLevel = response.data;
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
