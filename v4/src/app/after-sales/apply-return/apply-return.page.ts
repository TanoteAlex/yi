import {Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {SaleOrder} from "../../../domain/original/sale-order.model";
import {OrderProvider} from "../../../services/order-service/order";
import {Events, ModalController, NavController} from "@ionic/angular";
import {NativeProvider} from "../../../services/native-service/native";
import {ActivatedRoute} from "@angular/router";
import {MemberProvider} from "../../../services/member-service/member";
import {ReturnOrderBo} from "../../../domain/bo/return-order-bo.model";
import {ReturnReasonModalPage} from "../return-reason-modal/return-reason-modal.page";
import {FileServiceProvider} from "../../../services/file-service/file-service";
import {AfterSaleService} from "../../../services/order-service/after-sale.service";
import {inputHandle, scrollToZero} from "../../../util/bug-util";
import {AppConfig} from "../../app.config";


@Component({
    selector: 'app-apply-return',
    templateUrl: './apply-return.page.html',
    styleUrls: ['./apply-return.page.scss'],
})
export class ApplyReturnPage implements OnInit {

    commonForm: FormGroup;
    order: SaleOrder;

    simpleColumns;

    reason = [];

    voucherPhoto = "../../assets/app_icon/basic/评论添加图片icon@3x.png";

    constructor(public orderProvider: OrderProvider, public fb: FormBuilder, public navCtrl: NavController, public modalCtrl: ModalController,
                public nativeProvider: NativeProvider, public route: ActivatedRoute, public fileServiceProvider: FileServiceProvider,
                public afterSaleProvider: AfterSaleService, public appConfig: AppConfig) {
    }

    ngOnInit(): void {
        this.commonForm = this.fb.group({
            attachmentVos: [[]],
            saleOrder: [],
            member: [],
            afterSaleType: ["", Validators.compose([Validators.required, Validators.minLength(1)])],
            afterSaleReason: ["", Validators.compose([Validators.required, Validators.minLength(1)])],
            problemDesc: ["", Validators.compose([Validators.required, Validators.minLength(1)])],
            contact: ["", Validators.required],
            contactPhone: ["", Validators.compose([Validators.pattern("^1[0-9]{10}$"), Validators.required,
                Validators.minLength(1), Validators.maxLength(32)])],
        });

        inputHandle();
    }

    ionViewWillEnter() {
        this.orderProvider.getOrder(this.route.params["value"].orderId).then(e => {
            if (e.result == "SUCCESS") {
                this.order = e.data;
                this.attachData();
            } else {
                this.nativeProvider.showToastFormI4(e.message);
            }
        }, err => this.nativeProvider.showToastFormI4(err.message))
    }

    attachData() {
        this.commonForm.patchValue({
            saleOrder: {id: this.order.id,},
            member: {id: MemberProvider.getLoginMember().id},
            contact: this.order.consignee,
            contactPhone: this.order.consigneePhone,
        })
    }

    async openReturnType() {
        const modal = await this.modalCtrl.create({
            component: ReturnReasonModalPage,
            componentProps: {afterSaleType:0}
        });
        await modal.present();
        await modal.onDidDismiss().then(data => {
            if (data.data) {
                this.commonForm.patchValue({
                    afterSaleType: data.data,
                    afterSaleReason:''
                });
            }
        })
    }

    async openReasonModal() {
        const modal = await this.modalCtrl.create({
            component: ReturnReasonModalPage,
            componentProps: {afterSaleType: this.commonForm.value.afterSaleType}
        });
        await modal.present();
        await modal.onDidDismiss().then(data => {
            if (data.data) {
                this.commonForm.patchValue({
                    afterSaleReason: data.data
                })
            }
        })
    }

    commit() {
        let returnOrderBo: ReturnOrderBo = JSON.parse(JSON.stringify(this.commonForm.value));

        if(returnOrderBo.afterSaleType == '未收到货') returnOrderBo.afterSaleType=1;
        if(returnOrderBo.afterSaleType == '已收到货') returnOrderBo.afterSaleType=2;

        this.afterSaleProvider.applyAfterSale(returnOrderBo).then(e => {
            if (e.result == "SUCCESS") {
                this.nativeProvider.showToastFormI4("申请成功", () => {
                    this.navCtrl.goBack();
                });
            } else {
                this.nativeProvider.showToastFormI4(e.message);
            }
        }, err => {
            this.nativeProvider.showToastFormI4(err.message);
        })
    }

    @ViewChild('avatarImage') avatarImage;
    @ViewChild('avatarCamera') avatarCamera;

    resizeMe(img: any) {
        //压缩的大小
        var max_width = 100;
        var max_height = 100;

        var canvas = document.createElement('canvas');
        var width = img.width;
        var height = img.height;
        if (width > height) {
            if (width > max_width) {
                height = Math.round(height *= max_width / width);
                width = max_width;
            }
        } else {
            if (height > max_height) {
                width = Math.round(width *= max_height / height);
                height = max_height;
            }
        }

        canvas.width = width;
        canvas.height = height;

        var ctx = canvas.getContext("2d");
        ctx.drawImage(img, 0, 0, width, height);
        //压缩率
        return canvas.toDataURL("image/jpeg", 0.92);
    }
    //删除
    deleteImg(index){
        this.commonForm.value.attachmentVos.splice(index, 1);
        this.commonForm.patchValue({
            attachmentVos: this.commonForm.value.attachmentVos
        });
    }

    changeAvatar() {
        if (this.appConfig.isApp) {
            this.changeAvatarforApp();
        }else{
            // console.log(this.avatarImage);
            let _this = this
            document.getElementById('avatarCamera').onchange = function (e: any) {
                let file: any = document.getElementById("avatarCamera");
                // console.log(file);
                let fileReader = new FileReader();
                fileReader.readAsDataURL(file.files[0]);
                fileReader.onload = (e: any) => {
                    // console.log(e.target.result);
                    // _this.personalData.avater = e.target.result;
                    // console.log(e.target.result);
                    let img = new Image()
                    img.src = e.target.result
                    img.onload = e => {
                        var resized = _this.resizeMe(img)
                        // console.log(resized);
                        _this.fileServiceProvider.uploadBrowers(resized).then(data => {
                            if (data.result == 'SUCCESS') {
                                _this.commonForm.value.attachmentVos.push(data.data[0])
                                console.log(_this.commonForm.value.attachmentVos);
                                _this.commonForm.patchValue({
                                    attachmentVos: _this.commonForm.value.attachmentVos
                                });
                            } else {
                                _this.nativeProvider.showToastFormI4("上传失败");
                            }
                        })
                    }
                }
            }
            document.getElementById('avatarImage').onchange = function (e: any) {
                let file: any = document.getElementById("avatarImage");
                // console.log(file);
                let fileReader = new FileReader();
                fileReader.readAsDataURL(file.files[0]);
                fileReader.onload = (e: any) => {
                    // console.log(e.target.result);
                    let img = new Image()
                    img.src = e.target.result
                    img.onload = e => {
                        var resized = _this.resizeMe(img)
                        // console.log(resized);
                        _this.fileServiceProvider.uploadBrowers(resized).then(data => {
                            if (data.result == 'SUCCESS') {
                                _this.commonForm.value.attachmentVos.push(data.data[0])
                                _this.commonForm.patchValue({
                                    attachmentVos: _this.commonForm.value.attachmentVos
                                });
                            } else {
                                _this.nativeProvider.showToastFormI4("上传失败");
                            }
                        })
                    }
                }
            }
            this.nativeProvider.getPhoto().subscribe(data => {
                if (data == 'camera') {
                    this.avatarCamera.nativeElement.click()

                } else if (data == 'image') {
                    this.avatarImage.nativeElement.click()

                }
            })
        }
    }

    changeAvatarforApp() {
        this.nativeProvider.getPhoto().subscribe(data => {
            console.log(data);
            if (this.nativeProvider.isIos()) {
                this.fileServiceProvider.uploadFileIOS(data).then(data => {
                    if (data.result == 'SUCCESS') {
                        this.commonForm.value.attachmentVos.push(data.data[0]);
                        this.commonForm.patchValue({
                            attachmentVos: this.commonForm.value.attachmentVos
                        });
                    } else {
                        this.nativeProvider.showToastFormI4("上传失败");
                    }
                })
            } else if (this.nativeProvider.isAndroid()) {
                // alert(JSON.stringify(data))
                this.fileServiceProvider.uploadFile(data).then(data1 => {
                    data1=JSON.parse(data1);
                    if (data1.result == 'SUCCESS') {
                        this.commonForm.value.attachmentVos.push(data1.data[0]);
                        this.commonForm.patchValue({
                            attachmentVos: this.commonForm.value.attachmentVos
                        });
                    } else {
                        this.nativeProvider.showToastFormI4("上传失败");
                    }
                },err=>{
                    alert(JSON.stringify(err))
                })
            }
        })
    }

    list = ["商品寄回地址将在审核通过后以短信形式告知，或在申请记录中查询。",
        "提交服务单后，售后专员可能与您电话沟通，请保持手机畅通。",
        "退货处理成功后退款金额将原路返回到您的支持账户中"
    ];

    blur(){
        scrollToZero();
    }

}
