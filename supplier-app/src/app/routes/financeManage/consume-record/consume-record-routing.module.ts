import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ListConsumeRecordComponent} from './list-consume-record/list-consume-record.component';
import {AddConsumeRecordComponent} from './add-consume-record/add-consume-record.component';
import {EditConsumeRecordComponent} from './edit-consume-record/edit-consume-record.component';
import {ViewConsumeRecordComponent} from './view-consume-record/view-consume-record.component';
import {ConsumeRecordService} from '../../services/consume-record.service';


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListConsumeRecordComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddConsumeRecordComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditConsumeRecordComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewConsumeRecordComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [ConsumeRecordService],
})
export class ConsumeRecordRoutingModule {
}
