import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewChild, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Message} from '../../../models/original/message.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';
import {MemberService} from "../../../services/member.service";
import {PageQuery} from "../../../models/page-query.model";
import {ModalSelecetComponent} from "../../../components/modal-selecet/modal-selecet.component";

@Component({
    selector: 'form-message',
    templateUrl: './form-message.component.html',
    styleUrls: ['./form-message.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class FormMessageComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() title: string = '表单';

    @Input() message: Message = new Message();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

    memberPageQuery: PageQuery = new PageQuery();

    @ViewChild('memberModalSelect') memberModalSelect: ModalSelecetComponent;

    formErrors = {
        title: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大127位长度',
            }
        ],
        content: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大65535位长度',
            }
        ],
        sort: [],
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
        //        messageType: [
        //            {
        //                name: 'required',
        //                msg: '不可为空',
        //            },
        //            {
        //                name: 'maxlength',
        //                msg: '最大3位长度',
        //            }
        //        ],
    };

    constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService, public memberService: MemberService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.message !== undefined && value.message.currentValue !== undefined) {
            this.setBuildFormValue(this.message);
        }
    }

    ngOnInit() {
//        this.memberPageQuery.addOnlyOneFilter('state', 0, 'eq');
    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            title: [null, Validators.compose([Validators.required, Validators.maxLength(127)])],
            content: [null, Validators.compose([Validators.required, Validators.maxLength(65535)])],
            sort: [],
            state: [0, Validators.compose([Validators.required, Validators.min(0), Validators.max(1)])],
            pushScope: [0],
            members: [],
            //messageType: [0, Validators.compose([Validators.required, Validators.min(0)])]
        });
    }

    setBuildFormValue(message: Message) {
        this.commonForm.setValue({
            title: message.title,
            content: message.content,
            sort: message.sort,
            state: message.state,
            pushScope: 0,
            members: message.members,
            //messageType: message.messageType,
        });
        this.memberModalSelect.dataRetrieval(message.members);
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
        this.onTransmitFormValue.emit({ obj: formValue });
    }

    reset() {

    }

    goBack() {
        this.location.back();
    }

    setMemebrs(event) {
        this.commonForm.patchValue({
            members: event.map(e => {
                return { id: e.id, nickname: e.nickname, username: e.username }
            })
        });
    }

}
