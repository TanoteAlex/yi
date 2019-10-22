import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { SUCCESS} from "../../../models/constants.model";
import { MessageService } from "../../../services/message.service";
import { Message } from "../../../models/original/message.model";
import { Location } from "@angular/common";

@Component({
    selector: 'view-message',
    templateUrl: './view-message.component.html',
    styleUrls: ['./view-message.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ViewMessageComponent implements OnInit {

    message: Message = new Message;

    constructor(private route: ActivatedRoute, private location: Location, private messageService: MessageService,
        public msgSrv: NzMessageService, public http: _HttpClient, public msg: NzMessageService, ) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId) {
        this.messageService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.message = response.data;
            } else {
                this.msg.error('请求失败', response.message);
            }
        }, error => {
            this.msg.error('请求错误', error.message);
        });
    }

    goBack() {
        this.location.back();
    }

}
