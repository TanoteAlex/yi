import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {NzMessageService} from 'ng-zorro-antd';
import {_HttpClient} from '@delon/theme';
import {SUCCESS} from "../../../models/constants.model";
import {MemberService} from "../../../services/member.service";
import {Member} from "../../../models/original/member.model";
import {Location} from "@angular/common";

@Component({
  selector: 'view-member',
  templateUrl: './view-member.component.html',
  styleUrls: ['./view-member.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewMemberComponent implements OnInit {

    member: Member=new Member;
  switch:boolean=true;
    constructor(private route: ActivatedRoute,private location: Location,private memberService: MemberService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.memberService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.member = response.data;
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
