import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ListCommentComponent} from './list-comment/list-comment.component';
import {AddCommentComponent} from './add-comment/add-comment.component';
import {EditCommentComponent} from './edit-comment/edit-comment.component';
import {ViewCommentComponent} from './view-comment/view-comment.component';
import {CommentService} from '../../services/comment.service';
import {CommodityService} from "../../services/commodity.service";
import {MemberService} from "../../services/member.service";


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListCommentComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddCommentComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditCommentComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewCommentComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [CommentService,CommodityService,MemberService],
})
export class CommentRoutingModule {
}
