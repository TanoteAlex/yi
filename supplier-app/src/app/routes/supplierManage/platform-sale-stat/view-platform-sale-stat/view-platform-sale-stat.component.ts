import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {NzMessageService} from 'ng-zorro-antd';
import {_HttpClient} from '@delon/theme';
import {SUCCESS} from "../../../models/constants.model";
import {PlatformSaleStatService} from "../../../services/platform-sale-stat.service";
import {PlatformSaleStat} from "../../../models/original/platform-sale-stat.model";
import {Location} from "@angular/common";

@Component({
  selector: 'view-platform-sale-stat',
  templateUrl: './view-platform-sale-stat.component.html',
  styleUrls: ['./view-platform-sale-stat.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewPlatformSaleStatComponent implements OnInit {

platformSaleStat: PlatformSaleStat=new PlatformSaleStat;

    constructor(private route: ActivatedRoute,private location: Location,private platformSaleStatService: PlatformSaleStatService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.platformSaleStatService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.platformSaleStat = response.data;
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
