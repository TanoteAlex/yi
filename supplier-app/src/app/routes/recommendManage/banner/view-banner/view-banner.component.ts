import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {NzMessageService} from 'ng-zorro-antd';
import {_HttpClient} from '@delon/theme';
import {SUCCESS} from "../../../models/constants.model";
import {BannerService} from "../../../services/banner.service";
import {Banner} from "../../../models/original/banner.model";
import {Location} from "@angular/common";

@Component({
  selector: 'view-banner',
  templateUrl: './view-banner.component.html',
  styleUrls: ['./view-banner.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewBannerComponent implements OnInit {

banner: Banner=new Banner;

    constructor(private route: ActivatedRoute,private location: Location,private bannerService: BannerService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.bannerService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.banner = response.data;
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
