import {Component, OnInit, ViewChild} from '@angular/core';
import {MemberProvider} from '../../services/member-service/member';
import {REFRESH_CHOOSECONSIGNEE} from '../Constants';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ShippingAddressBo} from '../../domain/bo/shipping-address-bo.model';
import {NativeProvider} from '../../services/native-service/native';
import {Events, ModalController, NavController, NavParams} from '@ionic/angular';
import {ShippingAddress} from '../../domain/original/shipping-address.model';
import {ActivatedRoute} from '@angular/router';
import {DistrictsComponent} from '../components/districts/districts.component';
import {inputHandle} from '../../util/bug-util';

@Component({
    selector: 'app-consignee-modal',
    templateUrl: './consignee-modal.page.html',
    styleUrls: ['./consignee-modal.page.scss'],
})
export class ConsigneeModalPage implements OnInit {

    commonForm: FormGroup;
    consignee: ShippingAddress;

    multiCity;

    @ViewChild(DistrictsComponent) childDistricts: DistrictsComponent;

    /*默认为添加add，编辑为edit*/
    type = 'add';

    constructor(public events: Events, public navParam: NavParams, public ModalCtrl: ModalController, public router: ActivatedRoute, public nativeProvider: NativeProvider, public fb: FormBuilder, public memberProvider: MemberProvider, public navCtrl: NavController) {
        if (this.navParam.data.address) {
            this.consignee = this.navParam.data.address;
            this.multiCity = this.consignee.province + ' ' + this.consignee.city + ' ' + this.consignee.district;
            this.type = 'edit';
        }
    }

    ngOnInit(): void {
        this.commonForm = this.fb.group({
            fullName: [''],
            phone: ['', Validators.compose([Validators.pattern('^1[0-9]{10}$'),
                Validators.required, Validators.minLength(1), Validators.maxLength(32)])],
            zipCode: ['', Validators.compose([Validators.maxLength(6)])],
            province: [''],
            city: [''],
            district: [''],
            address: ['', Validators.compose([Validators.required])],
            defaulted: [0],
            member: [{id: MemberProvider.getLoginMember().id}],
            id: []
        });

        if (this.consignee) {
            this.commonForm.patchValue({
                fullName: this.consignee.fullName,
                phone: this.consignee.phone,
                zipCode: this.consignee.zipCode,
                address: this.consignee.address,
                defaulted: this.consignee.defaulted,
                id: this.consignee.id,
                province: this.consignee.province,
                city: this.consignee.city,
                district: this.consignee.district,
            });
        }

        // this.commonForm.patchValue({
        //     member: {id: MemberProvider.getLoginMember().id},
        // })

        inputHandle();
    }

    /*防止重复提交*/
    submit: boolean = false;

    goChooseConsignee() {
        if (!this.childDistricts.districtCode) {
            this.nativeProvider.showToastFormI4('请填写完整地址');
            return;
        }
        this.commonForm.patchValue({
            province: this.childDistricts.provinceCode,
            city: this.childDistricts.cityCode,
            district: this.childDistricts.districtCode,
        });

        let consigneeBo: ShippingAddressBo = this.commonForm.value;
        consigneeBo.defaulted = this.commonForm.value.defaulted == true ? 1 : 0;
        if (this.submit) {
            this.nativeProvider.showToastFormI4('正在提交...');
            return;
        } else {
            if (this.type == 'edit') {
                this.memberProvider.editAddress(consigneeBo).then(e => {
                    if (e.result == 'SUCCESS') {
                        this.ModalCtrl.dismiss(e.data);
                        this.events.publish(REFRESH_CHOOSECONSIGNEE);
                    } else {
                        this.nativeProvider.showToastFormI4(e.message);
                    }
                }, err => {
                    this.nativeProvider.showToastFormI4(err.message);
                });
            } else if (this.type == 'add') {
                this.memberProvider.addAddress(consigneeBo).then(e => {
                    if (e.result == 'SUCCESS') {
                        this.ModalCtrl.dismiss(e.data);
                        this.events.publish(REFRESH_CHOOSECONSIGNEE);
                    } else {
                        this.nativeProvider.showToastFormI4(e.message);
                    }
                }, err => {
                    this.nativeProvider.showToastFormI4(err.message);
                });
            }
        }
    }

    rexPhone() {
        let phoneRex = /^1[0-9]{10}$/;
        if (!phoneRex.test(this.commonForm.value.phone) && this.commonForm.value.phone != '') {
            return '请输入正确的手机号';
        }
    }

    rexZipCode() {
        if (this.commonForm.value.zipCode.length != 6 && this.commonForm.value.zipCode.length != 0) {
            return '请输入正确的邮政编码';
        }
    }

    changMultiCity(data) {
        this.multiCity = data;
    }
}
