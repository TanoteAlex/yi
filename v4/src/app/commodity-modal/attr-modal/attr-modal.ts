import {Component} from '@angular/core';
import {Product} from "../../../domain/original/product.model";
import {CommodityProvider} from "../../../services/commodity-service/commodity";
import {Events, ModalController, NavController, NavParams} from "@ionic/angular";
import {ActivatedRoute} from "@angular/router";
import {MemberProvider} from "../../../services/member-service/member";
import {NativeProvider} from "../../../services/native-service/native";
import {REFRESH_SHOPPINGCART} from "../../Constants";
import {ShareData} from "../../../domain/original/share-data.model";
import {CartService} from "../../../services/member-service/cart.service";

@Component({
    selector: 'page-attr-modal',
    templateUrl: './attr-modal.html',
    styleUrls: ['./attr-modal.scss'],
})
export class AttrModalPage {
    /**
     * 产品拥有的属性组
     */
    attrGroups = [];

    /**
     * 该商品所有的产品
     */
    products: Product[] = [];

    /**
     * 最终选中的产品
     */
    selectProduct: Product;

    /**
     * 商品图片及名称
     */
    imgPath;
    commodityName;

    /**
     * attrs 传回页面经过整理的属性名数组
     * attrs1 所选的属性
     */
    attrs = [];
    attrs1 = [];

    /**
     * 数量
     */
    amount: number = 1;

    /**
     * 所有product的最低价
     */
    lowestPrice: number;

    /**
     * 送礼gift，其余undefined
     */
    buyType: string;

    /**
     * 分享参数(包含分享人id和用户wechat参数
     */
    shareData: ShareData;

    isLogin;
    vipPrice = undefined;

    constructor(public modalCtrl: ModalController, public navParam: NavParams, public commodityProvider: CommodityProvider, public navCtrl: NavController,
                public route: ActivatedRoute, public nativeProvider: NativeProvider, public events: Events, public cartProvider: CartService) {
        this.imgPath = this.navParam.data.imgPath;
        this.commodityName = this.navParam.data.commodityName;
        this.products = this.navParam.data.products;
        this.attrGroups = this.navParam.data.attrGroups;
        this.buyType = this.navParam.data.buyType;
        this.isLogin = MemberProvider.isLogin();
        this.lowestPrice = this.getLowestPrice();

        //点击分享进来
        let href = window.location.href;
        if (href.indexOf('unionId') != -1) {
            this.shareData = {
                preMemberId: href.split('preMemberId=')[1].split('?')[0],
                openId: href.split('openId=')[1].split('&')[0],
                unionId: href.split('unionId=')[1],
            };
        } else {
            this.shareData = null;
        }

        //只有一件货品，默认选择所有属性
        if (this.products.length == 1) {
            this.selectProduct = this.products[0];
            if (this.attrGroups.length) {
                this.attrGroups.forEach(e => {
                    this.select(e, 0)
                });
                return
            }
        }

        //之前进来过，再次进来会通过这个选择原本选择的货品
        this.filterProduct();
        this.getVipPrice()
    }

    /**
     * @param group
     * @param i
     */
    select(group?, i?) {
        if (group) {
            group.selectNo = i;
        }
        this.filterProduct();
    }

    filterProduct() {
        if (!this.attrGroups.some(e => e.selectNo == null)) {
            //group标识所选属性
            this.attrGroups.forEach((e1, i1) => {
                this.attrs1[i1] = e1.attrValues[e1.selectNo];
            });

            //确定所选商品
            this.selectProduct = undefined;

            this.products.forEach(e1 => {
                let num = 0;
                e1.attributes.forEach(e2 => {
                    this.attrs1.forEach(e3 => {
                        if (e2.id == e3.id) {
                            num++;
                        }
                    })
                });
                if (num == this.attrs1.length) {
                    this.selectProduct = e1
                }
                this.getVipPrice()
            });
            console.log(this.selectProduct);
            console.log(this.attrs1);
        }
    }

    reduce() {
        if (this.amount > 1) {
            this.amount--;
        }
    }

    add() {
        this.amount++;
    }

    goBack() {
        if (!this.attrGroups.some(e => e.selectNo == null) && this.selectProduct) {
            this.attrs1.forEach(e => {
                this.attrs.push(e.attrValue)
            });

            let data = {
                attrs: this.attrs,
                num: this.amount,
                id: this.selectProduct.id,
                price: this.selectProduct.currentPrice,
                levelPrices: this.vipPrice
            };
            this.modalCtrl.dismiss(data);
        } else {
            this.modalCtrl.dismiss();
        }
    }

    //防止重复提交
    private submitFlag = false;

    goShoppingCart() {
        if (!MemberProvider.isLogin()) {
            this.modalCtrl.dismiss();
            this.navCtrl.navigateForward(["LoginPage", {shareData: JSON.stringify(this.shareData)}]);
            return
        }
        if (!this.selectProduct) {
            this.nativeProvider.showToastFormI4("所选商品为空");
            return;
        }
        if (this.buyType == 'gift') {
            this.addGift();
        } else {
            this.addCommonCart();
        }
    }

    addCommonCart() {
        this.cartProvider.addShopCart(MemberProvider.getLoginMember().id, this.selectProduct.id, this.amount).then(e => {
            if (e.result == "SUCCESS") {
                let _this = this;
                this.nativeProvider.showToastFormI4("添加成功", () => {
                    _this.events.publish(REFRESH_SHOPPINGCART);
                    //如果是点击立即购买打开模态框的情况下
                    if (_this.navParam.data.writeOrder) {
                        _this.navCtrl.navigateForward("/app/tabs/shoppingCart")
                    } else {
                        _this.goBack();
                    }
                });

            } else {
                this.nativeProvider.showToastFormI4(e.message);
            }
        }, err => {
            this.nativeProvider.showToastFormI4(err.message);
        })
    }

    addGift() {
        this.navCtrl.navigateForward("ConfirmGiftPage")
    }

    /**
     * 计算所有product的最低价
     */
    getLowestPrice() {
        let price: number[] = [];

        this.products.forEach(e1 => {
            price.push(e1.currentPrice);
        });

        return Math.min.apply(null, price);
    }

    /*加入购物车按钮，为选完规格禁用*/
    isConfirm(): boolean {
        return this.attrGroups.some(e => e.selectNo == null)
    }

    getVipPrice() {
        if (!this.isLogin || !(this.selectProduct && this.selectProduct.levelPrices && this.selectProduct.levelPrices.length)) {
            return;
        }
        try {
            let vipPrice: number[] = [];
            if (this.selectProduct) {
                this.vipPrice = this.selectProduct.levelPrices.forEach(e1 =>
                    vipPrice.push(e1.levelPrice)
                )
            } else {
                this.products.forEach(e1 => {
                    e1.levelPrices.forEach(e2 => {
                        vipPrice.push(e2.levelPrice);
                    })
                });
            }
            this.vipPrice = Math.min.apply(null, vipPrice);
        } catch (e) {
            console.log(e);
        }
    }
}
