import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ListSupplierWithdrawComponent} from './list-supplier-withdraw/list-supplier-withdraw.component';
import {AddSupplierWithdrawComponent} from './add-supplier-withdraw/add-supplier-withdraw.component';
import {EditSupplierWithdrawComponent} from './edit-supplier-withdraw/edit-supplier-withdraw.component';
import {ViewSupplierWithdrawComponent} from './view-supplier-withdraw/view-supplier-withdraw.component';
import {SupplierWithdrawService} from '../../services/supplier-withdraw.service';


const routes: Routes = [
    { path: '', redirectTo: 'list', pathMatch: 'full' },
    { path: 'list', component: ListSupplierWithdrawComponent, data: { breadcrumb: '提现列表' } },
    { path: 'add', component: AddSupplierWithdrawComponent, data: { breadcrumb: '新增提现' } },
    { path: 'edit/:objId', component: EditSupplierWithdrawComponent, data: { breadcrumb: '提现发放' } },
    { path: 'view/:objId', component: ViewSupplierWithdrawComponent, data: { breadcrumb: '详情' } },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
    providers: [SupplierWithdrawService],
})
export class SupplierWithdrawRoutingModule {
}
