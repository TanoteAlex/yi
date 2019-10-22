import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {NzMessageService} from 'ng-zorro-antd';
import {_HttpClient} from '@delon/theme';
import {SUCCESS} from "../../../models/constants.model";
import {ExpressTemplateService} from "../../../services/express-template.service";
import {ExpressTemplate} from "../../../models/original/express-template.model";
import {Location} from "@angular/common";

@Component({
  selector: 'view-express-template',
  templateUrl: './view-express-template.component.html',
  styleUrls: ['./view-express-template.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewExpressTemplateComponent implements OnInit {

expressTemplate: ExpressTemplate=new ExpressTemplate;

    constructor(private route: ActivatedRoute,private location: Location,private expressTemplateService: ExpressTemplateService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.expressTemplateService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.expressTemplate = response.data;
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
