import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ListPlatformSaleStatComponent} from './list-platform-sale-stat/list-platform-sale-stat.component';
import {AddPlatformSaleStatComponent} from './add-platform-sale-stat/add-platform-sale-stat.component';
import {EditPlatformSaleStatComponent} from './edit-platform-sale-stat/edit-platform-sale-stat.component';
import {ViewPlatformSaleStatComponent} from './view-platform-sale-stat/view-platform-sale-stat.component';
import {PlatformSaleStatService} from '../../services/platform-sale-stat.service';


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListPlatformSaleStatComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddPlatformSaleStatComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditPlatformSaleStatComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewPlatformSaleStatComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [PlatformSaleStatService],
})
export class PlatformSaleStatRoutingModule {
}
