import {Component} from '@angular/core';
import {NavController} from "@ionic/angular";

@Component({
    selector: 'app-grade-rule',
    templateUrl: './grade-rule.page.html',
    styleUrls: ['./grade-rule.page.scss'],
})
export class GradeRulePage {

    list = [{
        title: "会员等级划分及介绍",
        content: "会员级别共分为4个级别，分别为vip1、vip2、vip3、vip4。会员级别由成长值决定，成长值越高会员等级越高，享受的会员权益越大。会员级别由系统自动处理，无需申请。",
        type: "member"
    }, {
        title: "会员等级划分及介绍",
        content: "会员级别共分为4个级别，分别为vip1、vip2、vip3、vip4。会员级别由成长值决定，成长值越高会员等级越高，享受的会员权益越大。会员级别由系统自动处理，无需申请。",
        type: "member"
    }, {
        title: "什么是成长值？",
        content: "成长值决定会员等级, 成长值越高会员等级越高, 享受到的会员权益越大。会员通过在本壹壹优选上完成登录、购物、评价、晒单等行为所获得相应的成长值。",
        type: "growth"
    }, {
        title: "如何获得成长值?",
        content: "会员级别共分为4个级别，分别为vip1、vip2、vip3、vip4。会员级别由成长值决定，成长值越高会员等级越高，享受的会员权益越大。会员级别由系统自动处理，无需申请。",
        type: "growth"
    }];

    list2 = [{
        origin: "新手欢迎奖",
        score: "10",
        explain: "只能领一次"
    }, {
        origin: "设置头像",
        score: "5",
        explain: "设置头像，只能领一次"
    }, {
        origin: "设置昵称",
        score: "5",
        explain: "设置昵称，只能领一次"
    }, {
        origin: "完善个人信息",
        score: "20",
        explain: "设置性别、生日、城市、职业、个性签名、喜欢的分类，需要全部设置完才能领取，只能领一次"
    }];

    list1 = [];

    pet = "member";

    constructor(public navCtrl: NavController,) {
        this.ordersFilter("member")
    }

    ordersFilter(type) {
        switch (type) {
            case "member":
                this.list1 = this.list.filter(e =>
                    e.type == "member"
                );
                break;
            case "growth":
                this.list1 = this.list.filter(e =>
                    e.type == "growth"
                );
                break;
            default:
                break;
        }
    }

}
