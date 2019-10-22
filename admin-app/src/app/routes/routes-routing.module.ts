import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { environment } from '@env/environment';
// layout
import { LayoutDefaultComponent } from '../layout/default/default.component';
import { LayoutFullScreenComponent } from '../layout/fullscreen/fullscreen.component';
import { LayoutPassportComponent } from '../layout/passport/passport.component';
// dashboard pages
import { DashboardComponent } from './dashboard/dashboard.component';
// passport pages
import { UserLoginComponent } from './passport/login/login.component';
import { UserRegisterComponent } from './passport/register/register.component';
import { UserRegisterResultComponent } from './passport/register-result/register-result.component';
// single pages
import { CallbackComponent } from './callback/callback.component';
import { UserLockComponent } from './passport/lock/lock.component';
import { Exception403Component } from './exception/403.component';
import { Exception404Component } from './exception/404.component';
import { Exception500Component } from './exception/500.component';
import { InviteActivityModule } from './marketingManage/invite-activity/invite-activity.module';
import { InviteRewardModule } from './marketingManage/invite-reward/invite-reward.module';
import { SalesAreaModule } from './activityManage/sales-area/sales-area.module';

const routes: Routes = [
  { path: '', redirectTo: 'passport/login', pathMatch: 'full' },
  {
    path: 'dashboard',
    component: LayoutDefaultComponent,
    children: [
      // { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      { path: '', component: DashboardComponent, data: { breadcrumb: '仪表盘', title: '仪表盘' } },
      // 业务子模块
      //首页

      //商品管理
      { path: 'commodity', loadChildren: 'app/routes/commodityManage/commodity/commodity.module#CommodityModule', data: { breadcrumb: '商品列表', title: '商品列表' } },
      {
        path: 'commodity-to-examine',
        loadChildren: 'app/routes/commodityManage/commodity-to-examine/commodity-to-examine.module#CommodityToExamineModule',
        data: { breadcrumb: '待审核商品', title: '待审核商品' },
      },
      { path: 'comment', loadChildren: 'app/routes/commodityManage/comment/comment.module#CommentModule', data: { breadcrumb: '商品评价', title: '商品评价' } },
      {
        path: 'attribute-group',
        loadChildren: 'app/routes/commodityManage/attribute-group/attribute-group.module#AttributeGroupModule',
        data: { breadcrumb: '商品属性', title: '商品属性' },
      },


      //商品分类
      { path: 'category', loadChildren: 'app/routes/categoryManage/category/category.module#CategoryModule', data: { breadcrumb: '商品分类', title: '商品分类' } },
      {
        path: 'operate-category',
        loadChildren: 'app/routes/categoryManage/operate-category/operate-category.module#OperateCategoryModule',
        data: { breadcrumb: ' 运营分类', title: '分类管理' },
      },

      //分销管理
      {
        path: 'distribution-level',
        loadChildren: 'app/routes/distributionManage/distribution-level/distribution-level.module#DistributionLevelModule',
        data: { breadcrumb: '分销等级', title: '分销等级' },
      },

      //会员管理
      { path: 'member', loadChildren: 'app/routes/memberManage/member/member.module#MemberModule', data: { breadcrumb: '会员列表', title: '会员列表' } },
      { path: 'member-level', loadChildren: 'app/routes/memberManage/member-level/member-level.module#MemberLevelModule', data: { breadcrumb: '会员等级', title: '会员等级' } },
      { path: 'consume-record', loadChildren: 'app/routes/memberManage/consume-record/consume-record.module#ConsumeRecordModule', data: { breadcrumb: '消费记录', title: '消费记录' } },
      { path: 'login-record', loadChildren: 'app/routes/memberManage/login-record/login-record.module#LoginRecordModule', data: { breadcrumb: '登录记录', title: '登录记录' } },

      //积分管理
      { path: 'integral-cash', loadChildren: 'app/routes/IntegralManage/integral-cash/integral-cash.module#IntegralCashModule', data: { breadcrumb: '积分抵现设置', title: '积分抵现设置' } },
      { path: 'integral-task', loadChildren: 'app/routes/IntegralManage/integral-task/integral-task.module#IntegralTaskModule', data: { breadcrumb: '积分设置', title: '积分设置' } },
      {
        path: 'integral-record',
        loadChildren: 'app/routes/IntegralManage/integral-record/integral-record.module#IntegralRecordModule',
        data: { breadcrumb: '积分记录', title: '积分记录' },
      },

      //内容管理
      { path: 'article', loadChildren: 'app/routes/contentManage/article/article.module#ArticleModule', data: { breadcrumb: '爱生活', title: '爱生活' } },

      {
        path: 'article-comment',
        loadChildren: 'app/routes/contentManage/article-comment/article-comment.module#ArticleCommentModule',
        data: { breadcrumb: '爱生活评论', title: '爱生活评论' },
      },

      //拼团管理
      {
        path: 'group-buy-activity',
        loadChildren: 'app/routes/groupBuyManage/group-buy-activity/group-buy-activity.module#GroupBuyActivityModule',
        data: { breadcrumb: '团购活动', title: '团购活动' },
      },
      {
        path: 'group-buy-order',
        loadChildren: 'app/routes/groupBuyManage/group-buy-order/group-buy-order.module#GroupBuyOrderModule',
        data: { breadcrumb: '开团列表', title: '开团列表' },
      },
      { path: 'join-group-buy', loadChildren: 'app/routes/groupBuyManage/join-group-buy/join-group-buy.module#JoinGroupBuyModule', data: { breadcrumb: '参团列表', title: '参团列表' } },

      { path: 'national-group', loadChildren: 'app/routes/collageManage/national-group/national-group.module#NationalGroupModule', data: { breadcrumb: '全国拼团', title: '全国拼团' } },
      {
        path: 'community-group',
        loadChildren: 'app/routes/collageManage/community-group/community-group.module#CommunityGroupModule',
        data: { breadcrumb: '小区拼团', title: '小区拼团' },
      },
      { path: 'rebate-group', loadChildren: 'app/routes/collageManage/rebate-group/rebate-group.module#RebateGroupModule', data: { breadcrumb: '返现拼团', title: '返现拼团' } },
      { path: 'seckill', loadChildren: 'app/routes/collageManage/seckill/seckill.module#SeckillModule', data: { breadcrumb: '秒杀活动', title: '秒杀活动' } },

      //营销管理
      { path: 'coupon', loadChildren: 'app/routes/marketingManage/coupon/coupon.module#CouponModule', data: { breadcrumb: '优惠券列表', title: '优惠券列表' } },
      { path: 'stored-coupon', loadChildren: 'app/routes/marketingManage/stored-coupon/stored-coupon.module#StoredCouponModule', data: { breadcrumb: '储值券', title: '储值券' } },
      { path: 'voucher', loadChildren: 'app/routes/marketingManage/voucher/voucher.module#VoucherModule', data: { breadcrumb: '代金券', title: '代金券' } },
      {
        path: 'voucher-grant-config',
        loadChildren: 'app/routes/marketingManage/voucher-grant-config/voucher-grant-config.module#VoucherGrantConfigModule',
        data: { breadcrumb: '代金券发放', title: '代金券发放' },
      },
      {
        path: 'voucher-grant-record',
        loadChildren: 'app/routes/marketingManage/voucher-grant-record/voucher-grant-record.module#VoucherGrantRecordModule',
        data: { breadcrumb: '代金券发放记录', title: '代金券发放记录' },
      },
      { path: 'reward', loadChildren: 'app/routes/marketingManage/reward/reward.module#RewardModule', data: { breadcrumb: '奖励列表', title: '奖励列表' } },
      { path: 'prize', loadChildren: 'app/routes/marketingManage/prize/prize.module#PrizeModule', data: { breadcrumb: '奖品列表', title: '奖品列表' } },
      {
        path: 'invite-activity',
        loadChildren: 'app/routes/marketingManage/invite-activity/invite-activity.module#InviteActivityModule',
        data: { breadcrumb: '邀请有礼活动列表', title: '邀请有礼活动列表' },
      },
      { path: 'invite-prize', loadChildren: 'app/routes/marketingManage/invite-prize/invite-prize.module#InvitePrizeModule', data: { breadcrumb: '邀请有礼奖励列表', title: '邀请有礼奖励列表' } },

      //库存管理
      { path: 'stock', loadChildren: 'app/routes/stockManage/stock/stock.module#StockModule', data: { breadcrumb: '库存列表', title: '库存列表' } },

      //订单管理
      { path: 'sale-order', loadChildren: 'app/routes/orderManage/sale-order/sale-order.module#SaleOrderModule', data: { breadcrumb: '销售订单', title: '销售订单' } },
      // { path: 'refund-order', loadChildren: 'app/routes/orderManage/refund-order/refund-order.module#RefundOrderModule', data: { breadcrumb: '退款处理', title: '退款处理'} },
      // { path: 'return-order', loadChildren: 'app/routes/orderManage/return-order/return-order.module#ReturnOrderModule', data: { breadcrumb: '退货处理', title: '退货处理'} },
      { path: 'order-setting', loadChildren: 'app/routes/orderManage/order-setting/order-setting.module#OrderSettingModule', data: { breadcrumb: '订单设置', title: '订单设置' } },
      // { path: 'return-reason', loadChildren: 'app/routes/orderManage/return-reason/return-reason.module#ReturnReasonModule', data: { breadcrumb: '退货原因设置', title: '退货原因设置'} },
      {
        path: 'after-sale-reason',
        loadChildren: 'app/routes/orderManage/after-sale-reason/after-sale-reason.module#AfterSaleReasonModule',
        data: { breadcrumb: '售后原因', title: '售后原因' },
      },
      {
        path: 'after-sale-order',
        loadChildren: 'app/routes/orderManage/after-sale-order/after-sale-order.module#AfterSaleOrderModule',
        data: { breadcrumb: '售后订单', title: '售后订单' },
      },
      { path: 'order-log', loadChildren: 'app/routes/orderManage/order-log/order-log.module#OrderLogModule', data: { breadcrumb: '订单日志', title: '订单日志' } },


      //快递管理
      {
        path: 'freight-template-config',
        loadChildren: 'app/routes/expressManage/freight-template-config/freight-template-config.module#FreightTemplateConfigModule',
        data: { breadcrumb: '运费模板设置', title: '运费模板设置' },
      },

      //供应商管理
      { path: 'supplier', loadChildren: 'app/routes/supplierManage/supplier/supplier.module#SupplierModule', data: { breadcrumb: '供应商管理', title: '供应商管理' } },
      {
        path: 'supplier-survey',
        loadChildren: 'app/routes/supplierManage/supplier-survey/supplier-survey.module#SupplierSurveyModule',
        data: { breadcrumb: '供应商概况', title: '供应商概况' },
      },
      {
        path: 'express-template',
        loadChildren: 'app/routes/supplierManage/express-template/express-template.module#ExpressTemplateModule',
        data: { breadcrumb: '快递单模板', title: '快递单模板' },
      },

      //系统管理
      { path: 'user', loadChildren: 'app/routes/systemManage/user/user.module#UserModule', data: { breadcrumb: '成员管理', title: '成员管理' } },
      { path: 'role', loadChildren: 'app/routes/systemManage/role/role.module#RoleModule', data: { breadcrumb: '角色管理', title: '角色管理' } },
      { path: 'dept', loadChildren: 'app/routes/systemManage/dept/dept.module#DeptModule', data: { breadcrumb: '部门管理', title: '部门管理' } },

      //财务
      { path: 'account-record', loadChildren: 'app/routes/financeManage/account-record/account-record.module#AccountRecordModule', data: { breadcrumb: '交易记录', title: '交易记录' } },
      {
        path: 'supplier-sale-stat',
        loadChildren: 'app/routes/financeManage/supplier-sale-stat/supplier-sale-stat.module#SupplierSaleStatModule',
        data: { breadcrumb: '供应商销售统计', title: '供应商销售统计' },
      },
      {
        path: 'supplier-withdraw',
        loadChildren: 'app/routes/financeManage/supplier-withdraw/supplier-withdraw.module#SupplierWithdrawModule',
        data: { breadcrumb: '供应商提现', title: '供应商提现' },
      },
      // { path: 'supplier-record', loadChildren: 'app/routes/financeManage/supplier-record/supplier-record.module#SupplierRecordModule', data: { breadcrumb: '供应商提现记录', title: '供应商提现记录'} },
      {
        path: 'supplier-check-account',
        loadChildren: 'app/routes/financeManage/supplier-check-account/supplier-check-account.module#SupplierCheckAccountModule',
        data: { breadcrumb: '供应商对账明细', title: '供应商对账明细' },
      },

      //地区管理
      { path: 'region', loadChildren: 'app/routes/regionManage/region/region.module#RegionModule', data: { breadcrumb: '地区列表', title: '地区列表' } },
      { path: 'region-group', loadChildren: 'app/routes/regionManage/region-group/region-group.module#RegionGroupModule', data: { breadcrumb: '区域表', title: '区域表' } },

      //小区管理
      { path: 'community', loadChildren: 'app/routes/communityManage/community/community.module#CommunityModule', data: { breadcrumb: '小区列表', title: '小区列表' } },

      //广告系统
      { path: 'advertisement', loadChildren: 'app/routes/bannerManage/advertisement/advertisement.module#AdvertisementModule', data: { breadcrumb: '广告位设置', title: '广告位设置' } },


      //基本设置
      { path: 'basic-info', loadChildren: 'app/routes/basicInformationManage/basic-info/basic-info.module#BasicInfoModule', data: { breadcrumb: '基础信息', title: '基础信息' } },
      { path: 'message', loadChildren: 'app/routes/basicInformationManage/message/message.module#MessageModule', data: { breadcrumb: '推送消息', title: '推送消息' } },
      { path: 'basic-rule', loadChildren: 'app/routes/basicInformationManage/basic-rule/basic-rule.module#BasicRuleModule', data: { breadcrumb: '基础规则', title: '基础规则' } },
      {
        path: 'question-type',
        loadChildren: 'app/routes/basicInformationManage/question-type/question-type.module#QuestionTypeModule',
        data: { breadcrumb: '问题类型', title: '问题类型' },
      },
      { path: 'question', loadChildren: 'app/routes/basicInformationManage/question/question.module#QuestionModule', data: { breadcrumb: '帮助列表', title: '帮助列表' } },


      //推荐系统
      { path: 'recommend', loadChildren: 'app/routes/recommendManage/recommend/recommend.module#RecommendModule', data: { breadcrumb: '推荐位设置', title: '推荐位设置' } },
      { path: 'position', loadChildren: 'app/routes/recommendManage/position/position.module#PositionModule', data: { breadcrumb: '位置管理', title: '位置管理' } },

      //活动管理
      { path: 'millionaire', loadChildren: 'app/routes/activityManage/millionaire/millionaire.module#MillionaireModule', data: { breadcrumb: '大富翁', title: '大富翁' } },
      { path: 'turntable', loadChildren: 'app/routes/activityManage/turntable/turntable.module#TurntableModule', data: { breadcrumb: '大转盘', title: '大转盘' } },
      { path: 'scraping', loadChildren: 'app/routes/activityManage/scraping/scraping.module#ScrapingModule', data: { breadcrumb: '刮刮乐', title: '刮刮乐' } },
      { path: 'sales-area', loadChildren: 'app/routes/activityManage/sales-area/sales-area.module#SalesAreaModule', data: { breadcrumb: '专区活动', title: '专区活动' } },
      {
        path: 'sales-area-commodity',
        loadChildren: 'app/routes/activityManage/sales-area-commodity/sales-area-commodity.module#SalesAreaCommodityModule',
        data: { breadcrumb: '专区活动商品管理', title: '专区活动商品管理' },
      },
      { path: 'area-column', loadChildren: 'app/routes/activityManage/area-column/area-column.module#AreaColumnModule', data: { breadcrumb: '专区栏目管理', title: '专区栏目管理' } },
      {
        path: 'area-column-commodity',
        loadChildren: 'app/routes/activityManage/area-column-commodity/area-column-commodity.module#AreaColumnCommodityModule',
        data: { breadcrumb: '专区栏目商品管理', title: '专区栏目商品管理' },
      },

    ],
  },
  // 全屏布局
  {
    path: 'fullscreen',
    component: LayoutFullScreenComponent,
    children: [],
  },
  // passport
  {
    path: 'passport',
    component: LayoutPassportComponent,
    children: [
      { path: 'login', component: UserLoginComponent, data: { title: '登录', titleI18n: '登录' } },
      { path: 'register', component: UserRegisterComponent, data: { title: '注册', titleI18n: '注册' } },
      { path: 'register-result', component: UserRegisterResultComponent, data: { title: '注册结果', titleI18n: '注册结果' } },
    ],
  },
  // 单页不包裹Layout
  { path: 'callback/:type', component: CallbackComponent },
  { path: 'lock', component: UserLockComponent, data: { title: '锁屏', titleI18n: 'lock' } },
  { path: '403', component: Exception403Component },
  { path: '404', component: Exception404Component },
  { path: '500', component: Exception500Component },
  { path: '**', redirectTo: 'dashboard' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: environment.useHash })],
  exports: [RouterModule],
})
export class RouteRoutingModule {
}

