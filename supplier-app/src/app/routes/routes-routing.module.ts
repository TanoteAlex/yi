import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {environment} from '@env/environment';
// layout
import {LayoutDefaultComponent} from '../layout/default/default.component';
import {LayoutFullScreenComponent} from '../layout/fullscreen/fullscreen.component';
import {LayoutPassportComponent} from '../layout/passport/passport.component';
// dashboard pages
import {DashboardComponent} from './dashboard/dashboard.component';
// passport pages
import {UserLoginComponent} from './passport/login/login.component';
import {UserRegisterComponent} from './passport/register/register.component';
import {UserRegisterResultComponent} from './passport/register-result/register-result.component';
// single pages
import {CallbackComponent} from './callback/callback.component';
import {UserLockComponent} from './passport/lock/lock.component';
import {Exception403Component} from './exception/403.component';
import {Exception404Component} from './exception/404.component';
import {Exception500Component} from './exception/500.component';

const routes: Routes = [
  { path: '', redirectTo: 'passport/login', pathMatch: 'full'},
  {
    path: 'dashboard',
    component: LayoutDefaultComponent,
    children: [
      // { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      { path: '', component: DashboardComponent, data: { breadcrumb: '仪表盘',title: '仪表盘', } },
      // 业务子模块
      //首页

      { path: 'dashboard', component: DashboardComponent, data: { breadcrumb: '仪表盘',title: '仪表盘', } },
      // 业务子模块

      //商品分类
      { path: 'category', loadChildren: 'app/routes/categoryManage/category/category.module#CategoryModule', data: { breadcrumb: '商品分类', title: '商品分类'} },
      { path: 'operate-category', loadChildren: 'app/routes/categoryManage/operate-category/operate-category.module#OperateCategoryModule', data: { breadcrumb: ' 运营分类', title: '分类管理'} },
      //商品管理
      { path: 'commodity', loadChildren: 'app/routes/commodityManage/commodity/commodity.module#CommodityModule', data: { breadcrumb: '商品列表', title: '商品列表'} },
      { path: 'commodity-to-examine', loadChildren: 'app/routes/commodityManage/commodity-to-examine/commodity-to-examine.module#CommodityToExamineModule', data: { breadcrumb: '待审核商品', title: '待审核商品'} },

      //订单管理
      { path: 'sale-order', loadChildren: 'app/routes/orderManage/sale-order/sale-order.module#SaleOrderModule', data: { breadcrumb: '销售订单', title: '销售订单'} },
      { path: 'after-sale-order', loadChildren: 'app/routes/orderManage/after-sale-order/after-sale-order.module#AfterSaleOrderModule', data: { breadcrumb: '售后订单', title: '售后订单'} },
      // { path: 'refund-order', loadChildren: 'app/routes/orderManage/refund-order/refund-order.module#RefundOrderModule', data: { breadcrumb: '退款处理', title: '退款处理'} },
      // { path: 'return-order', loadChildren: 'app/routes/orderManage/return-order/return-order.module#ReturnOrderModule', data: { breadcrumb: '退货处理', title: '退货处理'} },
      { path: 'logistics-address', loadChildren: 'app/routes/orderManage/logistics-address/logistics-address.module#LogisticsAddressModule', data: { breadcrumb: '物流地址', title: '物流地址'} },


      //快递管理
      { path: 'express-template', loadChildren: 'app/routes/expressManage/express-template/express-template.module#ExpressTemplateModule', data: { breadcrumb: '快递单模板', title: '快递单模板'} },
      { path: 'freight-template-config', loadChildren: 'app/routes/expressManage/freight-template-config/freight-template-config.module#FreightTemplateConfigModule', data: { breadcrumb: '运费模板设置', title: '运费模板设置'} },


      //供应商管理
      { path: 'supplier-withdraw', loadChildren: 'app/routes/supplierManage/supplier-withdraw/supplier-withdraw.module#SupplierWithdrawModule', data: { breadcrumb: '提现申请', title: '提现申请'} },
//      { path: 'supplier-record', loadChildren: 'app/routes/supplierManage/supplier-record/supplier-record.module#SupplierRecordModule', data: { breadcrumb: '提现记录', title: '提现记录'} },
      { path: 'my-income', loadChildren: 'app/routes/supplierManage/my-income/my-income.module#MyIncomeModule', data: { breadcrumb: '我的收入', title: '我的收入'} },

      //基本信息
      { path: 'account-information', loadChildren: 'app/routes/informationManage/account-information/account-information.module#AccountInformationModule', data: { breadcrumb: '账号资料', title: '账号资料'} },
      { path: 'update-password', loadChildren: 'app/routes/informationManage/update-password/update-password.module#UpdatePasswordModule', data: { breadcrumb: '修改密码', title: '修改密码'} },

    ]
  },
  // 全屏布局
  {
      path: 'fullscreen',
      component: LayoutFullScreenComponent,
      children: [
      ]
  },
  // passport
  {
    path: 'passport',
    component: LayoutPassportComponent,
    children: [
      { path: 'login', component: UserLoginComponent, data: { title: '登录', titleI18n: '登录' } },
      { path: 'register', component: UserRegisterComponent, data: { title: '注册', titleI18n: '注册' } },
      { path: 'register-result', component: UserRegisterResultComponent, data: { title: '注册结果', titleI18n: '注册结果' } }
    ]
  },
  // 单页不包裹Layout
  { path: 'callback/:type', component: CallbackComponent },
  { path: 'lock', component: UserLockComponent, data: { title: '锁屏', titleI18n: 'lock' } },
  { path: '403', component: Exception403Component },
  { path: '404', component: Exception404Component },
  { path: '500', component: Exception500Component },
  { path: '**', redirectTo: 'dashboard' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: environment.useHash })],
  exports: [RouterModule]
})
export class RouteRoutingModule { }

