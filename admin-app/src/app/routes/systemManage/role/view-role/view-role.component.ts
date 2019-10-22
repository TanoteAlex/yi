import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {NzMessageService} from 'ng-zorro-antd';
import {_HttpClient} from '@delon/theme';
import {SUCCESS} from "../../../models/constants.model";
import {RoleService} from "../../../services/role.service";
import {Role} from "../../../models/original/role.model";
import {Location} from "@angular/common";

@Component({
  selector: 'view-role',
  templateUrl: './view-role.component.html',
  styleUrls: ['./view-role.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewRoleComponent implements OnInit {

role: Role=new Role;

    constructor(private route: ActivatedRoute,private location: Location,private roleService: RoleService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.roleService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.role = response.data;
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
