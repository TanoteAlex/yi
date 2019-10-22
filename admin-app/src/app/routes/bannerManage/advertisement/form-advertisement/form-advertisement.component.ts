import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewChild, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ObjectUtils} from '@shared/utils/ObjectUtils';
import {Advertisement} from "../../../models/original/advertisement.model";
import {PositionService} from "../../../services/position.service";
import {ArticleService} from "../../../services/article.service";
import {CommodityService} from "../../../services/commodity.service";
import {SUCCESS} from "../../../models/constants.model";
import {PageQuery} from "../../../models/page-query.model";
import {articleValidator, commodityValidator} from "@shared/custom-validators/custom-validator";
import {ModalSelecetComponent} from "../../../components/modal-selecet/modal-selecet.component";

@Component({
    selector: 'form-advertisement',
    templateUrl: './form-advertisement.component.html',
    styleUrls: ['./form-advertisement.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class FormAdvertisementComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() title: string = '表单';

    @Input() advertisement: Advertisement = new Advertisement();

    positionPageQuery: PageQuery = new PageQuery()
    commodityPageQuery: PageQuery = new PageQuery();
    @ViewChild('positionModalSelect') positionModalSelect: ModalSelecetComponent;
    @ViewChild('commodityModalSelect') commodityModalSelect: ModalSelecetComponent;
    @ViewChild('articleModalSelect') articleModalSelect: ModalSelecetComponent;

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

        sort: [],
        state: [
            {
                name: 'required',
                msg: '不可为空',
            },
        ],
        position: [
            {
                name: 'required',
                msg: '不可为空',
            }
        ],
        linkType: [
            {
                name: 'required',
                msg: '不可为空',
            }
        ],
        linkId: [],
        commodity: [
            {
                name: 'commodityRequire',
                msg: '请选择商品'
            }
        ],
        article: [
            {
                name: 'articleRequire',
                msg: '请选择文章'
            }
        ]
    };

    constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService
        , public positionService: PositionService, public articleService: ArticleService, public commodityService: CommodityService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.advertisement !== undefined && value.advertisement.currentValue !== undefined) {
            this.setBuildFormValue(this.advertisement);
        }
    }

    linkTypes = [{
        code: 1,
        title: "商品",
    }, {
            code: 2,
            title: "文章",
        }/*, {
    code: 3,
    title: "活动",
  }, {
    code: 4,
    title: "专区",
  }*/]


    ngOnInit() {
        //位置类型 只查询轮播图推荐
        this.positionPageQuery.addOnlyFilter("positionType", "1", "eq")
        this.positionPageQuery.addOnlyFilter("state", "0", "eq")
        this.commodityPageQuery.addFilter("shelfState", 1, "eq");
        this.commodityPageQuery.addOnlyFilter("auditState", 2, "eq")
    }

    setCommodity(event) {
        if (event.commodityShortName != null && event.length != 0) {
            this.commonForm.patchValue({
                linkId: event.id
            })
            this.commonForm.patchValue({
                commodity: {
                    id: event.id,
                    commodityShortName: event.commodityShortName
                }
            })
            this.commonForm.patchValue({
                article: null
            })
        }
    }

    setArticle(event) {
        if (event.title != null && event.length != 0) {
            this.commonForm.patchValue({
                linkId: event.id
            })
            this.commonForm.patchValue({
                article: {
                    id: event.id,
                    title: event.title
                }
            })
            this.commonForm.patchValue({
                commodity: null
            })
        }
    }

    getPic(event) {
        this.commonForm.patchValue({
            imgPath: event.length != 0 ? (event[0].response.data ? event[0].response.data[0].url : null) : null
        })
    }

    setPositionlier(e) {
        if (e.name) {
            this.commonForm.patchValue({
                position: {
                    id: e.id,
                    positionType: e.positionType,
                    name: e.name,
                }
            })
        }
    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            title: [null, Validators.compose([Validators.required, Validators.maxLength(127)])],
            imgPath: [],
            sort: [],
            state: [0, Validators.compose([Validators.required, Validators.min(0), Validators.max(1)])],
            position: [null, Validators.compose([Validators.required])],
            linkType: [null, Validators.compose([Validators.required])],
            linkId: [],
            commodity: [null],
            article: [null]
        });
        //自定义广告链接表单
        this.commonForm.get('commodity').setValidators(commodityValidator(this.commonForm.get('linkType')))
        this.commonForm.get('article').setValidators(articleValidator(this.commonForm.get('linkType')))
    }

    setBuildFormValue(advertisement: Advertisement) {
        this.commonForm.setValue({
            title: advertisement.title,
            imgPath: advertisement.imgPath,
            linkType: advertisement.linkType,
            sort: advertisement.sort,
            state: advertisement.state,
            position: {
                id: advertisement.position.id,
                positionType: advertisement.position.positionType,
                name: advertisement.position.name
            },
            linkId: advertisement.linkId,
            commodity: null,
            article: null,
        });

        if (this.advertisement != null) {
            if (this.advertisement.linkType == 1) {
                this.commodityService.getVoById(this.advertisement.linkId).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.commonForm.patchValue({
                            commodity: {
                                id: response.data.id,
                                commodityShortName: response.data.commodityShortName
                            },
                            article: null,
                        })
                        //设置商品
                        this.commodityModalSelect.dataRetrieval(this.commonForm.value.commodity)
                    } else {
                        this.msgSrv.error('请求失败', response.message);
                    }
                }, error => {
                    this.msgSrv.error('请求错误', error.message);
                });
            } else if (this.advertisement.linkType == 2) {
                this.articleService.getVoById(this.advertisement.linkId).subscribe(response => {
                    if (response.result == SUCCESS) {
                        /*  this.article = response.data;*/
                        this.commonForm.patchValue({
                            article: {
                                id: response.data.id,
                                title: response.data.title
                            },
                            commodity: null,
                        })
                        //设置爱生活
                        this.articleModalSelect.dataRetrieval(this.commonForm.value.article)
                    } else {
                        this.msgSrv.error('请求失败', response.message);
                    }
                }, error => {
                    this.msgSrv.error('请求错误', error.message);
                });
            }
        }
        //设置位置
        this.positionModalSelect.dataRetrieval(advertisement.position)
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

}
