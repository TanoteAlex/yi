import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ListExpressTemplateComponent} from './list-express-template/list-express-template.component';
import {AddExpressTemplateComponent} from './add-express-template/add-express-template.component';
import {EditExpressTemplateComponent} from './edit-express-template/edit-express-template.component';
import {ViewExpressTemplateComponent} from './view-express-template/view-express-template.component';
import {ExpressTemplateService} from '../../services/express-template.service';


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListExpressTemplateComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddExpressTemplateComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditExpressTemplateComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewExpressTemplateComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [ExpressTemplateService],
})
export class ExpressTemplateRoutingModule {
}
