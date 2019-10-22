import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {NzMessageService} from 'ng-zorro-antd';
import {_HttpClient} from '@delon/theme';
import {SUCCESS} from "../../../models/constants.model";
import {FreightTemplateService} from "../../../services/freight-template.service";
import {FreightTemplate} from "../../../models/original/freight-template.model";
import {Location} from "@angular/common";

@Component({
  selector: 'view-freight-template',
  templateUrl: './view-freight-template.component.html',
  styleUrls: ['./view-freight-template.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewFreightTemplateComponent implements OnInit {

freightTemplate: FreightTemplate=new FreightTemplate;

    constructor(private route: ActivatedRoute,private location: Location,private freightTemplateService: FreightTemplateService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.freightTemplateService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.freightTemplate = response.data;
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
