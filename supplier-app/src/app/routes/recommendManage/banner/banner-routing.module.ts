import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ListBannerComponent} from './list-banner/list-banner.component';
import {AddBannerComponent} from './add-banner/add-banner.component';
import {EditBannerComponent} from './edit-banner/edit-banner.component';
import {ViewBannerComponent} from './view-banner/view-banner.component';
import {BannerService} from '../../services/banner.service';


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListBannerComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddBannerComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditBannerComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewBannerComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [BannerService],
})
export class BannerRoutingModule {
}
