import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {NzMessageService} from 'ng-zorro-antd';
import {_HttpClient} from '@delon/theme';
import {SUCCESS} from "../../../models/constants.model";
import {BannerService} from "../../../services/banner.service";
import {Banner} from "../../../models/original/banner.model";
import {Location} from "@angular/common";
import {Advertisement} from "../../../models/original/advertisement.model";

@Component({
  selector: 'view-banner',
  templateUrl: './view-advertisement.component.html',
  styleUrls: ['./view-advertisement.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewAdvertisementComponent implements OnInit {

/*banner: Banner=new Banner;*/
   advertisement: Advertisement=new Advertisement;
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
                this.advertisement = response.data;
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
