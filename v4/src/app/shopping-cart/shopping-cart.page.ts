import {Component} from '@angular/core';
import {Events, NavController} from "@ionic/angular";
import {ShoppingCartProduct} from "../../domain/original/shopping-cart-product.model";
import {MemberProvider} from "../../services/member-service/member";
import {OrderProvider} from "../../services/order-service/order";
import {CommodityProvider} from "../../services/commodity-service/commodity";
import {NativeProvider} from "../../services/native-service/native";
import {REFRESH_SHOPPINGCART} from "../Constants";
import {BannerProvider} from "../../services/banner-service/banner";
import {ActivatedRoute} from "@angular/router";
import {CartService} from "../../services/member-service/cart.service";

@Component({
    selector: 'app-shopping-cart',
    templateUrl: 'shopping-cart.page.html',
    styleUrls: ['shopping-cart.page.scss']
})
export class ShoppingCartPage {
    isEdit = false;
    isLoading = true;
    shoppingCartsVos: ShoppingCartProduct[] = [];
    totalPrice = "0.00";
    totalDiscount = "0.00";

    advertisingCommodity;
    buyNowCommodityId;

    constructor(public commodityProvider: CommodityProvider, public orderProvider: OrderProvider, public nativeProvider: NativeProvider,
                public MemberProvider: MemberProvider, public navCtrl: NavController, public events: Events, public bannerProvider: BannerProvider,
                public route: ActivatedRoute, public cartProvider: CartService) {
        this.events.subscribe(REFRESH_SHOPPINGCART, () => {
            // this.getData()
        });
        this.buyNowCommodityId = this.route.params["value"].buyNowCommodityId;
        this.getAdvertisingCommodityData();
    }

    ionViewWillEnter() {
        this.getData();
    }

    getData() {
        this.isSelectAll = false;
        this.isEdit = false;
        this.isLoading = true;
        this.totalPrice = "0.00";
        this.totalDiscount = "0.00";
        this.shoppingCartsVos = [];

        if (MemberProvider.isLogin()) {
            this.cartProvider.getShoppingCart(MemberProvider.getLoginMember().id).then(data => {
                if (data.result == "SUCCESS") {
                    this.shoppingCartsVos = data.data;
                    this.shoppingCartsVos.forEach(e => {
                        e.product.imgPath = e.product.productImgPath;
                        e.select = e.product.id == this.buyNowCommodityId   //立即购买，跳转到购物车自动已选择
                    });
                    this.count();
                    this.isLoading = false;
                } else {
                    this.nativeProvider.showToastFormI4(data.message);
                    this.isLoading = false;
                }
            }, err => {
                this.nativeProvider.showToastFormI4(err.message);
                this.isLoading = false;
            });
        } else {
            this.isLoading = false;
        }
    }

    /*广告*/
    getAdvertisingCommodityData() {
        this.bannerProvider.getAdvertisingCommodity(4).then(data => {
            if (data.result == "SUCCESS") {
                this.advertisingCommodity = data.data;
            } else {
                this.nativeProvider.showToastFormI4(data.message)
            }
        }, err => this.nativeProvider.showToastFormI4(err.message));
    }

    goOrderPage() {
        if (this.shoppingCartsVos.filter(e => e.select).length > 0) {
            let shoppingCart = {
                member: {id: MemberProvider.getLoginMember().id,},
                cartVos: this.shoppingCartsVos.filter(e => e.select).map(e => {
                    return {
                        id: e.id,
                        product: {id: e.product.id},
                        quantity: e.quantity
                    };
                })
            };
            this.navCtrl.navigateForward(["WriteOrderPage", {shoppingCart: JSON.stringify(shoppingCart)}], false);
        } else {
            this.nativeProvider.showToastFormI4("请选择商品");
        }
    }

    /*全选设置*/
    isSelectAll: boolean = false;

    selectAll() {
        if (this.shoppingCartsVos.some(e => !e.select)) {
            this.shoppingCartsVos.forEach(e => e.select = true);
        } else {
            this.shoppingCartsVos.forEach(e => e.select = false);
        }
        this.count()
    }

    goCommodity(product) {
        if (product.state){
            return
        } else {
            this.navCtrl.navigateForward(["CommodityPage", {"id": product.commodityId}]);
        }
    }

    reduce(i,event) {
        if (this.shoppingCartsVos[i].quantity == 1) {
            return
        }
        this.shoppingCartsVos[i].quantity--;
        this.count();
        this.changQuantity(this.shoppingCartsVos[i]);
        event.stopPropagation();
    }

    add(i, event) {
        this.shoppingCartsVos[i].quantity++;
        this.count();
        this.changQuantity(this.shoppingCartsVos[i]);
        event.stopPropagation();
    }

    /*商品数量变化提交,传入需要改变的的对象*/
    changQuantity(product) {
        this.cartProvider.changQuantity(MemberProvider.getLoginMember().id, product.id, product.quantity);
    }

    deleteProduct() {
        let carts = this.shoppingCartsVos.filter(e => !e.select);
        let deleteProductIds = this.shoppingCartsVos.filter(e => e.select).map(e => e.id);
        this.shoppingCartsVos = carts;
        deleteProductIds.forEach((e, index) => {
            this.cartProvider.deleteCommodity(MemberProvider.getLoginMember().id, e).then(e1 => {
                if (e1.result == "SUCCESS") {
                    if (deleteProductIds.length == index + 1) {
                        this.nativeProvider.showToastFormI4("删除成功");
                    }
                } else {
                    this.nativeProvider.showToastFormI4("删除失败，请稍后再试");
                    this.ionViewWillEnter();
                }
            }, err => this.nativeProvider.showToastFormI4(err.message))
        });
    }

    goHome() {
        this.navCtrl.navigateForward('/app/tabs/home', false,);
    }

    /*购物车商品金额，优惠金额计算,*/
    count() {
        setTimeout(() => {
            this.isSelectAll = this.shoppingCartsVos.every(e => {
                return e.select
            });

            if (this.shoppingCartsVos.filter(e => e.select).length > 0) {
                this.totalPrice = this.shoppingCartsVos.filter(e => e.select).map(e => {
                    return e.product.vipPrice * e.quantity
                }).reduce((a, b) => {
                    return a + b;
                }).toFixed(2);
                // this.totalDiscount = this.shoppingCartsVos.filter(e=>e.select).map(e=>{return parseFloat(e.discount)}).reduce((a,b) => {return a+b;});
            } else {
                this.totalPrice = "0.00";
                this.totalDiscount = "0.00";
            }
        }, 1)
    }
}
