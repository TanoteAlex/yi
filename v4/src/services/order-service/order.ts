import {HttpClient, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {HttpServiceProvider} from "../http-service/http-service";
import {AlertController} from "@ionic/angular";
import {AppConfig} from "../../app/app.config";

/*
  Generated class for the OrderProvider provider.

  See https://angular.io/guide/dependency-injection for more info on providers
  and Angular DI.
*/
@Injectable()
export class OrderProvider extends HttpServiceProvider {

    constructor(public appConfig: AppConfig, public http: HttpClient, public alertCtrl: AlertController) {
        super(appConfig, http, alertCtrl, "order");
    }

    /**
     * 订单部分
     */
    getOrder(orderId) {
        const params = new HttpParams().set("orderId", orderId);
        return this.get("getOrder", params);
    }

    /*提交订单*/
    confirmOrder(orderBo) {
        return this.post('confirmOrder', orderBo);
    }

    /*支付订单*/
    submitOrder(saleOrderBo) {
        return this.post("submitOrder", saleOrderBo);
    }

    /*订单列表分页查询*/
    queryOrder(order) {
        return this.post("queryOrder", order);
    }

    name(nameid) {
        return this.patch("queryOrder", {id: nameid})
    }

    /*取消订单*/
    cancelOrder(orderId) {
        const params = new HttpParams().set("orderId", orderId);
        return this.get("cancelOrder", params);
    }

    /*删除订单*/
    delOrder(orderId) {
        const params = new HttpParams().set("orderId", orderId);
        return this.get("removeOrder", params);
    }

    /*待支付，待收货，待评价数量*/
    getOrdersNum(memberId) {
        const params = new HttpParams().set("memberId", memberId);
        return this.get("getOrderState", params);
    }

    /*确认收货*/
    confirmReceive(orderId) {
        const params = new HttpParams().set("orderId", orderId);
        return this.get("confirmReceipt", params);
    }

    /*退货申请*/
    applyReturnOrder(returnOrderBo) {
        return this.post("applyReturn", returnOrderBo)
    }

    /*查看退货申请*/
    returnOrder(returnOrderId) {
        const params = new HttpParams().set("returnOrderId", returnOrderId);
        return this.get("speedOfProgress", params)
    }

    /*退货进度查看*/
    auditProgress(returnOrderId) {
        const params = new HttpParams().set("returnOrderId", returnOrderId);
        return this.get("returnSchedule", params)
    }

    /*评论商品*/
    evaluationOrder(commodityCommentListBo) {
        return this.post("commentOrder", commodityCommentListBo)
    }

    /*退货原因选择*/
    getReturnReason() {
        return this.get("causeOfReturn");
    }

    /**0
     * 快递100Api
     * @param name  快递公司编码
     * @param no    快递单号
     * @returns {Promise<any>}
     * {
     *  "message": "ok",
     *  "nu": "3373360521434",      //订单号
     *  "ischeck": "0",
     *  "condition": "00",
     *  "com": "shentong",          //快递公司编码
     *  "status": "200",
     *  "state": "0",               //下面说
     *  "data": [{
     *      "time": "2018-08-29 16:09:00",
     *      "ftime": "2018-08-29 16:09:00",
     *      "context": "【深圳福田西丽大学城】已收入",
     *      "location": ""
     *  },]
     * }
     * state：
     *      0：在途，即货物处于运输过程中；
     *      1：揽件，货物已由快递公司揽收并且产生了第一条跟踪信息；
     *      2：疑难，货物寄送过程出了问题；
     *      3：签收，收件人已签收；
     *      4：退签，即货物由于用户拒签、超区等原因退回，而且发件人已经签收；
     *      5：派件，即快递正在进行同城派件；
     *      6：退回，货物正处于退回发件人的途中；
     *
     * https://m.kuaidi100.com/index_all.html?type=shentong&postid=3373360521434#result     html
     * https://m.kuaidi100.com/query?type=shentong&postid=3373360521434                     json
     * https://www.kuaidi100.com/openapi/mobileapi.shtml
     */

    getExpressDelivery(name, no) {
        //todo waiting databaseData
        return this.http.get(this.appConfig.baseURL + "express/query" + '?type=' + name + '&postid=' + no + '#result').toPromise().catch(this.handleError);
    }

    /**
     * 活动部分
     * */

    //添加订单，拼团开团，不保存数据
    confirmGroupOrder(orderBo) {
        return this.post('confirmGroupOrder', orderBo);
    }

    //拼团提交订单
    submitGroupOrder(orderBo) {
        return this.post('submitGroupOrder', orderBo)
    }

    //查看该订单的返券之类的信息
    getRewardByPaid(orderIds) {
        const params = new HttpParams().set("orderIds", orderIds);
        return this.get("getRewardByPaid", params)
    }

}
