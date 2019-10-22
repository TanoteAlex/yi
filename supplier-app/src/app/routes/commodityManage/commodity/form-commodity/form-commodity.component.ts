import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewChild} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormArray, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Commodity} from '../../../models/original/commodity.model';
import {ObjectUtils} from 'app/shared/utils/ObjectUtils';
import {FreightTemplateService} from "../../../services/freight-template.service";
import {RegionService} from "../../../services/region.service";
import {EditorComponent} from "../../../components/editor/editor.component";
import {CategoryService} from "../../../services/category.service";
import {PageQuery} from "../../../models/page-query.model";
import {MemberLevelService} from "../../../services/member-level.service";
import {CategoryTreeSelectComponent} from "../../../components/category-tree-select/category-tree-select.component";
import {CommodityService} from "../../../services/commodity.service";
import {RegionGroupComponent} from "../../../components/region-group/region-group.component";
import {FormAttrGroup} from "./form-attr-group";
import {freightTemplateValidator} from "@shared/custom-validators/custom-validator";
import {COMMODITY_FORM_ERRORS} from "@shared/common/common-form-error";
import {FreightTemplateConfigService} from "../../../services/freight-template-config.service";
import {ModalSelecetComponent} from "../../../../../../../supplier-app/src/app/routes/components/modal-selecet/modal-selecet.component";

@Component({
    selector: 'form-commodity',
    templateUrl: './form-commodity.component.html',
})
export class FormCommodityComponent extends FormAttrGroup implements OnInit, OnChanges {
    formatterProportion = value => `${value} %`;   //单位（%）
    parserProportion = value => value.replace(' %', '');
    imgPaths = [];
    commodityLevelDiscounts: FormGroup;

    @Input() title: string = '表单';
    @Input() commodity: Commodity = new Commodity();
    pageQuery: PageQuery = new PageQuery()
    freightTemplateConfigPageQuery: PageQuery = new PageQuery()
    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

    @ViewChild('editor') editor: EditorComponent;
    @ViewChild('categoryTreeSelect') categoryTreeSelect: CategoryTreeSelectComponent
    @ViewChild('operateCategoryTreeSelect') operateCategoryTreeSelect: CategoryTreeSelectComponent
    @ViewChild('regionGroup') regionGroup: RegionGroupComponent
    @ViewChild('freightModalSelect') freightTemplateConfigModalSelect: ModalSelecetComponent

    formErrors = COMMODITY_FORM_ERRORS;

    constructor(public commodityService: CommodityService, public freightTemplateConfigService: FreightTemplateConfigService, public regionService: RegionService, public fb: FormBuilder, public location: Location, public msgSrv: NzMessageService, public categoryService: CategoryService, public memberLevelService: MemberLevelService) {
        super(commodityService, fb);
        this.buildForm();
    }

    ngOnInit() {
        this.freightTemplateConfigPageQuery.addOnlyFilter("state", 0, "eq");
    }

    ngOnChanges(value) {
        if (value.commodity !== undefined && value.commodity.currentValue !== undefined) {
            this.setBuildFormValue(this.commodity);
        }
    }

    /**
     * 获取会员等级
     */
    initCommodityLevelDiscountsFormArray() {
        this.memberLevelService.query(this.pageQuery).subscribe(response => {
            this.setCommodityLevelDiscountsFormArray(response.content, true)
        })
    }

    /**
     * 根据值构建会员折扣
     * @param commodityLevelDiscounts
     * @param init                       判断是否初始化还是填充表单
     */
    setCommodityLevelDiscountsFormArray(commodityLevelDiscounts, init?) {
        let commodityLevelDiscountFormArray: FormArray = new FormArray([]);
        commodityLevelDiscounts.forEach(e => {
            commodityLevelDiscountFormArray.push(this.fb.group({
                id: init ? null : e.id,
                guid: null,
                commodity: null,
                memberLevel: e.memberLevel ? e.memberLevel : e,
                memberLevelName: e.memberLevel ? e.memberLevel.name : e.name,
                discount: init ? null : e.discount,
            }))
        })
        this.commonForm.setControl('commodityLevelDiscounts', commodityLevelDiscountFormArray)
    }

    /**
     * 货品图片上传回调
     * @param event
     * @param index
     */
    getProductImgPath(event, index) {
        if (event.length != 0) {
            this.commonForm.get('products').get(index + '').patchValue({
                productImgPath: event[0].response.data[0].url
            })
        } else {
            this.commonForm.get('products').get(index + '').patchValue({
                productImgPath: null
            })
        }
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

    /**
     * 富文本回调赋值
     * @param event
     */
    setDescription(event) {
        this.commonForm.patchValue({
            description: event
        });
    }

    /**
     * 运费模版回调赋值
     * @param event
     */
    setCommodityFreightTemplate(event) {
        if (event.configName && event.length != 0) {
            this.commonForm.patchValue({
                freightTemplate: {
                    id: event.id,
                    configName: event.configName,
                }
            });
        }
    }

    /**
     * 设置商品分类
     * @param choose
     */
    setCommodityCategory(choose) {
        if (choose.id != null) {
            this.commonForm.patchValue({
                category: choose
            })
            this.setGroupTip(choose);
        }
    }

    /**
     * 运营分类回调赋值
     * @param choose
     */
    setCommodityOperateCategory(choose) {
        let addOperateCategories = true;
        choose.forEach(e => {
            if (e.id == 0) {
                this.msgSrv.warning(e.categoryName + '下没有二级分类！');
                addOperateCategories = false;
                return;
            }
        })
        if (addOperateCategories) {
            this.commonForm.patchValue({
                operateCategories: choose
            })
        }
    }

    /**
     * 地区回调赋值
     * @param event
     */
    setRegions(event) {
        this.commonForm.patchValue({
            regions: event
        })
    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            commodityNo: [null, Validators.compose([Validators.maxLength(32)])],
            commodityName: [null, Validators.compose([Validators.required, Validators.maxLength(64)])],
            commodityShortName: [null, Validators.compose([Validators.required, Validators.maxLength(64)])],
            imgPath: [null, Validators.compose([Validators.required])],
            sort: [],
            distribution: [0, Validators.compose([Validators.required, Validators.min(0), Validators.max(1)])],
            integralRate: [0, Validators.compose([Validators.required, Validators.maxLength(4)])],
            freightSet: [1, Validators.compose([Validators.required, Validators.min(1), Validators.max(2)])],
            unifiedFreight: [0],
            freightTemplate: [null],
            stockSet: [1, Validators.compose([Validators.required, Validators.min(1), Validators.max(2)])],
            shelfState: [1, Validators.compose([Validators.required, Validators.min(1), Validators.max(2)])],
            volume: [0],
            weight: [0],
            description: [],
            attributeGroups: this.fb.array([]),
            products: this.fb.array([]),
            regions: [null, Validators.compose([Validators.required])],
            category: [null, Validators.compose([Validators.required])],
            operateCategories: [[]],
            attachmentVos: [[]],
            commodityLevelDiscounts: this.fb.array([]),
            commodityType: [0, Validators.compose([Validators.required])],
            auditState: [1],
        });
        //自定义运费模版表单
        this.commonForm.get('freightTemplate').setValidators(freightTemplateValidator(this.commonForm.get('freightSet')))
        //初始化折扣表单
        this.initCommodityLevelDiscountsFormArray()
    }

    setBuildFormValue(commodity: Commodity) {
        this.isngOnInit = false;
        this.commonForm.patchValue({
            commodityNo: commodity.commodityNo,
            commodityName: commodity.commodityName,
            commodityShortName: commodity.commodityShortName,
            imgPath: commodity.imgPath,
            sort: commodity.sort,
            distribution: commodity.distribution,
            integralRate: commodity.integralRate,
            freightSet: commodity.freightSet,
            unifiedFreight: commodity.unifiedFreight,
            freightTemplate: commodity.freightTemplate,
            stockSet: commodity.stockSet,
            shelfState: commodity.shelfState,
            volume: commodity.volume,
            weight: commodity.weight,
            description: commodity.description,
            attributeGroups: [],
            products: [],
            regions: commodity.regions,
            category: commodity.category,
            operateCategories: commodity.operateCategories,
            attachmentVos: commodity.attachmentVos,
            commodityType: commodity.commodityType,
            auditState: 1,
        });
        this.regionGroup.select(commodity.regions)
        //赋值商品图片数组
        this.getCommodityImgPath();

        //选择商品管理分类  运营分类
        this.categoryTreeSelect.select(this.commonForm.value.category.id)
        this.operateCategoryTreeSelect.select(this.commonForm.value.operateCategories.map((e) => e.id))
        //设置富文本内容
        this.editor.setContent(this.commonForm.value.description);
        //设置属性组表单
        this.setFormAttrGroup(commodity);
        //设置会员折扣表单
        this.setCommodityLevelDiscountsFormArray(commodity.commodityLevelDiscounts)

        this.attrGroupsArrayControl = this.commonForm.get('attributeGroups') as FormArray

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

    goBack() {
        this.location.back();
    }

    setDescAttachmentVos(data: any) {
        this.commonForm.patchValue({
            descAttachmentVos: data
        })
    }

}
