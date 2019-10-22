import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {IonicModule} from "@ionic/angular";
import {CommonModule} from "@angular/common";
import {TimeCountComponent} from "./time-count.component";

@NgModule({
    imports: [
        IonicModule,
        CommonModule
    ],
    declarations: [
        TimeCountComponent
    ],
    exports: [
        TimeCountComponent
    ],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TimeCountModule {
}
