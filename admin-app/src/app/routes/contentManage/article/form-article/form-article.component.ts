import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewChild, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Article} from '../../../models/original/article.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';
import {EditorComponent} from "../../../components/editor/editor.component";
import {CommodityService} from "../../../services/commodity.service";
import {PageQuery} from "../../../models/page-query.model";
import {ModalSelecetComponent} from "../../../components/modal-selecet/modal-selecet.component";

@Component({
    selector: 'form-article',
    templateUrl: './form-article.component.html',
    styleUrls: ['./form-article.component.less'],
    encapsulation: ViewEncapsulation.None
})
export class FormArticleComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() title: string = '表单';

    @Input() article: Article = new Article();

    @ViewChild('editor') editor: EditorComponent;

    @ViewChild('modalSelect') modalSelect: ModalSelecetComponent;

    imgPaths = [];

    commodityTable = [];

    @Input()
    pageQuery = new PageQuery().addFilter("weight", '0', "eq");
    commodityPageQuery: PageQuery = new PageQuery();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

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
        subtitle: [
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
                name: 'maxlength',
                msg: '最大65535位长度',
            }
        ],
        imgPath: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大255位长度',
            }
        ],
        url: [
            {
                name: 'maxlength',
                msg: '最大255位长度',
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
        commodities: [],
        attachmentVos: []
    };

    constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService, public commodityService: CommodityService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.article !== undefined && value.article.currentValue !== undefined) {
            this.setBuildFormValue(this.article);
        }
    }



    ngOnInit() {
        this.commodityPageQuery.addOnlyOneFilter('auditState', 2, 'eq');
        this.commodityPageQuery.addOnlyOneFilter('shelfState', 1, 'eq');
    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            title: [null, Validators.compose([Validators.required, Validators.maxLength(127)])],
            subtitle: [null, Validators.compose([Validators.required, Validators.maxLength(127)])],
            content: [],
            imgPath: [null, Validators.compose([Validators.required])],
            url: [],
            state: [
                0, Validators.compose([Validators.required,
                    Validators.min(0), Validators.max(1)
                ])
            ],
            commodities: [[]],
            attachmentVos: []
        });
    }

    setBuildFormValue(article: Article) {
        this.commonForm.setValue({
            title: article.title,
            subtitle: article.subtitle,
            content: article.content,
            imgPath: article.imgPath,
            url: article.url,
            state: article.state,
            commodities: article.commodities,
            attachmentVos: article.attachmentVos,
        });
        this.editor.setContent(this.commonForm.value.content);
        this.commonForm.value.commodities.map(e => {
            this.commodityTable.push(e);
        });
        this.modalSelect.dataRetrieval(article.commodities)
        //赋值商品图片数组
        this.getCommodityImgPath();
    }

    /**
     * 赋值商品图片数组
     */
    getCommodityImgPath() {
        if (this.commonForm.value.attachmentVos != null) {
            this.imgPaths = []
            this.commonForm.value.attachmentVos.forEach(e => {
                if (e != null) {
                    this.imgPaths.push({ oldValue: e, url: e.url ? e.url : e.filePath, status: 'edit' })
                }
            })
        }
        if (this.imgPaths.length == 0) {
            this.commonForm.patchValue({
                imgPath: null
            })
        }
    }

    /**
     * 商品图片回调
     * @param fileList
     */
    changeAttachment(fileList) {
        let newAttachmentVos = fileList.map(e => {
            if (e.status == 'edit') {
                return e.oldValue
            } else {
                return e.response ? e.response.data[0] : null
            }
        })
        this.commonForm.patchValue({
            attachmentVos: newAttachmentVos
        })
        if (newAttachmentVos.length != 0) {
            this.commonForm.patchValue({
                imgPath: newAttachmentVos[0].url ? newAttachmentVos[0].url : newAttachmentVos[0].filePath
            })
        }
        this.getCommodityImgPath()
    }


    thematicText(event) {
        this.commonForm.patchValue({
            content: event
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

    setCommodity(event) {
        this.commodityTable = event;
        this.commonForm.patchValue({
            commodities: event.map(e => {
                return { id: e.id, commodityName: e.commodityName, commodityNo: e.commodityNo, imgPath: e.imgPath }
            })
        });
    }

}
