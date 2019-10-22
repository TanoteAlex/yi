import {SharedModule} from "@shared/shared.module";
import {ComponentsModule} from "../../components/components.module";
import {NgModule} from "@angular/core";
import {MemberLevelService} from "../../services/member-level.service";
import {MemberService} from "../../services/member.service";
import {ListMillionaireComponent} from "./list-millionaire/list-millionaire.component";
import {MillionaireRoutingModule} from "./millionaire.routing.module";

const COMPONENTS = [
  ListMillionaireComponent
];

const COMPONENTS_NOROUNT = [
];

@NgModule({
  imports: [
    SharedModule,
    MillionaireRoutingModule,
    ComponentsModule,
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[MemberService,MemberLevelService]
})

export class MillionaireModule {

}
