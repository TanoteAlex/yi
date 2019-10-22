import {Inject, Injectable, Injector} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {zip} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {MenuService, SettingsService, TitleService} from '@delon/theme';
import { DA_SERVICE_TOKEN, ITokenService, JWTTokenModel } from '@delon/auth';
import {ACLService} from '@delon/acl';

/**
 * 用于应用启动时
 * 一般用来获取应用所需要的基础数据等
 */
@Injectable()
export class StartupService {
    constructor(private menuService: MenuService, private settingService: SettingsService, private aclService: ACLService, private titleService: TitleService,
        @Inject(DA_SERVICE_TOKEN) private tokenService: ITokenService, private httpClient: HttpClient, private injector: Injector) {
    }

    private viaHttp(resolve: any, reject: any) {
        zip(this.httpClient.get('assets/tmp/app-data.json')).pipe(
            // 接收其他拦截器后产生的异常消息
            catchError(([appData]) => {
                resolve(null);
                return [appData];
            }),
        ).subscribe(([appData]) => {

            // application data
            const res: any = appData;
            // 应用信息：包括站点名、描述、年份
            this.settingService.setApp(res.app);
            // 用户信息：包括姓名、头像、邮箱地址
            this.settingService.setUser(res.user);
            // ACL：设置权限为全量
            this.aclService.setFull(true);
            // 初始化菜单
            this.menuService.add(res.menu);
            // 设置页面标题的后缀
            this.titleService.suffix = res.app.name;
        },
            () => {
            },
            () => {
                resolve(null);
            });
    }

    private viaMock(resolve: any, reject: any) {

        const tokenData = this.tokenService.get<JWTTokenModel>(JWTTokenModel).token;

        const app: any = {
            name: `美周壹壹优选`,
            description: `yiyi admin framework`,
        };
        const user: any = {
            name: '美周壹壹优选',
            avatar: './assets/tmp/img/avatar.jpg',
            email: 'cipchk@qq.com',
            token: tokenData,
        };
        // 应用信息：包括站点名、描述、年份
        this.settingService.setApp(app);
        // 用户信息：包括姓名、头像、邮箱地址
        this.settingService.setUser(user);
        // ACL：设置权限为全量
        this.aclService.setFull(true);
        // 初始化菜单
        this.menuService.add([
            {
                text: '主导航',
                group: true,
                hideInBreadcrumb: false,
                children: [
                    {
                        text: '平台数据',
                        icon: 'anticon anticon-appstore-o',
                        link: '/dashboard/',
                    },
                    {
                        text: '商品分类',
                        icon: 'anticon anticon-dot-chart',
                        children: [
                            {
                                text: '商品分类',
                                link: '/dashboard/category',
                            }, {
                                text: '运营分类',
                                link: '/dashboard/operate-category',
                            },
                        ],
                    },
                    {
                        text: '商品管理',
                        icon: 'anticon anticon-shop',
                        children: [
                            {
                                text: '商品列表',
                                link: '/dashboard/commodity',
                            },
                            {
                                text: '待审核商品',
                                link: '/dashboard/commodity-to-examine',
                            },
                        ]
                    },
                    // {
                    //   text: '会员管理',
                    //   icon: 'anticon anticon-appstore-o',
                    //   children: [
                    //     {
                    //       text: '会员列表',
                    //       link: '/member',
                    //     },
                    //   ]
                    // },
                    {
                        text: '订单管理',
                        icon: 'anticon anticon-shopping-cart',
                        children: [
                            {
                                text: '销售订单',
                                link: '/dashboard/sale-order',
                            },
                            // {
                            //   text: '退款处理',
                            //   link: '/dashboard/refund-order',
                            // },
                            // {
                            //   text: '退货处理',
                            //   link: '/dashboard/return-order',
                            // },
                            {
                                text: '售后订单',
                                link: '/dashboard/after-sale-order',
                            },
                            {
                                text: '物流地址',
                                link: '/dashboard/logistics-address',
                            },
                        ]
                    },
                    {
                        text: '快递管理',
                        icon: 'anticon anticon-car',
                        children: [
                            // {
                            //   text: '快递单模板',
                            //   link: '/dashboard/express-template',
                            // },
                            {
                                text: '运费模板设置',
                                link: '/dashboard/freight-template-config',
                            },
                        ]
                    },
                    {
                        text: '结算管理',
                        icon: 'anticon anticon-layout',
                        children: [
                            {
                                text: '提现申请',
                                link: '/dashboard/supplier-withdraw',
                            },
//                            {
//                                text: '提现记录',
//                                link: '/dashboard/supplier-record',
//                            },
                            {
                                text: '我的收入',
                                link: '/dashboard/my-income',
                            },
                        ]
                    },
                    {
                        text: '基本信息',
                        icon: 'anticon anticon-info-circle-o',
                        children: [

                            {
                                text: '账号资料',
                                link: '/dashboard/account-information',
                            },
                            {
                                text: '修改密码',
                                link: '/dashboard/update-password',
                            },
                        ]
                    },
                    // {
                    //   text: '快捷菜单',
                    //   icon: 'anticon anticon-rocket',
                    //   shortcut_root: true,
                    // },
                ],
            },
        ]);
        // 设置页面标题的后缀
        this.titleService.suffix = app.name;

        resolve({});
    }

    load(): Promise<any> {
        // only works with promises
        // https://github.com/angular/angular/issues/15088
        return new Promise((resolve, reject) => {
            // http
            // this.viaHttp(resolve, reject);
            // mock：请勿在生产环境中这么使用，viaMock 单纯只是为了模拟一些数据使脚手架一开始能正常运行
            this.viaMock(resolve, reject);
        });
    }
}
