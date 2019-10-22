import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ListSupplierCheckAccountComponent} from './list-supplier-check-account/list-supplier-check-account.component';
import {AddSupplierCheckAccountComponent} from './add-supplier-check-account/add-supplier-check-account.component';
import {EditSupplierCheckAccountComponent} from './edit-supplier-check-account/edit-supplier-check-account.component';
import {ViewSupplierCheckAccountComponent} from './view-supplier-check-account/view-supplier-check-account.component';
import {SupplierCheckAccountService} from '../../services/supplier-check-account.service';


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListSupplierCheckAccountComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddSupplierCheckAccountComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditSupplierCheckAccountComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewSupplierCheckAccountComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [SupplierCheckAccountService],
})
export class SupplierCheckAccountRoutingModule {
}
