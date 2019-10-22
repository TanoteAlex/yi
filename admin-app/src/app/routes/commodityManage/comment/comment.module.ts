import {NgModule} from '@angular/core';
import {SharedModule} from '@shared/shared.module';
import {CommentRoutingModule} from './comment-routing.module';
import {ListCommentComponent} from './list-comment/list-comment.component';
import {AddCommentComponent} from './add-comment/add-comment.component';
import {EditCommentComponent} from './edit-comment/edit-comment.component';
import {FormCommentComponent} from './form-comment/form-comment.component';
import {ViewCommentComponent} from './view-comment/view-comment.component';
import {ComponentsModule} from "../../components/components.module";
import {CommentService} from '../../services/comment.service';
import {CommodityService} from "../../services/commodity.service";
import {MemberService} from "../../services/member.service";
const COMPONENTS = [
  ListCommentComponent,
  AddCommentComponent,
  EditCommentComponent,
  FormCommentComponent,
  ViewCommentComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
CommentRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[CommentService,CommodityService,MemberService]
})
export class CommentModule { }
