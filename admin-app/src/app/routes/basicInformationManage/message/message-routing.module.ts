

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ListMessageComponent} from './list-message/list-message.component';
import {AddMessageComponent} from './add-message/add-message.component';
import {EditMessageComponent} from './edit-message/edit-message.component';
import {ViewMessageComponent} from './view-message/view-message.component';
import {MessageService} from '../../services/message.service';


const routes: Routes = [
    { path: '', redirectTo: 'list', pathMatch: 'full' },
    { path: 'list', component: ListMessageComponent, data: { breadcrumb: '列表' } },
    { path: 'add', component: AddMessageComponent, data: { breadcrumb: '新增' } },
    { path: 'edit/:objId', component: EditMessageComponent, data: { breadcrumb: '编辑' } },
    { path: 'view/:objId', component: ViewMessageComponent, data: { breadcrumb: '详情' } },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
    providers: [MessageService],
})
export class MessageRoutingModule {
}