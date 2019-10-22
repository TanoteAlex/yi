import { Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewChild, ViewEncapsulation } from '@angular/core';
import { Location } from '@angular/common';
import { NzMessageService } from 'ng-zorro-antd';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { InviteActivityBo } from '../../../models/domain/bo/invite-activity-bo.model';
import { ObjectUtils } from '@shared/utils/ObjectUtils';
import { EditorComponent } from '../../../components/editor/editor.component';

@Component({
  selector: 'form-invite-activity',
  templateUrl: './form-invite-activity.component.html',
  styleUrls: ['./form-invite-activity.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class FormInviteActivityComponent implements OnInit, OnChanges {

  commonForm: FormGroup;

  @Input() title: string = '表单';

  @Input() inviteActivity: InviteActivityBo = new InviteActivityBo();

  @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

  @ViewChild('editor') editor: EditorComponent;

  dateFormat = 'yyyy/MM/dd';

  formErrors = {
    title: [
      {
        name: 'required',
        msg: '标题不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大32位长度',
      },
    ],
    banner: [
      {
        name: 'required',
        msg: '请上传图片',
      },
    ],
    rule: [
      {
        name: 'maxlength',
        msg: '最大65535位长度',
      },
    ],

    timeSlot: [
      {
        name: 'required',
        msg: '请选择时间范围',
      },
    ],
    inviteFlag: [
      {
        name: 'required',
        msg: '不可为空',
      },
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


  constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService) {
    this.buildForm();
  }

  ngOnChanges(value) {
    if (value.inviteActivity !== undefined && value.inviteActivity.currentValue !== undefined) {
      this.setBuildFormValue(this.inviteActivity);
    }
  }

  ngOnInit() {

  }

  getPic(event) {
    if (event == 'failure') {
      this.commonForm.patchValue({ banner: null });
    } else {
      this.commonForm.patchValue({ banner: event.data[0].url });
    }
  }

  buildForm(): void {
    this.commonForm = this.fb.group({
      title: [null, Validators.compose([Validators.required,
        Validators.maxLength(32),
      ])],
      banner: [null, Validators.required],
      rule: [null, Validators.required],
      timeSlot: [null, Validators.required],
      startTime: [null, Validators.required],
      endTime: [null, Validators.required],
      inviteFlag: [
        1, Validators.compose([Validators.required,
        ]),
      ],
      state: [
        0, Validators.compose([Validators.required,
        ]),
      ],
    });
  }

  setBuildFormValue(inviteActivity: InviteActivityBo) {
    this.commonForm.setValue({
      title:
      inviteActivity.title
      ,
      banner:
      inviteActivity.banner
      ,
      rule:
      inviteActivity.rule
      ,
      timeSlot: [inviteActivity.startTime, inviteActivity.endTime]
      ,
      startTime: inviteActivity.startTime
      ,
      endTime: inviteActivity.endTime
      ,
      inviteFlag:
      inviteActivity.inviteFlag
      ,
      state:
      inviteActivity.state
      ,
    });
    this.editor.setContent(this.commonForm.value.rule);
  }

  submitCheck(): any {
    const commonFormValid = ObjectUtils.checkValidated(this.commonForm);
    if (commonFormValid) {
      return this.commonForm.value;
    }
    return null;
  }

  thematicText(event) {
    this.commonForm.patchValue({
      rule: event,
    });
  }

  onSubmit() {
    const formValue = this.submitCheck();
    if (this.inviteActivity) {
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

  changeRange(dates: Date[]) {
    if (dates != null && dates.length != 0) {
      this.commonForm.patchValue({
        startTime: dates[0].toLocaleDateString().replace(/\//g, '-') + ' 00:00:00',
        endTime: dates[1].toLocaleDateString().replace(/\//g, '-') + ' 23:59:59',
      });
    } else {
      this.commonForm.patchValue({
        startTime: null,
        endTime: null,
      });
    }
  }

}
