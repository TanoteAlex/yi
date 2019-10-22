import {Component, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {PageQuery} from '../../../models/page-query.model';
import {BasicRuleService} from '../../../services/basic-rule.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {SUCCESS} from "../../../models/constants.model";
import {EditorComponent} from "../../../components/editor/editor.component";
import {Location} from "@angular/common";
import {ObjectUtils} from "@shared/utils/ObjectUtils";

@Component({
  selector: 'list-basic-rule',
  templateUrl: './list-basic-rule.component.html',
  styleUrls: ['./list-basic-rule.component.less'],
  encapsulation: ViewEncapsulation.None
})
export class ListBasicRuleComponent implements OnInit {

  pageQuery: PageQuery = new PageQuery();

  commonForm: FormGroup;

  loading = false;

  datas: any[] = [];

  expandForm = false;

  @ViewChild('editor') editor: EditorComponent;

  constructor(public route: ActivatedRoute, public router: Router, private basicRuleService: BasicRuleService, public msg: NzMessageService,
              private fb: FormBuilder, private modalService: NzModalService) {
    this.buildForm();

  }

  ngOnInit() {
    this.basicRuleService.getAll().subscribe(e => {
      this.editor.setContent(e.data.content);
    }, error => {
      this.msg.error('请求错误' + error.message);
    })

  }

  formErrors = {
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
  };


  buildForm(): void {
    this.commonForm = this.fb.group({
      content: [null, Validators.compose([Validators.required,Validators.maxLength(65535)])],
    });
  }


  thematicText(e) {
    this.commonForm.patchValue({
      content: e
    })
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
      this.msg.warning('校验尚未通过无法提交，请确认输入项！');
      return;
    }
    this.basicRuleService.update(formValue).subscribe(response => {
      if (response.result == SUCCESS) {
        this.msg.success("保存成功");
      } else {
        this.msg.error('请求失败' + response.message);
      }
    }, error => {
      this.msg.error('请求失败' + error.message);
    });

  }


}
