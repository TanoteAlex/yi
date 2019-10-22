import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {StarComponent} from "./star.component";
import {IonicModule} from "@ionic/angular";
import {CommonModule} from "@angular/common";

@NgModule({
    imports: [
        IonicModule,
        CommonModule,
    ],
    declarations: [
        StarComponent
    ],
    exports: [
        StarComponent
    ],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class StarModule {
}
