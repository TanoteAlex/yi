import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ListSupplierComponent} from './list-supplier/list-supplier.component';
import {AddSupplierComponent} from './add-supplier/add-supplier.component';
import {EditSupplierComponent} from './edit-supplier/edit-supplier.component';
import {ViewSupplierComponent} from './view-supplier/view-supplier.component';
import {SupplierService} from '../../services/supplier.service';
import {UserService} from "../../services/user.service";


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListSupplierComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddSupplierComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditSupplierComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewSupplierComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [SupplierService,UserService],
})
export class SupplierRoutingModule {
}
