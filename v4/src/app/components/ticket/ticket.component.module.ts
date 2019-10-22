import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {TicketComponent} from "./ticket.component";
import {IonicModule} from "@ionic/angular";
import {CommonModule} from "@angular/common";

@NgModule({
    imports: [
        IonicModule,
        CommonModule
    ],
    declarations: [
        TicketComponent
    ],
    exports: [
        TicketComponent
    ],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TicketModule {
}
