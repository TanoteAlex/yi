import {Component, EventEmitter, Input, Output} from '@angular/core';
import {MemberService} from "../../services/member.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {NzMessageService} from "ng-zorro-antd";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'nz-modal-customer',
  template: `
    <a style="color: #0090ff" *ngIf="showButton" (click)="showModal()">{{buttonName}}
      <ng-content></ng-content>
    </a>
    <nz-modal *ngIf="showButton" [(nzVisible)]="isVisible" [nzTitle]="title" (nzOnCancel)="handleCancel()"
              (nzOnOk)="handleOk()">
      <p>{{content}}</p>
    </nz-modal>
    <button nz-button nzType="primary" type="submit" *ngIf="!showButton" (click)="showModal()">{{buttonName}}

    </button>
    <nz-modal *ngIf="!showButton" [(nzVisible)]="isVisible" [nzTitle]="title" (nzOnCancel)="handleCancel()"
              (nzOnOk)="handleOk()">
      <p>{{content}}</p>
    </nz-modal>
    <nz-modal *ngIf="couponGrant" [(nzVisible)]="isVisible" [nzTitle]="title"
              (nzOnCancel)="handleCancel()"
              (nzOnOk)="handleOk()">
      <p>{{content}}</p>
      <app-modal-selecet
        [filters]="[{filterName:'nickname',name:'会员昵称',value:''}]"
        [showCol]="[{name:'username',value:'账号',isShow:false},{name:'nickname',value:'会员昵称',isShow:true}]"
        [select]="commonForm.value.members"
        [resultName]="commonForm.value.members ? commonForm.value.members.nickname : '请选择'"
        [baseService]="memberService"
        [isMulti]="true"
        (result)="setMember($event)">
      </app-modal-selecet>
    </nz-modal>
    <nz-modal *ngIf="auditing" [(nzVisible)]="isVisible" [nzTitle]="title"
              (nzOnCancel)="handleCancel()"
              (nzOnOk)="handleOk()">
      <p>{{content}}</p>
      <app-modal-selecet
        [filters]="[{filterName:'fullName',name:'姓名',value:''}]"
        [showCol]="[{name:'jobNo',value:'工号',isShow:false},{name:'fullName',value:'姓名',isShow:true}]"
        [select]="commonForm.value.auditUser"
        [resultName]="commonForm.value.auditUser ? commonForm.value.auditUser.fullName : '请选择'"
        [baseService]="userService"
        (result)="setUser($event)">
      </app-modal-selecet>
    </nz-modal>
  `,
  styles: [`
    ::ng-deep .ant-modal-mask {
      background-color: rgba(0, 0, 0, 0.38) !important;
    }
  `]
})
export class ModalComponent {
  isVisible = false;
  @Input()
  title = ''
  @Input()
  content = ''
  @Input()
  buttonName = 'Show Modal'
  @Input()
  showButton = true

  @Input()
  couponGrant = false

  @Input()
  auditing = false

  commonForm: FormGroup;

  @Output()
  ok: EventEmitter<any> = new EventEmitter();
  @Output()
  result = new EventEmitter

  constructor(private fb: FormBuilder, public memberService: MemberService, public msgSrv: NzMessageService, public userService: UserService) {
    this.buildForm()
  }

  buildForm(): void {
    this.commonForm = this.fb.group({
      members: [null],
      auditUser: [null],
    });
  }

  showModal(): void {
    this.isVisible = true;
  }

  handleOk(): void {
    if (this.couponGrant) {
      if (!this.commonForm.value.members || this.commonForm.value.members.length == 0) {
        this.msgSrv.warning('请选择发放用户！');
        return;
      }
    }
    if (this.auditing) {
      if (!this.commonForm.value.auditUser || this.commonForm.value.auditUser.length == 0) {
        this.msgSrv.warning('请选择审核人！');
        return;
      }
    }
    this.ok.emit('ok');
    if (this.couponGrant) {
      this.result.emit(this.commonForm.value.members);
    }
    if (this.auditing) {
      this.result.emit(this.commonForm.value.auditUser);
    }
    this.isVisible = false;
  }

  handleCancel(): void {
    this.isVisible = false;
  }

  setMember(event) {
    this.commonForm.patchValue({
      members: event.map(e => {
        return {id: e.id, nickname: e.nickname}
      })
    })
  }

  setUser(event) {
    if (event.fullName && event.length != 0) {
      this.commonForm.patchValue({
        auditUser: event
      });
    }
  }

}
