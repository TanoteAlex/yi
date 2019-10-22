import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {NzMessageService} from 'ng-zorro-antd';
import {_HttpClient} from '@delon/theme';
import {SUCCESS} from "../../../models/constants.model";
import {DeptService} from "../../../services/dept.service";
import {Dept} from "../../../models/original/dept.model";
import {Location} from "@angular/common";

@Component({
  selector: 'view-dept',
  templateUrl: './view-dept.component.html',
  styleUrls: ['./view-dept.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewDeptComponent implements OnInit {

dept: Dept=new Dept;

    constructor(private route: ActivatedRoute,private location: Location,private deptService: DeptService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.deptService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.dept = response.data;
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
