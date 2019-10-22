import {Inject, Injectable, Injector} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {zip} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {MenuService, SettingsService, TitleService} from '@delon/theme';
import {DA_SERVICE_TOKEN, ITokenService} from '@delon/auth';
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
    // const tokenData = this.tokenService.get();
    // if (!tokenData.token) {
    //   this.injector.get(Router).navigateByUrl('/dashboard/passport/login');
    //   resolve({});
    //   return;
    // }
    // mock
    const app: any = {
      name: `美周壹壹优选`,
      description: `yiyi admin framework`,
    };
    const user: any = {
      name: 'Admin',
      avatar: './assets/tmp/img/avatar.jpg',
      email: 'cipchk@qq.com',
      token: '123456789',
    };

    // 应用信息：包括站点名、描述、年份
    this.settingService.setApp(app);
    // 用户信息：包括姓名、头像、邮箱地址
    this.settingService.setUser(user);
    // ACL：设置权限为全量
    this.aclService.setFull(true);
    // 初始化菜单
    // this.menuService.add([
    //   {
    //     text: '主导航',
    //     group: true,
    //     hideInBreadcrumb: false,
    //     children: [
    //       // {
    //       //   text: '仪表盘',
    //       //   link: '/dashboard/dashboard',
    //       //   icon: 'anticon anticon-appstore-o',
    //       // },
    //       {
    //         text: '平台数据',
    //         icon: 'anticon anticon-appstore-o',
    //         link: '/dashboard/',
    //       },
    //       {
    //         text: '地区管理',
    //         icon: 'anticon anticon-environment-o',
    //         children: [
    //           {
    //             text: '区域列表',
    //             link: '/dashboard/region-group',
    //           },
    //         ]
    //       },
    //       {
    //         text: '小区管理',
    //         icon: 'anticon anticon-home',
    //         children: [
    //           {
    //             text: '小区列表',
    //             link: '/dashboard/community',
    //           },
    //         ]
    //       },
    //       {
    //         text: '商品分类',
    //         icon: 'anticon anticon-dot-chart',
    //         children: [
    //           {
    //             text: '商品分类',
    //             link: '/dashboard/category',
    //           },{
    //             text: '运营分类',
    //             link: '/dashboard/operate-category',
    //           },
    //         ],
    //       },
    //       // {
    //       //   text: '分销管理',
    //       //   icon: 'anticon anticon-solution',
    //       //   children: [
    //       //     {
    //       //       text: '分销等级',
    //       //       link: '/dashboard/distribution-level',
    //       //     },
    //       //   ]
    //       // },
    //       {
    //         text: '商品管理',
    //         icon: 'anticon anticon-shop',
    //         children: [
    //           {
    //             text: '商品列表',
    //             link: '/dashboard/commodity',
    //           },
    //           {
    //             text: '商品属性',
    //             link: '/dashboard/attribute-group',
    //           },
    //           {
    //             text: '商品审核',
    //             link: '/dashboard/commodity-to-examine',
    //           },
    //           {
    //             text: '商品评价',
    //             link: '/dashboard/comment',
    //           },
    //         ],
    //       },
    //       {
    //         text: '库存管理',
    //         icon: 'anticon anticon-database',
    //         children: [
    //           {
    //             text: '库存列表',
    //             link: '/dashboard/stock',
    //           },
    //         ],
    //       },
    //       {
    //         text: '订单设置',
    //         icon: 'anticon anticon-pushpin-o',
    //         children: [
    //           {
    //             text: '订单设置',
    //             link: '/dashboard/order-setting',
    //           },
    //           // {
    //           //   text: '退货原因设置',
    //           //   link: '/dashboard/return-reason',
    //           // },
    //         ]
    //       },
    //       {
    //         text: '订单管理',
    //         icon: 'anticon anticon-shopping-cart',
    //         children: [
    //           {
    //             text: '销售订单',
    //             link: '/dashboard/sale-order',
    //           },
    //           // {
    //           //   text: '退款处理',
    //           //   link: '/dashboard/refund-order',
    //           // },
    //           // {
    //           //   text: '退货处理',
    //           //   link: '/dashboard/return-order',
    //           // },
    //           {
    //             text: '售后订单',
    //             link: '/dashboard/after-sale-order',
    //           },
    //           {
    //             text: '售后原因',
    //             link: '/dashboard/after-sale-reason',
    //           },
    //           {
    //             text: '订单日志',
    //             link: '/dashboard/order-log',
    //           },
    //         ]
    //       },
    //       {
    //         text: '快递管理',
    //         icon: 'anticon anticon-car',
    //         children: [
    //           {
    //             text: '运费模板设置',
    //             link: '/dashboard/freight-template-config',
    //           },
    //         ]
    //       },
    //       {
    //         text: '会员管理',
    //         icon: 'anticon anticon-usergroup-add',
    //         children: [
    //           {
    //             text: '会员列表',
    //             link: '/dashboard/member',
    //           },
    //           {
    //             text: '会员等级',
    //             link: '/dashboard/member-level',
    //           },
    //           {
    //             text: '消费记录',
    //             link: '/dashboard/consume-record',
    //           },
    //           {
    //             text: '登录记录',
    //             link: '/dashboard/login-record',
    //           },
    //         ]
    //       },
    //       {
    //         text: '积分管理',
    //         icon: 'anticon anticon-schedule',
    //         children: [
    //           {
    //             text: '积分记录',
    //             link: '/dashboard/integral-record',
    //           },
    //           {
    //             text: '积分设置',
    //             link: '/dashboard/integral-task',
    //           },
    //         ]
    //       },
    //       {
    //         text: '内容管理',
    //         icon: 'anticon anticon-form',
    //         children: [
    //           {
    //             text: '爱生活',
    //             link: '/dashboard/article',
    //           },
    //           {
    //             text: '爱生活评论',
    //             link: '/dashboard/article-comment',
    //           },
    //         ]
    //       },
    //       {
    //         text: '营销管理',
    //         icon: 'anticon anticon-export',
    //         children: [
    //           {
    //             text: '优惠券',
    //             link: '/dashboard/coupon',
    //           },
    //           {
    //             text: '代金券',
    //             link: '/dashboard/voucher',
    //           },
    //           {
    //             text: '储值券',
    //             link: '/dashboard/stored-coupon',
    //           },
    //           {
    //             text: '代金券发放',
    //             link: '/dashboard/voucher-grant-config',
    //           },
    //           {
    //             text: '代金券发放记录',
    //             link: '/dashboard/voucher-grant-record',
    //           },
    //           {
    //               text: '奖励列表',
    //             link: '/dashboard/reward',
    //           },
    //           {
    //             text: '奖品列表',
    //             link: '/dashboard/prize',
    //           },
    //         ]
    //       },
    //       {
    //         text: '团购管理',
    //         icon: 'anticon anticon-clock-circle-o',
    //         children: [
    //           {
    //             text: '团购活动',
    //             link: '/dashboard/group-buy-activity',
    //           },
    //           {
    //             text: '开团列表',
    //             link: '/dashboard/group-buy-order',
    //           },
    //           // {
    //           //   text: '参团列表',
    //           //   link: '/dashboard/join-group-buy',
    //           // },
    //         ]
    //       },
    //       {
    //         text: '活动管理',
    //         icon: 'anticon anticon-gift',
    //         children: [
    //           {
    //             text: '大转盘',
    //             link: '/dashboard/turntable',
    //           },
    //           {
    //             text: '大富翁',
    //             link: '/dashboard/millionaire',
    //           },
    //           {
    //             text: '刮刮乐',
    //             link: '/dashboard/scraping',
    //           },
    //         ]
    //       },
    //       // {
    //       //   text: '拼团管理',
    //       //   icon: 'anticon anticon-clock-circle-o',
    //       //   children: [
    //       //     {
    //       //       text: '全国拼团',
    //       //       link: '/dashboard/national-group',
    //       //     },
    //       //     {
    //       //       text: '小区拼团',
    //       //       link: '/dashboard/community-group',
    //       //     },
    //       //     {
    //       //       text: '返现拼团',
    //       //       link: '/dashboard/rebate-group',
    //       //     },
    //       //     {
    //       //       text: '秒杀活动',
    //       //       link: '/dashboard/seckill',
    //       //     },
    //       //   ]
    //       // },
    //       {
    //         text: '推荐系统',
    //         icon: 'anticon anticon-share-alt',
    //         children: [
    //           {
    //             text: '位置管理',
    //             link: '/dashboard/position',
    //           },
    //           {
    //             text: '推荐管理',
    //             link: '/dashboard/recommend',
    //           },
    //           {
    //             text: '广告管理',
    //             link: '/dashboard/advertisement',
    //           }
    //
    //         ]
    //       },
    //       {
    //         text: '基础信息',
    //         icon: 'anticon anticon-info-circle-o',
    //         children: [
    //           {
    //             text: '基础信息',
    //             link:'/dashboard/basic-info',
    //           },
    //           {
    //             text: '消息通知',
    //             link:'/dashboard/message',
    //           },
    //           {
    //             text: '基础规则',
    //             link:'/dashboard/basic-rule',
    //           },
    //           {
    //             text: '问题类型',
    //             link:'/dashboard/question-type',
    //           },
    //           {
    //             text: '帮助列表',
    //             link: '/dashboard/question',
    //           }
    //         ]
    //       },
    //       {
    //         text: '财务管理',
    //         icon: 'anticon anticon-wallet',
    //         children: [
    //           {
    //             text: '交易记录',
    //             link: '/dashboard/account-record',
    //           },
    //           {
    //             text: '供应商销售统计',
    //             link: '/dashboard/supplier-sale-stat',
    //           },
    //           {
    //             text: '供应商提现申请',
    //             link: '/dashboard/supplier-withdraw',
    //           },
    //           {
    //             text: '供应商提现记录',
    //             link: '/dashboard/supplier-record',
    //           },
    //           {
    //             text: '供应商对账明细',
    //             link: '/dashboard/supplier-check-account',
    //           },
    //         ]
    //       },
    //       {
    //         text: '管理员管理',
    //         icon: 'anticon anticon-user',
    //         children: [
    //           {
    //             text: '成员管理',
    //             link: '/dashboard/user',
    //           },
    //           {
    //             text: '角色管理',
    //             link: '/dashboard/role',
    //           },
    //           {
    //             text: '部门管理',
    //             link: '/dashboard/dept',
    //           },
    //         ]
    //       },
    //       {
    //         text: '供应商管理',
    //         icon: 'anticon anticon-solution',
    //         children: [
    //           {
    //             text: '供应商列表',
    //             link: '/dashboard/supplier',
    //           },
    //           {
    //             text: '供应商概况',
    //             link: '/dashboard/supplier-survey',
    //           },
    //            /*{
    //              text: '快递单模板设置',
    //              link: '/dashboard/express-template',
    //            },*/
    //         ]
    //       },
    //       // {
    //       //   text: '快捷菜单',
    //       //   icon: 'anticon anticon-rocket',
    //       //   shortcut_root: true,
    //       // },
    //     ],
    //   },
    // ]);
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
