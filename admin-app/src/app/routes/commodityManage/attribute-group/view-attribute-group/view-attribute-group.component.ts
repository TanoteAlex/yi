

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { SUCCESS} from "../../../models/constants.model";
import { AttributeGroupService } from "../../../services/attribute-group.service";
import { AttributeGroup } from "../../../models/original/attribute-group.model";
import { Location } from "@angular/common";

@Component({
  selector: 'view-attribute-group',
  templateUrl: './view-attribute-group.component.html',
  styleUrls: ['./view-attribute-group.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewAttributeGroupComponent implements OnInit {

attributeGroup: AttributeGroup=new AttributeGroup;

    constructor(private route: ActivatedRoute,private location: Location,private attributeGroupService: AttributeGroupService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.attributeGroupService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.attributeGroup = response.data;
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
