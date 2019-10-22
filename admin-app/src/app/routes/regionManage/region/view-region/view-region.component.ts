import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {NzMessageService} from 'ng-zorro-antd';
import {_HttpClient} from '@delon/theme';
import {SUCCESS} from "../../../models/constants.model";
import {RegionService} from "../../../services/region.service";
import {Region} from "../../../models/original/region.model";
import {Location} from "@angular/common";

@Component({
  selector: 'view-region',
  templateUrl: './view-region.component.html',
  styleUrls: ['./view-region.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewRegionComponent implements OnInit {

region: Region=new Region;

    constructor(private route: ActivatedRoute,private location: Location,private regionService: RegionService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.regionService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.region = response.data;
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
