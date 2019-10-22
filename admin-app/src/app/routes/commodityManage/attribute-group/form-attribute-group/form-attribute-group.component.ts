import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewChild, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {NzMessageService} from 'ng-zorro-antd';
import {FormArray, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AttributeGroup} from '../../../models/original/attribute-group.model';
import {ObjectUtils} from '@shared/utils/ObjectUtils';
import {CommodityService} from "../../../services/commodity.service";
import {CategoryTreeSelectComponent} from "../../../components/category-tree-select/category-tree-select.component";

@Component({
    selector: 'form-attribute-group',
    templateUrl: './form-attribute-group.component.html',
    styleUrls: ['./form-attribute-group.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class FormAttributeGroupComponent implements OnInit, OnChanges {
    attrArryForm: FormArray;
    commonForm: FormGroup;

    @Input() title: string = '表单';

    @Input() attributeGroup: AttributeGroup = new AttributeGroup();

    @ViewChild('categoryTreeSelect') categoryTreeSelect: CategoryTreeSelectComponent

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);
    attrGroupTip = []
    isngOnInit = true

    formErrors = {
        category: [
            {
                name: 'required',
                msg: '不可为空',
            },
        ],
        groupName: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大64位长度',
            }
        ],
        remark: [
            {
                name: 'maxlength',
                msg: '最大127位长度',
            }

        ],
        showMode: [],
    };

    constructor(private fb: FormBuilder, private location: Location, public msgSrv: NzMessageService, public commodityService: CommodityService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.attributeGroup !== undefined && value.attributeGroup.currentValue !== undefined) {
            this.setBuildFormValue(this.attributeGroup);
        }
    }

    ngOnInit() {

    }

    setCommodityCategory(choose) {
        this.commonForm.patchValue({
            category: choose
        })
    }

    deleteName(i) {
        let attributes: FormArray = this.commonForm.get('attributes') as FormArray;
        if (attributes.length == 1) {
            this.msgSrv.warning('属性值不能为空');
            return;
        }
        attributes.removeAt(i)
    }

    addNewTemplate() {
        let attrArryForm: FormArray = this.commonForm.get('attributes') as FormArray;
        attrArryForm.push(this.fb.group({
            attrValue: [''],
            imgPath: ['']
        }))
    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            attributes: this.fb.array([this.fb.group({
                id: [0],
                attrValue: [''],
                imgPath: ['']
            })]),
            category: [null, Validators.compose([Validators.required, Validators.maxLength(10)])],
            groupName: [null, Validators.compose([Validators.required, Validators.maxLength(64)])],
            showMode: [1, Validators.compose([Validators.required, Validators.min(1), Validators.max(3)])],
            remark: [null, Validators.compose([Validators.maxLength(127)])],
        });
        this.attrArryForm = this.commonForm.get('attributes') as FormArray
    }

    setBuildFormValue(attributeGroup: AttributeGroup) {
        this.isngOnInit = false
        let attributes: FormArray = this.commonForm.get('attributes') as FormArray;
        attributeGroup.attributes.forEach((e, index) => {
            let value = { id: e.id, attrValue: e.attrValue, imgPath: e.imgPath }
            if (index == 0) {
                attributes.get('0').patchValue(value)
            } else {
                attributes.push(this.fb.group({
                    id: [e.id],
                    attrValue: [e.attrValue],
                    imgPath: [e.imgPath]
                }))
            }
        })
        this.commonForm.patchValue({
            category: attributeGroup.category,
            groupName: attributeGroup.groupName,
            remark: attributeGroup.remark,
            showMode: attributeGroup.showMode,
        });
        this.categoryTreeSelect.select(this.commonForm.value.category.id)
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
        let attributes: FormArray = this.commonForm.get('attributes') as FormArray;
        for (let i = 0; i < attributes.length; i++) {
            if (attributes.at(i).value.attrValue == null || attributes.at(i).value.attrValue == "") {
                this.msgSrv.warning('请填写属性值');
                return;
            }
        }
        if (this.attributeGroup) {
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

    getProductImgPath(event, index) {
        if (event.length != 0) {
            this.commonForm.get('attributes').get(index + '').patchValue({
                imgPath: event[0].response.data[0].url
            })
        } else {
            this.commonForm.get('attributes').get(index + '').patchValue({
                imgPath: null
            })
        }
    }

}
