import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewChild, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Community} from '../../../models/original/community.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';
import {MemberService} from "../../../services/member.service";
import {EditorComponent} from "../../../components/editor/editor.component";
import {PageQuery} from "../../../models/page-query.model";
import {ModalSelecetComponent} from "../../../components/modal-selecet/modal-selecet.component";

@Component({
  selector: 'form-community',
  templateUrl: './form-community.component.html',
  styleUrls: ['./form-community.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormCommunityComponent implements OnInit, OnChanges {

  commonForm: FormGroup;

  @Input() title: string = '表单';

  @ViewChild('editor') editor: EditorComponent;
  @ViewChild('memberModalSelect') memberModalSelect: ModalSelecetComponent;

  @Input() community: Community = new Community();
  memberPageQuery: PageQuery = new PageQuery()

  formatterCommissionRate = value => `${value} %`;
  parserCommissionRate = value => value.replace(' %', '');

  @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);
  position;

  formErrors = {

    member: [
      {
        name: 'required',
        msg: '不可为空',
      },
    ],
    province: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大16位长度',
      }
    ],
    city: [
      {
        name: 'maxlength',
        msg: '最大16位长度',
      }
    ],
    district: [
      {
        name: 'maxlength',
        msg: '最大16位长度',
      }
    ],
    address: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大32位长度',
      }
    ],
    state: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大3位长度',
      }
    ],
    commissionRate: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大5位长度',
      }
    ],
    description: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大65535位长度',
      }
    ],
    imgPath: [
      {
        name: 'required',
        msg: '不可为空',
      },{
        name: 'imgPathFailure',
        msg: '图片上传失败,请更换图片',
      }
    ],
    receivingAddress: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大128位长度',
      }
    ],
  };

  constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService, public memberService: MemberService) {
    this.buildForm();
  }

  ngOnChanges(value) {
    if (value.community !== undefined && value.community.currentValue !== undefined) {
      this.setBuildFormValue(this.community);
    }
  }

  ngOnInit() {
    //小区管理员模态框查询(类型:普通会员 状态:启用)
    this.memberPageQuery.addOnlyFilter("memberType", 0, "eq");
    this.memberPageQuery.addOnlyFilter("state", 0, "eq");
  }

  setMember(event) {
    if (event.username && event.length != 0) {
      this.commonForm.patchValue({
        member: {
          id: event.id,
          username: event.username,
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

  getPic(event) {
    this.commonForm.patchValue({
      imgPath: event.length != 0 ? (event[0].response.data ? event[0].response.data[0].url : null) : null
    })
  }

  buildForm(): void {
    this.commonForm = this.fb.group({
      member: [null, Validators.compose([Validators.required])],
      province: [null, Validators.compose([Validators.required, Validators.maxLength(16)])],
      city: [],
      district: [],
      address: [null, Validators.compose([Validators.required, Validators.maxLength(32)])],
      state: [0, Validators.compose([Validators.required, Validators.min(0), Validators.max(1)])],
      commissionRate: [0, Validators.compose([Validators.required, Validators.maxLength(5)])],
      description: [null, Validators.compose([Validators.required, Validators.maxLength(65535)])],
      imgPath: [null, Validators.compose([Validators.required])],
      receivingAddress: [null, Validators.compose([Validators.required, Validators.maxLength(128)])],
    });
  }

  thematicText(event) {
    if (event == " ") {
      this.commonForm.patchValue({
        description: null
      });
    } else {
      this.commonForm.patchValue({
        description: event
      });
    }
  }

  setBuildFormValue(community: Community) {
    this.commonForm.setValue({
      member: {id: community.member.id, username: community.member.username,},
      province: community.province,
      city: community.city,
      district: community.district,
      address: community.address,
      state: community.state,
      commissionRate: community.commissionRate,
      description: community.description,
      imgPath: community.imgPath,
      receivingAddress: community.receivingAddress,
    });
    //设置会员
    this.memberModalSelect.dataRetrieval(community.member)
    //设置富文本内容
    this.editor.setContent(this.commonForm.value.description);
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
    if (this.community) {
    }
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

}
