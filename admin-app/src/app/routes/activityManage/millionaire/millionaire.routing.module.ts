import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {MemberLevelService} from "../../services/member-level.service";
import {MemberService} from "../../services/member.service";
import {ListMillionaireComponent} from "./list-millionaire/list-millionaire.component";

const routes: Routes = [
  {path: '', redirectTo: 'list', pathMatch: 'full'},
  {path: 'list', component: ListMillionaireComponent, data: {breadcrumb: '列表'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [MemberService,MemberLevelService],
})
export class MillionaireRoutingModule {

}
