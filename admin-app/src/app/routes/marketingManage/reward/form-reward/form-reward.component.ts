import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewChild, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {RewardBo} from '../../../models/domain/bo/reward-bo.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';
import {PrizeService} from "../../../services/prize.service";
import {ModalSelecetComponent} from "../../../components/modal-selecet/modal-selecet.component";
import {EditorComponent} from "../../../components/editor/editor.component";

@Component({
    selector: 'form-reward',
    templateUrl: './form-reward.component.html',
    styleUrls: ['./form-reward.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class FormRewardComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() title: string = '表单';

    @Input() reward: RewardBo = new RewardBo();
    @ViewChild('modalSelect') modalSelect: ModalSelecetComponent;
    @ViewChild('editor') editor: EditorComponent;

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

    formErrors = {

        name: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大32位长度',
            }
        ],
        rewardType: [
            {
                name: 'maxlength',
                msg: '最大3位长度',
            }
        ],
        signDays: [
            {
                name: 'maxlength',
                msg: '最大3位长度',
            }
        ],
//        inviteNum: [
//            {
//                name: 'maxlength',
//                msg: '最大3位长度',
//            }
//        ],
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
        remark: [
            {
                name: 'maxlength',
                msg: '最大127位长度',
            }
        ],
        reminder: [],
        prizes: [],
    };

    constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService, public prizeService: PrizeService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.reward !== undefined && value.reward.currentValue !== undefined) {
            this.setBuildFormValue(this.reward);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            name: [null, Validators.compose([Validators.required, Validators.maxLength(32)])],
            rewardType: [1, Validators.compose([Validators.required])],
            signDays: [0],
//            inviteNum: [0],
            state: [0, Validators.compose([Validators.required, Validators.maxLength(3)])],
            remark: [null, Validators.compose([Validators.maxLength(127)])],
            reminder: [],
            prizes: [[]],
        });
    }

    setBuildFormValue(reward: RewardBo) {
        this.commonForm.setValue({
            name: reward.name,
            rewardType: reward.rewardType,
            signDays: reward.signDays,
//            inviteNum: reward.inviteNum,
            state: reward.state,
            remark: reward.remark,
            prizes: reward.prizes,
            reminder: reward.reminder,
        });
        this.modalSelect.dataRetrieval(reward.prizes)
        //设置富文本内容
        this.editor.setContent(this.commonForm.value.reminder);
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
        if (this.reward) {
        }
        if (!formValue) {
            this.msgSrv.warning('校验尚未通过无法提交，请确认输入项！');
            return;
        }
        this.onTransmitFormValue.emit({ obj: formValue });
    }

    reset() {

    }

    goBack() {
        this.location.back();
    }

    setPrizes(event) {
        this.commonForm.patchValue({
            prizes: event.map(e => {
                return { id: e.id, name: e.name }
            })
        });
    }

    /**
     * 富文本回调赋值
     * @param event
     */
    setReminder(event) {
        this.commonForm.patchValue({
            reminder: event
        });
    }

}
