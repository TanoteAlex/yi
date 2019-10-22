import {Component, OnInit} from '@angular/core';

@Component({
    selector: 'app-invite-rule',
    templateUrl: './invite-rule.page.html',
    styleUrls: ['./invite-rule.page.scss'],
})
export class InviteRulePage implements OnInit {

    list = [
        "参与者资格：所有壹壹优选注册用户均可参与。活动期间每位用户分享邀请码均可获得总值120元的优惠券（有效期7天），每日分享次数不设上限",
        "新用户领取优惠券并完成注册后，视作邀请新用户成功，新用户会获取总值为180元的优惠券，有效期为系统显示为准，同时邀请人获得60元优惠券；邀请的新用户完成首单后，邀请人再可再获得60元优惠券（有效期7天）",
        "每成功邀请一位好友可获得xx积分的奖励，如果好友首次下单并完成付款还可获得奖励xx积分的奖励",
        "有任何疑问请在app内，咨询壹壹优选客服"
    ];

    constructor() {
    }

    ngOnInit() {
    }

}
