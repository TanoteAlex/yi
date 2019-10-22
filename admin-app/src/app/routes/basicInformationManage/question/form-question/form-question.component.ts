import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewChild, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Question} from '../../../models/original/question.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';
import {QuestionTypeService} from "../../../services/question-type.service";
import {PageQuery} from "../../../models/page-query.model";
import {ModalSelecetComponent} from "../../../components/modal-selecet/modal-selecet.component";

@Component({
  selector: 'form-question',
  templateUrl: './form-question.component.html',
  styleUrls: ['./form-question.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormQuestionComponent implements OnInit, OnChanges {

  commonForm: FormGroup;

  @Input() title: string = '表单';

  @Input() question: Question = new Question();

  @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

  questionTypePageQuery: PageQuery = new PageQuery();

  @ViewChild('questionTypeModalSelect') questionTypeModalSelect: ModalSelecetComponent;

  formErrors = {
    questionType: [
      {
        name: 'required',
        msg: '不可为空',
      },
    ],
    askQuestion: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大255位长度',
      }
    ],
    answerQuestion: [
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
  };

  constructor(public msg: NzMessageService, private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService, public questionTypeService: QuestionTypeService) {
    this.buildForm();
  }


  ngOnChanges(value) {
    if (value.question !== undefined && value.question.currentValue !== undefined) {
      this.setBuildFormValue(this.question);
    }
  }

  ngOnInit() {
    this.questionTypePageQuery.addOnlyOneFilter("state", 0, "eq");
  }

  buildForm(): void {
    this.commonForm = this.fb.group({
      questionType: [null, Validators.compose([Validators.required])],
      askQuestion: [null, Validators.compose([Validators.required, Validators.maxLength(255)])],
      answerQuestion: [null, Validators.compose([Validators.required, Validators.maxLength(65535)])],
      sort: [],
      state: [0, Validators.compose([Validators.required, Validators.min(0), Validators.max(1)])],
    });
  }

  setBuildFormValue(question: Question) {
    this.commonForm.patchValue({
      questionType: {
        id: question.questionType.id,
        typeName: question.questionType.typeName
      },
      askQuestion: question.askQuestion,
      answerQuestion: question.answerQuestion,
      sort: question.sort,
      state: question.state,
    });
    this.questionTypeModalSelect.dataRetrieval(question.questionType)
  }

  setQuestionType(e) {
    if (e.typeName) {
      this.commonForm.patchValue({
        questionType: {
          id: e.id,
          typeName: e.typeName
        }
      })
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
