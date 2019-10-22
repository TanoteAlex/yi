import {Component, OnInit} from '@angular/core';

@Component({
    selector: 'app-community-group-purchase',
    templateUrl: './community-group-purchase.page.html',
    styleUrls: ['./community-group-purchase.page.scss'],
})
export class CommunityGroupPurchasePage implements OnInit {

    selectId:number = 1;
    constructor() {
    }

    ngOnInit() {
    }

    goMyGroup() {

    }

    listFilter(id){
        this.selectId = id;
    }

    segmentList=[{id:1,title:'干货'},
        {id:2,title:'水货'},
        {id:3,title:'好货'},
        {id:4,title:'大货'},
        {id:5,title:'小货'},
        {id:6,title:'零货'},
        {id:7,title:'水货'},
        {id:8,title:'好货'},
        {id:9,title:'大货'},
        {id:10,title:'小货'},
        {id:11,title:'零货'},
    ];

    item = [{
        id:1,
        imgPath:'http://test.adminserver.my11mall.com:8082/attachment/show/74e89859-87f3-475b-a898-5987f3975ba2',
        commodityName:'仕爵18K白金0.50克拉仕爵18K白金0.50克拉仕爵18K白金0.50克拉',
        originalPrice:'3888',
        currentPrice:'3280',
        label:'秒杀',
        num:'2',
    },{
        id:2,
        imgPath:'http://test.adminserver.my11mall.com:8082/attachment/show/7b733df3-5be8-4770-b33d-f35be88770dd',
        commodityName:'欧米茄小清新手表',
        originalPrice:'119999',
        currentPrice:'19999',
        label:'[258元]限时特惠',
        num:'3',
    },{
        id:3,
        imgPath:'http://test.adminserver.my11mall.com:8082/attachment/show/74e89859-87f3-475b-a898-5987f3975ba2',
        commodityName:'仕爵18K白金0.50克拉仕爵18K白金0.50克拉仕爵18K白金0.50克拉',
        originalPrice:'3888',
        currentPrice:'3280',
        label:'秒杀',
        num:'4',
    },
    ]

}
