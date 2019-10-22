

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { SUCCESS} from "../../../models/constants.model";
import { OperateCategoryService } from "../../../services/operate-category.service";
import { OperateCategory } from "../../../models/original/operate-category.model";
import { Location } from "@angular/common";

@Component({
  selector: 'view-operate-category',
  templateUrl: './view-operate-category.component.html',
  styleUrls: ['./view-operate-category.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewOperateCategoryComponent implements OnInit {

operateCategory: OperateCategory=new OperateCategory;

    constructor(private route: ActivatedRoute,private location: Location,private operateCategoryService: OperateCategoryService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.operateCategoryService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.operateCategory = response.data;
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
