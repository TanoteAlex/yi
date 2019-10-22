import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {NzMessageService} from 'ng-zorro-antd';
import {_HttpClient} from '@delon/theme';
import {SUCCESS} from "../../../models/constants.model";
import {UserService} from "../../../services/user.service";
import {User} from "../../../models/original/user.model";
import {Location} from "@angular/common";

@Component({
  selector: 'view-user',
  templateUrl: './view-user.component.html',
  styleUrls: ['./view-user.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewUserComponent implements OnInit {

user: User=new User;

    constructor(private route: ActivatedRoute,private location: Location,private userService: UserService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.userService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.user = response.data;
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
