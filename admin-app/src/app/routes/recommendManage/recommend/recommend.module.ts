import {NgModule} from '@angular/core';
import {SharedModule} from '@shared/shared.module';
import {RecommendRoutingModule} from './recommend-routing.module';
import {ListRecommendComponent} from './list-recommend/list-recommend.component';
import {AddRecommendComponent} from './add-recommend/add-recommend.component';
import {EditRecommendComponent} from './edit-recommend/edit-recommend.component';
import {FormRecommendComponent} from './form-recommend/form-recommend.component';
import {ViewRecommendComponent} from './view-recommend/view-recommend.component';
import {ComponentsModule} from "../../components/components.module";
import {RecommendService} from '../../services/recommend.service';
import {CommodityService} from "../../services/commodity.service";
import {PositionService} from "../../services/position.service";
import {ArticleService} from "../../services/article.service";

const COMPONENTS = [
    ListRecommendComponent,
    AddRecommendComponent,
    EditRecommendComponent,
    FormRecommendComponent,
    ViewRecommendComponent];

const COMPONENTS_NOROUNT = [
];

@NgModule({
    imports: [
        SharedModule,
        RecommendRoutingModule,
        ComponentsModule
    ],
    declarations: [
        ...COMPONENTS,
        ...COMPONENTS_NOROUNT,
    ],
    entryComponents: COMPONENTS_NOROUNT,
    providers: [RecommendService, CommodityService, PositionService, ArticleService]
})
export class RecommendModule { }
