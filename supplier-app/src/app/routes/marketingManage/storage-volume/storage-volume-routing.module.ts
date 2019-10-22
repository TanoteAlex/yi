import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ListStorageVolumeComponent} from './list-storage-volume/list-storage-volume.component';
import {AddCouponComponent} from './add-storage-volume/add-storage-volume.component';
import {EditStorageVolumeComponent} from './edit-storage-volume/edit-storage-volume.component';
import {ViewCouponComponent} from './view-storage-volume/view-coupon.component';
import {CouponService} from '../../services/coupon.service';


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListStorageVolumeComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddCouponComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditStorageVolumeComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewCouponComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [CouponService],
})
export class StorageVolumeRoutingModule {
}
