import {Component, OnInit, ViewChild} from '@angular/core';
import {FormArray, FormBuilder} from "@angular/forms";
import {OrderProvider} from "../../services/order-service/order";
import {AlertController, ModalController, NavController} from "@ionic/angular";
import {NativeProvider} from "../../services/native-service/native";
import {StarComponent} from "../components/star/star.component";
import {MemberProvider} from "../../services/member-service/member";
import {Product} from "../../domain/original/product.model";
import {ActivatedRoute} from "@angular/router";
import {FileServiceProvider} from "../../services/file-service/file-service";
import {inputHandle, scrollToZero} from "../../util/bug-util";
import {RewardAlertModalPage} from "../rule-info/reward-alert-modal/reward-alert-modal.page";

@Component({
    selector: 'app-evaluation',
    templateUrl: './evaluation.page.html',
    styleUrls: ['./evaluation.page.scss'],
})
export class EvaluationPage implements OnInit {
    /*获取子组件评价星级*/
    @ViewChild(StarComponent) childStar: StarComponent;

    products: Product[] = [];
    evaluationForm: FormArray;

    evaImg = "../../assets/app_icon/basic/评论添加图片icon@3x.png";

    constructor(public fb: FormBuilder, public orderProvider: OrderProvider, public navCtrl: NavController,public alertCtrl: AlertController,
                public nativeProvider: NativeProvider, public route: ActivatedRoute, public fileServiceProvider: FileServiceProvider,public modalCtrl: ModalController,) {
    }

    ionViewWillEnter() {
        this.orderProvider.getOrder(this.route.params["value"].orderId).then(e => {
            if (e.result == "SUCCESS") {
                this.products = e.data.saleOrderItems.map(e1 => {
                    return {
                        commodityId: e1.commodityId,
                        imgPath: e1.product.productImgPath,
                        productName: e1.product.productName,
                        orderId: e.data.id
                    }
                });

                this.attach();

            } else {
                this.nativeProvider.showToastFormI4(e.message);
            }
        }, err => this.nativeProvider.showToastFormI4(err.message))
    }

    ngOnInit() {
        inputHandle();
    }

    attach() {
        this.evaluationForm = this.fb.array([]);
        this.products.forEach(e => {
            this.evaluationForm.push(this.fb.group({
                member: {id: MemberProvider.getLoginMember().id},
                saleOrder:{id:e.orderId} ,
                imgPath: '',
                commodity: {id:e.commodityId},
                commentStar: 0,
                commentContent: '',
            }))
        })
    }

    star(event, i: string) {
        this.evaluationForm.get(i + "").patchValue({
            commentStar: event
        })
    }

    changeAvatar(event, i: string) {
        this.nativeProvider.getPhoto().subscribe(data => {
            // console.log(event.target.src);
            // alert(JSON.stringify(data));
            if (this.nativeProvider.isIos()) {
                this.fileServiceProvider.uploadFileIOS(data).then(data => {
                    if (data.result == 'SUCCESS') {
                        event.target.src = data.data[0].url;
                        this.evaluationForm.get(i + "").patchValue({
                            img: data.data[0].url
                        });
                    } else {
                        this.nativeProvider.showToastFormI4("上传失败");
                    }
                })
            } else if (this.nativeProvider.isAndroid()) {
                this.fileServiceProvider.uploadFile(data).then(data => {
                    if (data.result == 'SUCCESS') {
                        event.target.src = data.data[0].url;
                        this.evaluationForm.get(i + "").patchValue({
                            img: data.data[0].url
                        });
                    } else {
                        this.nativeProvider.showToastFormI4("上传失败");
                    }
                })
            }
        })
    }

    blur(){
        scrollToZero();
    }

    comment() {
        if(this.evaluationForm.value.some(e => {return e.commentContent == ""})){
            this.nativeProvider.showToastFormI4("有内容未评论，请填写完整~");
            return
        }
        if(this.evaluationForm.value.some( e => {return e.commentStar == 0})){
            this.nativeProvider.showToastFormI4("有星级未评价，请填写完整~");
            return
        }
        this.orderProvider.evaluationOrder(this.evaluationForm.value).then(e => {
            if (e.result == "SUCCESS") {
                this.nativeProvider.showToastFormI4("评论成功", () => {
                    if (e.data && e.data.length){
                        this.presentAlert(e.data)
                    } else{
                        this.navCtrl.goBack();
                    }
                });
            }
        }, err => {
            this.nativeProvider.showToastFormI4(err.message);
        });
    }

    //提示相应的分步发放的代金券
    async presentAlert(data) {
        const modal = await this.modalCtrl.create({
                component: RewardAlertModalPage,
                componentProps: {data: data}
            }
        );
        modal.onWillDismiss().then(() => this.navCtrl.goBack());
        await modal.present();
    }
}
