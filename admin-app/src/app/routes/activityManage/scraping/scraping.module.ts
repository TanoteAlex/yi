import {SharedModule} from "@shared/shared.module";
import {ComponentsModule} from "../../components/components.module";
import {NgModule} from "@angular/core";
import {MemberLevelService} from "../../services/member-level.service";
import {MemberService} from "../../services/member.service";
import {ListScrapingComponent} from "./list-scraping/list-scraping.component";
import {ScrapingRoutingModule} from "./scraping.routing.module";

const COMPONENTS = [
  ListScrapingComponent
];

const COMPONENTS_NOROUNT = [
];

@NgModule({
  imports: [
    SharedModule,
    ScrapingRoutingModule,
    ComponentsModule,
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[MemberService,MemberLevelService]
})

export class ScrapingModule {

}
