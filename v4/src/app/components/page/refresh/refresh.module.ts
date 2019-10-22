import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {IonicModule} from "@ionic/angular";
import {CommonModule} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {PipesModule} from "../../../../pipes/pipes.module";
import {RefreshComponent} from "./refresh.component";

@NgModule({
    imports: [
        IonicModule,
        CommonModule,
        PipesModule,
        FormsModule,
    ],
    declarations: [
        RefreshComponent
    ],
    exports: [
        RefreshComponent
    ],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RefreshModule {
}
