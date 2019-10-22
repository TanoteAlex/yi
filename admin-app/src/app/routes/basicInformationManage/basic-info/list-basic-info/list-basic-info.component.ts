import {Component, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {PageQuery} from '../../../models/page-query.model';
import {BasicInfoService} from '../../../services/basic-info.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {SUCCESS} from "../../../models/constants.model";
import {EditorComponent} from "../../../components/editor/editor.component";
import {BasicInfo} from "../../../models/original/basic-info.model";
import {ObjectUtils} from "@shared/utils/ObjectUtils";

@Component({
  selector: 'list-basic-info',
  templateUrl: './list-basic-info.component.html',
  styleUrls: ['./list-basic-info.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ListBasicInfoComponent implements OnInit {

  pageQuery: PageQuery = new PageQuery();

  @ViewChild('editor') editor: EditorComponent;


  commonForm: FormGroup;

  loading = false;

  datas: any[] = [];

  expandForm = false;

  basicInfo: BasicInfo = new BasicInfo();

  constructor(public route: ActivatedRoute, public router: Router, private basicInfoService: BasicInfoService, public msg: NzMessageService,
              private fb: FormBuilder, private modalService: NzModalService, public msgSrv: NzMessageService) {
    this.buildForm();
  }

  getAll() {
    this.basicInfoService.getAll().subscribe(e => {
      this.basicInfo = e.data;
      this.setBuildFormValue(this.basicInfo);

      this.loading = false;
    }, error => {
      this.loading = false;
      this.msg.error('请求错误' + error.message);
    });
  }

  ngOnInit() {
    this.getAll();
  }


  formErrors = {
    logoUrl: [
      {
        name: 'required',
        msg: '不可为空',
      },
    ],
    companyName: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大127位长度',
      }
    ],
    companyAddr: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大255位长度',
      }
    ],
    companyTel: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大16位长度',
      }
    ],
    companyMobile: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大16位长度',
      }
    ],
    companyMail: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大32位长度',
      }
    ],
    setupTime: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大19位长度',
      }
    ],
    contentProfile: [
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

  thematicText(e) {
    this.commonForm.patchValue({
      contentProfile: e
    })
  }


  buildForm(): void {
    this.commonForm = this.fb.group({
      logoUrl: [
        null, Validators.compose([Validators.required])
      ],
      companyName: [
        null, Validators.compose([Validators.required,
          Validators.maxLength(127)
        ])
      ],
      companyAddr: [
        null, Validators.compose([Validators.required,
          Validators.maxLength(255)
        ])
      ],
      companyTel: [
        null, Validators.compose([Validators.required,
          Validators.maxLength(16)
        ])
      ],
      companyMobile: [
        null, Validators.compose([Validators.required,
          Validators.maxLength(16)
        ])
      ],
      companyMail: [
        null, Validators.compose([Validators.required,
          Validators.maxLength(32)
        ])
      ],
      /*  setupTime: [],*/
      contentProfile: [null, Validators.compose([Validators.required,
        Validators.maxLength(65535)
      ])],
    });

  }

  ngOnChanges(value) {
    if (value.basicInfo !== undefined && value.basicInfo.currentValue !== undefined) {
      this.setBuildFormValue(this.basicInfo);
    }
  }

  setBuildFormValue(basicInfo: BasicInfo) {
    this.commonForm.setValue({
      logoUrl:
      basicInfo.logoUrl
      ,
      companyName:
      basicInfo.companyName
      ,
      companyAddr:
      basicInfo.companyAddr
      ,
      companyTel:
      basicInfo.companyTel
      ,
      companyMobile:
      basicInfo.companyMobile
      ,
      companyMail:
      basicInfo.companyMail
      ,
      /*    setupTime:
          basicInfo.setupTime
          ,*/
      contentProfile:
      basicInfo.contentProfile
      ,
    });
    this.editor.setContent(this.commonForm.value.contentProfile);

  }

  getPic(event) {
    this.commonForm.patchValue({
      logoUrl: event.length != 0 ? (event[0].response.data ? event[0].response.data[0].url : null) : null
    })
  }


  getData() {
    this.loading = true;
    this.basicInfoService.query(this.pageQuery).subscribe(response => {
      this.datas = response['content'];
      this.pageQuery.covertResponses(response);
      this.loading = false;
    }, error => {
      this.loading = false;
      this.msg.error('请求错误' + error.message);
    });
  }


  remove(basicInfoId) {
    this.basicInfoService.removeById(basicInfoId).subscribe(response => {
      if (response.result == SUCCESS) {
        this.msg.success("删除成功");
        this.getData();
      } else {
        this.msg.error('请求失败' + response.message);
      }
    }, error => {
      this.msg.error('请求失败' + error.message);
    });
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
    if (this.basicInfo) {
    }
    if (!formValue) {
      this.msgSrv.warning('校验尚未通过无法提交，请确认输入项！');
      return;
    }

    if (formValue.contentProfile == null) {
      this.msgSrv.warning('请输入公司简介');
      return;
    }
    this.basicInfoService.update(formValue).subscribe(response => {
      if (response.result == SUCCESS) {
        this.msg.success("保存成功");
        this.getAll();
      } else {
        this.msg.error('请求失败' + response.message);
      }
    }, error => {
      this.msg.error('请求失败' + error.message);
    });
  }

}
