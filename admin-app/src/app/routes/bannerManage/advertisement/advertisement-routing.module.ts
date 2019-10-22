import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ListAdvertisementComponent} from './list-advertisement/list-advertisement.component';
import {AddAdvertisementComponent} from './add-advertisement/add-advertisement.component';
import {EditAdvertisementComponent} from './edit-advertisement/edit-advertisement.component';
import {ViewAdvertisementComponent} from './view-advertisement/view-advertisement.component';
import {BannerService} from '../../services/banner.service';
import {AdvertisementService} from "../../services/advertisement.service";


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListAdvertisementComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddAdvertisementComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditAdvertisementComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewAdvertisementComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [AdvertisementService],
})
export class AdvertisementRoutingModule {
}
