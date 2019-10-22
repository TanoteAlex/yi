import { Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewChild, ViewEncapsulation } from '@angular/core';
import { Location } from '@angular/common';
import { NzMessageService } from 'ng-zorro-antd';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { InviteRewardBo } from '../../../models/domain/bo/invite-reward-bo.model';
import { ObjectUtils } from '@shared/utils/ObjectUtils';
import { InviteActivityService } from '../../../services/invite-activity.service';
import { ModalSelecetComponent } from '../../../components/modal-selecet/modal-selecet.component';
import { RewardService } from '../../../services/reward.service';

@Component({
  selector: 'form-invite-reward',
  templateUrl: './form-invite-reward.component.html',
  styleUrls: ['./form-invite-reward.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class FormInviteRewardComponent implements OnInit, OnChanges {

  commonForm: FormGroup;

  @Input() title: string = '表单';

  @Input() inviteReward: InviteRewardBo = new InviteRewardBo();

  @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

  @ViewChild('inviteActivityModalSelect') inviteActivityModalSelect: ModalSelecetComponent;

  @ViewChild('rewardModalSelect') rewardModalSelect: ModalSelecetComponent;

  formErrors = {
    inviteActivity: [
      {
        name: 'required',
        msg: '不可为空',
      },
    ],
    reward: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大10位长度',
      },
    ],
    sort: [
      {
        name: 'maxlength',
        msg: '最大0位长度',
      },
    ],
    state: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大0位长度',
      },
    ],
  };

  constructor(private fb: FormBuilder, private location: Location, public inviteActivityService: InviteActivityService,
              public rewardService: RewardService, public msgSrv: NzMessageService) {
    this.buildForm();
  }

  ngOnChanges(value) {
    if (value.inviteReward !== undefined && value.inviteReward.currentValue !== undefined) {
      this.setBuildFormValue(this.inviteReward);
    }
  }

  ngOnInit() {

  }

  buildForm(): void {
    this.commonForm = this.fb.group({
      inviteActivity: [
        null, Validators.compose([Validators.required,
        ]),
      ],
      reward: [
        null, Validators.compose([Validators.required,
        ]),
      ],
      sort: [0,Validators.required],
      state: [
        0, Validators.compose([Validators.required,
        ]),
      ],
    });
  }

  setBuildFormValue(inviteReward: InviteRewardBo) {
    this.commonForm.setValue({
      inviteActivity:
      inviteReward.inviteActivity
      ,
      reward:
      inviteReward.reward
      ,
      sort:
      inviteReward.sort
      ,
      state:
      inviteReward.state
      ,
    });

    console.log("inviteReward="+JSON.stringify(inviteReward))
    this.inviteActivityModalSelect.dataRetrieval(inviteReward.inviteActivity);
    this.rewardModalSelect.dataRetrieval(inviteReward.reward);
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
    if (this.inviteReward) {
    }
    if (!formValue) {
      this.msgSrv.warning('校验尚未通过无法提交，请确认输入项！');
      return;
    }
    console.log('commonForm value=' + JSON.stringify(formValue));
    this.onTransmitFormValue.emit({ obj: formValue });
  }

  reset() {

  }

  goBack() {
    this.location.back();
  }

  setInviteActivity(event) {
    if (event.title != null && event.length != 0) {
      this.commonForm.patchValue({
        inviteActivity: event,
      });
    }
  }

  setRewardService(event) {
    if (event.name != null && event.length != 0) {
      this.commonForm.patchValue({
        reward: event,
      });
    }
  }
}
