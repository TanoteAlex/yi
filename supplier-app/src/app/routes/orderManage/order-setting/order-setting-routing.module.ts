import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ListOrderSettingComponent} from './list-order-setting/list-order-setting.component';
import {AddOrderSettingComponent} from './add-order-setting/add-order-setting.component';
import {EditOrderSettingComponent} from './edit-order-setting/edit-order-setting.component';
import {ViewOrderSettingComponent} from './view-order-setting/view-order-setting.component';
import {OrderSettingService} from '../../services/order-setting.service';


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListOrderSettingComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddOrderSettingComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditOrderSettingComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewOrderSettingComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [OrderSettingService],
})
export class OrderSettingRoutingModule {
}
