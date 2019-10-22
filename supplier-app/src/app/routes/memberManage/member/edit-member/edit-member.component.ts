import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {SUCCESS} from '../../../models/constants.model';
import {MemberService} from '../../../services/member.service';
import {Member} from '../../../models/original/member.model';

@Component({
  selector: 'edit-member',
  templateUrl: './edit-member.component.html',
  styleUrls: ['./edit-member.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditMemberComponent implements OnInit {

  submitting = false;

  member: Member;

  constructor(private route: ActivatedRoute, private router: Router, private memberService: MemberService, public msgSrv:
    NzMessageService, private modalService: NzModalService) {
  }

  ngOnInit() {
    this.route.params.subscribe((params: ParamMap) => {
      this.getById(params["objId"]);
    });
  }

  getById(objId) {
    this.memberService.getVoById(objId).subscribe(response => {
      if (response.result == SUCCESS) {
        this.member = response.data;
      } else {
        this.msgSrv.error('请求失败', response.message);
      }
    }, error => {
      this.msgSrv.error('请求错误', error.message);
    });
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
      formValue.obj.id = this.member.id;
      this.memberService.update(formValue.obj).subscribe(response => {
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
