import {FormArray, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {CommodityService} from "../../../services/commodity.service";
import {Commodity} from "../../../models/original/commodity.model";
import {ViewChild} from "@angular/core";

export class FormAttrGroup {

    show: boolean = false;  //货品表格显示状态

    attrGroupTip = []

    attrGroupsArrayControl: FormArray;
    /**
     * 货品表单集合
     * @type {any[]}
     */
    dynamicAttribute = []
    /**
     * 递归属性值临时集合
     * @type {string}
     */
    dynamicArray = ""

    //价格批量插入
    supplyPrices;
    originalPrices;
    currentPrices;
    stockQuantitys;

    commonForm: FormGroup;


    isngOnInit = true;  //判断货品图片是否值行ngOnInit(编辑商品是不执行、添加执行)

    attrGs = []


    constructor(public commodityService: CommodityService, public fb: FormBuilder) {
    }


    setGroupTip(choose) {
        this.commodityService.getAttributeGroupsByCategoryId(choose.id).subscribe(data => {
            this.attrGroupTip = data.data.map(e => {
                return { id: e.id, groupName: e.groupName, attributes: e.attributes }
            })
            let attributeGroups = this.commonForm.get("attributeGroups");
            this.attrGroup.setSelect(this.attrGroupTip, attributeGroups.value)
            //console.log(this.attrGroupTip);
        })
    }

    setAttrGroup(data) {
        let attrGsFormArray = this.fb.array([]);
        if (data.length != 0) {
            data.forEach((e, i) => {
                let formGroup = this.fb.group({
                    id: [e.id],
                    groupName: [e.groupName, [Validators.required]],
                    imgPath: [e.imgPath],
                    showMode: [e.showMode],
                    next: i == data.length - 1 ? 9999 : i + 1
                });
                let attrVsFormArray = this.fb.array([]);
                if (e.attributes.length != 0) {
                    e.attributes.forEach(e1 => {
                        attrVsFormArray.push(this.fb.group({
                            id: [e1.id],
                            imgPath: [e1.imgPath],
                            attrName: [e1.attrName, [Validators.required]],
                            attrValue: [e1.attrValue, [Validators.required]],
                        }))
                    })
                }
                formGroup.setControl('attributes', attrVsFormArray)
                attrGsFormArray.push(formGroup)
            })
            this.commonForm.setControl('attributeGroups', attrGsFormArray)
            this.addTrue()

        }

        // console.log('attrGsFormArray', attrGsFormArray);

    }


    addTrue() {
        let tmpOriginalProduct = JSON.parse(JSON.stringify(this.commonForm.get('products').value))
        this.dynamicArray = ""
        this.createDy("", 0)
        let array = this.dynamicArray.split(";")
        array.pop();//去除最后一个空值
        this.dynamicAttribute = []
        this.dynamicAttribute = array.map(e => {
            return e.split(",")
        })
        // console.log(this.dynamicAttribute);
        this.createProductFormControl(tmpOriginalProduct, false)
    }

    createDy(e1, i) {
        let attr = this.commonForm.get("attributeGroups") as FormArray;
        if (attr.value.length != i) {
            attr.value[i].attributes.forEach((e, index) => {
                if (attr.value[i].next != 0 && attr.value[i].next != 9999 && attr.value.length > attr.value[i].next) {
                    this.createDy(e1 + e.attrValue + ",", attr.value[i].next)
                } else {
                    this.dynamicArray += e1 + e.attrValue + ";"
                }
            })
        }
    }

    patchAttrGroup(data, control, i) {
        if (data != '') {
            // this.attr
            let filter = this.attrGroupTip.filter(e => e.groupName == data);
            if (filter.length != 0) {
                control.patchValue({
                    groupName: filter[0].groupName,
                    id: filter[0].id
                })
                control.attrValues = filter[0].attributes
            } else {
                control.patchValue({
                    groupName: data,
                    id: 0
                })
                control.attrValues = []
                let attrVs = this.attrGroupsArrayControl.get(i + '.attributes') as FormArray
                attrVs.controls.forEach(e => {
                    e.patchValue({
                        attrValue: e.value.attrValue,
                        id: 0,
                    })
                })
            }
        }
    }


    @ViewChild('attrGroup') attrGroup

    setFormAttrGroup(commodity: Commodity) {
        //构建展示的属性组属性值表单
        /*    console.log(commodity)
            alert(commodity.products.length )*/
        if (commodity.products.length != 0) {
            let attrGs = []
            commodity.products.forEach(e3 => {
                e3.attributes.forEach(e4 => {
                    let filter = attrGs.filter(e5 => e5.attrName == e4.attrName);
                    if (filter.length > 0) {
                        let filter1 = filter[0].attrValues.filter(e6 => e6.attrValue == e4.attrValue);
                        if (filter1.length == 0) {
                            filter[0].attrValues.push({ imgPath: e4.imgPath, id: e4.id, attrValue: e4.attrValue })
                        }
                    } else {
                        let attrValues = []
                        attrValues.push({ attrValue: e4.attrValue, id: e4.id })
                        attrGs.push({ showMode: e4.showMode, imgPath: e4.imgPath, attrName: e4.attrName, attrValues: attrValues })
                    }
                })
            })
            attrGs.forEach((e5, i) => {
                let attrGFormArray = this.commonForm.get('attributeGroups') as FormArray
                let attrVFormArray = this.fb.array([]) as FormArray
                e5.attrValues.forEach(e6 => {
                    attrVFormArray.push(this.fb.group({
                        id: e6.id,
                        attrValue: e6.attrValue,
                        imgPath: e6.imgPath
                    }))
                })
                attrGFormArray.push(this.fb.group({
                    id: [e5.attributeGroupId],
                    groupName: [e5.attrName],
                    imgPath: e5.imgPath,
                    showMode: e5.showMode,
                    next: i == attrGs.length - 1 ? 9999 : i + 1,
                    attributes: attrVFormArray
                }))
                this.commonForm.setControl('attributeGroups', attrGFormArray)
            })
        }
        //通过分类id 查询
        this.commodityService.getAttributeGroupsByCategoryId(this.commonForm.value.category.id).subscribe(data => {
            this.attrGroupTip = data.data.map(e => {
                return { id: e.id, groupName: e.groupName, attributes: e.attributes }
            })
            this.attrGroupsArrayControl = this.commonForm.get("attributeGroups") as FormArray
            this.attrGroupsArrayControl.controls.forEach((e: any, index) => {
                let filter = this.attrGroupTip.filter(e1 => e1.id == e.value.id);
                if (filter.length != 0) {
                    e.attrValues = filter[0].attributes
                }
            })
            this.attrGroupsArrayControl.controls.forEach((e1, index) => {
                this.attrGroupTip.forEach(e2 => {
                    if (e1.value.groupName == e2.groupName) {
                        this.patchAttrGroup(e1.value.groupName, e1, index)
                    }
                })
            })
            this.attrGroup.setSelect(this.attrGroupTip, this.attrGroupsArrayControl.value)

            this.createProductFormControl(commodity.products, true);

            //console.log(this.attrGroupsArrayControl);
        })
        //console.log(this.commonForm);
    }

    /**
     * 创建货品表格表单
     * @param {Commodity} commodity
     */
    createProductFormControl(originalProducts, isEdit?) {
        this.isngOnInit = false
        // console.log('originalProducts :', originalProducts);
        //生成货品表格
        this.attrGroupsArrayControl = this.commonForm.get("attributeGroups") as FormArray
        //获取生成表格属性值组表单  product表单
        this.dynamicArray = ""
        this.createDy("", 0)
        let array = this.dynamicArray.split(";")
        array.pop();//去除最后一个空值
        this.dynamicAttribute = array.map(e => {
            return e.split(",")
        })


        let formArrayAttrValues: FormArray = this.fb.array([]) as FormArray;
        //对应product
        let tempProducts: any = []
        this.dynamicAttribute.forEach((e5, i5) => {
            let isAdd = true;
            //判断用 临时product
            if (isEdit) {
                originalProducts.forEach((e2, i2) => {
                    let count = 0
                    let length1 = e2.attributes.length;
                    e2.attributes.forEach((e3, i3) => {
                        e5.forEach((e1, i1) => {
                            if (e3.attrValue == e1) {
                                count++;
                                //将属性值相同的所有product添加到 临时product
                                //tempProducts.push(...e3.products)
                                if (count == length1) {
                                    isAdd = false
                                    tempProducts.push({ products: e2, attr: e5, dyIndex: i5 })
                                }
                            }
                        })
                    })
                })
            }

            if (isAdd) {
                let addAttributes = e5.map(e2 => {
                    // console.log('e5', e5);
                    let valueId = 0
                    let attrG = this.commonForm.value.attributeGroups.filter(e3 => e3.attributes.filter(e4 => {
                        valueId = e4.id
                        return e4.attrValue == e2
                    }).length != 0)[0];
                    // console.log('attrG', attrG);
                    return { attrName: attrG.groupName, attrValue: e2, id: valueId, attributeGroupId: 0 }
                })
                // console.log('addAttributes', addAttributes);
                tempProducts.push({
                    products: {
                        id: 0,
                        supplyPrice: 0,
                        originalPrice: 0,
                        currentPrice: 0,
                        productImgPath: '',
                        stockQuantity: 0,
                        attrNames: e5,
                        attributes: addAttributes
                    }, attr: e5, dyIndex: i5
                })
            }
        })
        // console.log('finish tempProducts:', tempProducts);

        if (tempProducts.length > 0) {
            //构建product表单数据
            tempProducts.forEach(e => {
                // console.log('create product', e);
                formArrayAttrValues.push(this.fb.group({
                    id: [e.products.id],
                    supplyPrice: [e.products.supplyPrice ? e.products.supplyPrice : 0],
                    originalPrice: [e.products.originalPrice ? e.products.originalPrice : 0],
                    currentPrice: [e.products.currentPrice ? e.products.currentPrice : 0],
                    productImgPath: [e.products.productImgPath ? e.products.productImgPath : ''],
                    stockQuantity: e.products.stockQuantity ? e.products.stockQuantity : 0,
                    attrNames: [e.attrNames],
                    attributes: [e.products.attributes]
                }))
            })
            this.show = true
            this.commonForm.setControl('products', formArrayAttrValues);
            // console.log('formArrayAttrValues', formArrayAttrValues);
            // console.log(formArrayAttrValues);
        }
        setTimeout(() => {
            this.isngOnInit = true
        }, 1000)
    }

    selectPrice() {
        this.supplyPrices = document.getElementsByName("supplyPrice");
        this.originalPrices = document.getElementsByName("originalPrice");
        this.currentPrices = document.getElementsByName("currentPrice");
        this.stockQuantitys = document.getElementsByName("stockQuantity");
    }

    bulkInsert() {
        this.selectPrice();
        let formArrayAttrValues: FormArray = this.commonForm.get('products') as FormArray;
        formArrayAttrValues.controls.forEach(e => {
            e.patchValue({
                supplyPrice: this.supplyPrices[0].value,
                originalPrice: this.originalPrices[0].value,
                currentPrice: this.currentPrices[0].value,
                stockQuantity: this.stockQuantitys[0].value,
            })
        })
    }

    reset() {
        this.selectPrice();
        this.supplyPrices[0].value = null;
        this.originalPrices[0].value = null;
        this.currentPrices[0].value = null;
        this.stockQuantitys[0].value = null;
    }


}
