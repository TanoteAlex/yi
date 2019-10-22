import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ListConsumeRecordComponent} from './list-consume-record/list-consume-record.component';
import {AccountRecordService} from "../../services/account-record.service";


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListConsumeRecordComponent, data: {breadcrumb: '列表'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [AccountRecordService],
})
export class ConsumeRecordRoutingModule {
}
