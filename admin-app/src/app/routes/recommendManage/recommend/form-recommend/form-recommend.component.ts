import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewChild, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Recommend} from '../../../models/original/recommend.model';
import {Commodity} from '../../../models/original/commodity.model';
import {RecommendCommodity} from '../../../models/original/recommend-commodity.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';
import {CommodityService} from "../../../services/commodity.service";
import {RecommendCommodityService} from "../../../services/recommend-commodity.service";
import {PageQuery} from "../../../models/page-query.model";
import {PositionService} from "../../../services/position.service";
import {ModalSelecetComponent} from "../../../components/modal-selecet/modal-selecet.component";
import {SUCCESS} from "../../../models/constants.model";
import {ArticleService} from "../../../services/article.service";
import {articleValidator, commodityValidator} from "@shared/custom-validators/custom-validator";

@Component({
    selector: 'form-recommend',
    templateUrl: './form-recommend.component.html',
    styleUrls: ['./form-recommend.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class FormRecommendComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() title: string = '表单';

    @Input() recommend: Recommend = new Recommend();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

    @ViewChild('modalSelect') modalSelect: ModalSelecetComponent;
    @ViewChild('positionModalSelect') positionModalSelect: ModalSelecetComponent;
    @ViewChild('commodityModalSelect') commodityModalSelect: ModalSelecetComponent;
    @ViewChild('articleModalSelect') articleModalSelect: ModalSelecetComponent;
    commodityPageQuery: PageQuery = new PageQuery();
    commoditiesPageQuery: PageQuery = new PageQuery();
    positionPageQuery: PageQuery = new PageQuery();
    articlePageQuery: PageQuery = new PageQuery();

    linkTypes = [
        { code: 1, title: "商品", },
        { code: 2, title: "文章", },
        // {code: 3, title: "活动",},
        // {code: 4, title: "专区",}
    ]

    formErrors = {
        position: [
            {
                name: 'required',
                msg: '不可为空',
            }
        ],
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
                name: 'maxlength',
                msg: '最大255位长度',
            }
        ],
        sort: [],
        recommendType: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大3位长度',
            }
        ],
        showMode: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大3位长度',
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

        showBanner: [{
            name: 'required',
            msg: '不可为空',
        },
            {
                name: 'maxlength',
                msg: '最大3位长度',
            }],
        showTitle: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大3位长度',
            }
        ],
        recommendCommodities: [
            {
                name: 'required',
                msg: '不可为空',
            },
        ],
        linkType: [],
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
        ],
    };

    constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService, public commodityService: CommodityService, public msg: NzMessageService, public positionService: PositionService, public articleService: ArticleService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.recommend !== undefined && value.recommend.currentValue !== undefined) {
            this.setBuildFormValue(this.recommend);
        }
    }

    ngOnInit() {
        this.positionPageQuery.addOnlyFilter("positionType", "1", "neq");
        this.positionPageQuery.addOnlyFilter("state", "0", "eq");
        this.commodityPageQuery.addFilter("auditState", 2, "eq");
        this.commodityPageQuery.addFilter("shelfState", 1, "eq");
        this.commoditiesPageQuery.addFilter("auditState", 2, "eq");
        this.commoditiesPageQuery.addFilter("shelfState", 1, "eq");
        this.articlePageQuery.addOnlyFilter("state", "0", "eq");
    }

    getPic(event) {
        this.commonForm.patchValue({
            imgPath: event.length != 0 ? (event[0].response.data ? event[0].response.data[0].url : null) : null
        })
    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            position: [null, Validators.compose([Validators.required])],
            title: [null, Validators.compose([Validators.required, Validators.maxLength(127)])],
            sort: [],
            imgPath: [],
            recommendType: [1, Validators.compose([Validators.required, Validators.min(1), Validators.max(2)])],
            showMode: [2, Validators.compose([Validators.required, Validators.min(2), Validators.max(4)])],
            state: [0, Validators.compose([Validators.required, Validators.min(0), Validators.max(1)])],
            recommendCommodities: [null, Validators.compose([Validators.required])],
            showBanner: [0, Validators.compose([Validators.required, Validators.min(0), Validators.max(1)])],
            showTitle: [0, Validators.compose([Validators.required, Validators.min(0), Validators.max(1)])],
            linkType: [],
            linkId: [],
            commodity: [null],
            article: [null],
        });
        //自定义图片链接表单
        this.commonForm.get('commodity').setValidators(commodityValidator(this.commonForm.get('linkType')))
        this.commonForm.get('article').setValidators(articleValidator(this.commonForm.get('linkType')))
    }


    setBuildFormValue(recommend: Recommend) {
        this.commonForm.setValue({
            position: {
                id: recommend.position.id,
                name: recommend.position.name
            },
            title: recommend.title,
            recommendCommodities: recommend.recommendCommodities,
            sort: recommend.sort,
            imgPath: recommend.imgPath,
            recommendType: recommend.recommendType,
            showMode: recommend.showMode,
            state: recommend.state,
            showBanner: recommend.showBanner,
            showTitle: recommend.showTitle,
            linkType: recommend.linkType,
            linkId: recommend.linkId,
            commodity: null,
            article: null,
        });

        //        this.commodities = recommend.recommendCommodities.map(e => e.commodity);
        //设置推荐商品
        this.modalSelect.dataRetrieval(recommend.recommendCommodities);
        //设置位置
        this.positionModalSelect.dataRetrieval(recommend.position)

        if (this.recommend != null) {
            if (this.recommend.linkType == 1) {
                this.commodityService.getVoById(this.recommend.linkId).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.commonForm.patchValue({
                            commodity: {
                                id: response.data.id,
                                commodityName: response.data.commodityName
                            },
                            article: null
                        })
                        this.commodityModalSelect.dataRetrieval(this.commonForm.value.commodity);
                    } else {
                        this.msgSrv.error(response.message ? response.message : '请求失败');
                    }
                }, error => {
                    this.msgSrv.error(error.message ? error.message : '请求错误');
                });
            } else if (this.recommend.linkType == 2) {
                this.articleService.getVoById(this.recommend.linkId).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.commonForm.patchValue({
                            article: {
                                id: response.data.id,
                                title: response.data.title
                            },
                            commodity: null,
                        })
                        this.articleModalSelect.dataRetrieval(this.commonForm.value.article);
                    } else {
                        this.msgSrv.error(response.message ? response.message : "请求失败");
                    }
                }, error => {
                    this.msgSrv.error(error.message ? error.message : "请求错误");
                });
            }
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
        this.onTransmitFormValue.emit({ obj: formValue });
    }

    reset() {

    }

    goBack() {
        this.location.back();
    }

    setCommodity(event) {
        if (event.commodityName != null && event.length != 0) {
            this.commonForm.patchValue({
                linkId: event.id
            })
            this.commonForm.patchValue({
                commodity: {
                    id: event.id,
                    commodityName: event.commodityName
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

    setRecommendCommodities(event) {
        this.commonForm.patchValue({
            recommendCommodities: event.map(e => {
                return {
                    commodity: {
                        id: e.id,
                        commodityNo: e.commodityNo,
                        commodityName: e.commodityName,
                        imgPath: e.imgPath
                    },
                    commodityNo: e.commodityNo,
                    commodityName: e.commodityName,
                    imgPath: e.imgPath,
                    id: e.id,
                    sort: e.sort
                }
            })
        });
    }

    setRecommendPosition(event) {
        if (event.name) {
            this.commonForm.patchValue({
                position: {
                    id: event.id,
                    name: event.name,
                }
            });
            // if (event.name == "双星专区") {
            //   this.commoditiesPageQuery.addOnlyOneFilter("commodityType", 1, "eq");
            // }
        }
    }

}
