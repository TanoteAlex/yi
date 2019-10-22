import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {Member} from "../../../models/original/member.model";
import {MemberService} from '../../../services/member.service';
import {SUCCESS} from '../../../models/constants.model';


@Component({
  selector: 'add-member',
  templateUrl: './add-member.component.html',
  styleUrls: ['./add-member.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class AddMemberComponent implements OnInit {

  submitting = false;

  member: Member;

  constructor(private router: Router, private memberService: MemberService, public msgSrv: NzMessageService,
              private modalService: NzModalService) {
  }

  ngOnInit() {
  }

  onTransmitFormValueSubscription(event) {
    if (event) {
      this.confirmSub(event)
    }
  }

  confirmSub(formValue) {
    if (formValue) {
      this.submitting = true;
      const messageId = this.msgSrv.loading("正在提交...").messageId;
      this.memberService.add(formValue.obj).subscribe(response => {
        if (response.result == SUCCESS) {
          this.msgSrv.success("操作成功");
          this.router.navigate(['/dashboard/member/list']);
        } else {
          this.msgSrv.error('请求失败' + response.message);
        }
        this.msgSrv.remove(messageId);
        this.submitting = false;
      }, error => {
        this.msgSrv.error('请求错误' + error.message);
        this.msgSrv.remove(messageId);
        this.submitting = false;
      });
    }
  }

}
