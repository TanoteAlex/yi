import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewChild, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Member} from '../../../models/original/member.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';
import {MemberLevelService} from "../../../services/member-level.service";
import {CommunityService} from "../../../services/community.service";
import {PageQuery} from "../../../models/page-query.model";
import {ModalSelecetComponent} from "../../../components/modal-selecet/modal-selecet.component";
import { MemberService } from '../../../services/member.service';

@Component({
  selector: 'form-member',
  templateUrl: './form-member.component.html',
  styleUrls: ['./form-member.component.scss'],
  encapsulation: ViewEncapsulation.None,
  providers:[MemberService]
})
export class FormMemberComponent implements OnInit, OnChanges {

  commonForm: FormGroup;

  @Input() title: string = '表单';

  @Input() member: Member = new Member();

  @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

  communityPageQuery: PageQuery = new PageQuery();

  @ViewChild('parentMemberModalSelect') parentMemberModalSelect: ModalSelecetComponent;
  @ViewChild('memberLevelModalSelect') memberLevelModalSelect: ModalSelecetComponent;
  @ViewChild('communityModalSelect') communityModalSelect: ModalSelecetComponent;

  position;

  formErrors = {


    username: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大16位长度',
      }
    ],
    nickname: [
      {
        name: 'maxlength',
        msg: '最大32位长度',
      }
    ],
    memberLevel: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大10位长度',
      }
    ],
    memberType: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大3位长度',
      }
    ],

  };

  constructor(private fb: FormBuilder, public memberLevelService: MemberLevelService, private location: Location,
              public memberService: MemberService,public msgSrv: NzMessageService,public communityService:CommunityService) {
    this.buildForm();
  }

  ngOnChanges(value) {
    if (value.member !== undefined && value.member.currentValue !== undefined) {
      this.setBuildFormValue(this.member);
    }
  }

  ngOnInit() {
    this.communityPageQuery.addOnlyOneFilter("state",0,"eq");
  }

  setMemberLevel(event) {
    if(event.name){
      this.commonForm.patchValue({
        memberLevel: {
          id: event.id,
          name: event.name
        }
      });
    }
  }

  setProvince(event) {
    this.commonForm.patchValue({
      province: event[0],
      city: event[1],
      district: event[2],
    })
  }

  buildForm(): void {
    this.commonForm = this.fb.group({
      memberLevel: [null, Validators.compose([Validators.required])],
      parent: [null],
      province: [null],
      city: [null],
      district: [null],
      address: [null],
      community: [null],
    });
  }

  setBuildFormValue(member: Member) {
    this.commonForm.setValue({
      memberLevel: {
        id: member.memberLevel.id,
        name: member.memberLevel.name
      },
      parent:member.parent,
      province: member.province,
      city: member.city,
      district: member.district,
      address: member.address,
      community : member.community
    });
    let position = []
    if (this.commonForm.value.province != null) {
      position.push(this.commonForm.value.province);
    }
    if (this.commonForm.value.city != null) {
      position.push(this.commonForm.value.city);
    }
    if (this.commonForm.value.district != null) {
      position.push(this.commonForm.value.district);
    }
    this.position = position;
    this.parentMemberModalSelect.dataRetrieval(member.parent);
    //设置会员等级
    this.memberLevelModalSelect.dataRetrieval(member.memberLevel);
    //设置小区
    this.communityModalSelect.dataRetrieval(member.community);
  }

  submitCheck(): any {
    const commonFormValid = ObjectUtils.checkValidated(this.commonForm);
    if (commonFormValid) {
      return this.commonForm.value;
    }
    return null;
  }


  onSubmit() {
    const formValue = this.submitCheck();
    if (!formValue) {
      this.msgSrv.warning('校验尚未通过无法提交，请确认输入项！');
      return;
    }
    this.onTransmitFormValue.emit({obj: formValue});
  }

  reset() {

  }

  goBack() {
    this.location.back();
  }

  setCommunity(event) {
    if (event.address && event.length != 0) {
      this.commonForm.patchValue({
        community: {
          id: event.id,
          address: event.address,
        }
      });
    }
  }

  setMember(event) {
    if (event.username && event.length != 0) {
      if(event.id != this.member.id){
        this.commonForm.patchValue({
          parent: {
            id: event.id,
            username: event.username,
          }
        });
      }else{
        this.commonForm.patchValue({
          parent: null
        });
      }
    }
  }

}
