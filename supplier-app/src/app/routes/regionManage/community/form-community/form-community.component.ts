import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Community} from '../../../models/original/community.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';
import {MemberService} from "../../../services/member.service";

@Component({
  selector: 'form-community',
  templateUrl: './form-community.component.html',
  styleUrls: ['./form-community.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormCommunityComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() title: string = '表单';

    @Input() community: Community =new Community();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

    formErrors = {

        member:[
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        province:[
            {
                name: 'maxlength',
                msg: '最大16位长度',
            }
        ],
        city:[
            {
                name: 'maxlength',
                msg: '最大16位长度',
            }
        ],
        district:[
            {
                name: 'maxlength',
                msg: '最大16位长度',
            }
        ],
        address:[
          {
            name: 'required',
            msg: '不可为空',
          },
            {
                name: 'maxlength',
                msg: '最大32位长度',
            }
        ],
        state:[
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大3位长度',
            }
        ],
        commissionRate:[
          {
            name: 'required',
            msg: '不可为空',
          },
            {
                name: 'maxlength',
                msg: '最大5位长度',
            }
        ],
        description:[
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大256位长度',
            }
        ],
        imgPath:[
            {
                name: 'required',
                msg: '不可为空',
            }
        ],
        receivingAddress:[
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

    constructor(private fb: FormBuilder,private location: Location, public msgSrv: NzMessageService,public memberService:MemberService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.community !== undefined && value.community.currentValue !== undefined) {
            this.setBuildFormValue(this.community);
        }
    }

    ngOnInit() {

    }

  position=[];
  setProvince(event){
    this.commonForm.patchValue({
      province: event[0],
      city: event[1],
      district: event[2],
    })
  }

  imgPathPic="";

  getPic(event){
    this.imgPathPic=event.data[0].url;
  }

    buildForm(): void {
        this.commonForm = this.fb.group({

            member: [{
              id:0,
              username:"请选择"
            }
            ],
            province: [
            ],
            city: [
            ],
            district: [
            ],
            address: [
              null, Validators.compose([Validators.required,
                Validators.maxLength(32)
              ])
            ],
            state: [
              0, Validators.compose([Validators.required,
                Validators.min(0), Validators.max(1)
              ])
            ],
            commissionRate: [
              null, Validators.compose([Validators.required,
                Validators.maxLength(5)
              ])
            ],
            description: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(256)
                ])
            ],
            imgPath: [],
            receivingAddress: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(128)
                ])
            ],
        });
    }

    setBuildFormValue(community: Community) {
        this.commonForm.setValue({
            member:{
              id:community.member.id,
              username:community.member.username,
            }
                ,
            province:
            community.province
                ,
            city:
            community.city
                ,
            district:
            community.district
                ,
            address:
            community.address
                ,
            state:
            community.state
                ,
            commissionRate:
            community.commissionRate
                ,
            description:
            community.description
                ,
            imgPath:
            community.imgPath
                ,
            receivingAddress:
            community.receivingAddress
                ,
        });
        if(this.commonForm.value.province!=null) {
          this.position[0] = this.commonForm.value.province;
        }
        if(this.commonForm.value.city!=null) {
          this.position[1] = this.commonForm.value.city;
        }
        if(this.commonForm.value.district!=null) {
          this.position[2]= this.commonForm.value.district;
        }
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
      if(this.imgPathPic!=""){
        formValue.imgPath=this.imgPathPic;
      }
 if (this.community) {
        }
        if (!formValue) {
            this.msgSrv.warning('校验尚未通过无法提交，请确认输入项！');
            return;
        }
      if(this.commonForm.value.member.username=="请选择" || this.commonForm.value.member.id==0){
        this.msgSrv.warning('请选择管理员！');
        return;
      }
      if(this.commonForm.value.province==null){
        this.msgSrv.warning('请选择地区！');
        return;
      }
      if(!this.commonForm.value.imgPath){
        this.msgSrv.warning('请添加商品图片！');
        return;
      }
        this.onTransmitFormValue.emit({obj: formValue});
    }

    reset() {

    }

    goBack(){
        this.location.back();
    }


      setMember(event) {
        this.commonForm.patchValue({
          member: {
            id: event.id,
            username: event.username,
          }
        });
      }

}
