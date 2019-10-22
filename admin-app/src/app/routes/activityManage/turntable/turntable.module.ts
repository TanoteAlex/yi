import {SharedModule} from "@shared/shared.module";
import {ComponentsModule} from "../../components/components.module";
import {NgModule} from "@angular/core";
import {MemberLevelService} from "../../services/member-level.service";
import {MemberService} from "../../services/member.service";
import {ListTurntableComponent} from "./list-turntable/list-turntable.component";
import {TurntableRoutingModule} from "./turntable.routing.module";

const COMPONENTS = [
  ListTurntableComponent
];

const COMPONENTS_NOROUNT = [
];

@NgModule({
  imports: [
    SharedModule,
    TurntableRoutingModule,
    ComponentsModule,
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[MemberService,MemberLevelService]
})

export class TurntableModule {

}
