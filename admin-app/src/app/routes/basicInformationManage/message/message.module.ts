


import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { MessageRoutingModule } from './message-routing.module';
import { ListMessageComponent } from './list-message/list-message.component';
import { AddMessageComponent } from './add-message/add-message.component';
import { EditMessageComponent } from './edit-message/edit-message.component';
import { FormMessageComponent } from './form-message/form-message.component';
import { ViewMessageComponent } from './view-message/view-message.component';
import { ComponentsModule } from "../../components/components.module";
import { MessageService } from '../../services/message.service';

const COMPONENTS = [
    ListMessageComponent,
    AddMessageComponent,
    EditMessageComponent,
    FormMessageComponent,
    ViewMessageComponent];

const COMPONENTS_NOROUNT = [
];

@NgModule({
    imports: [
        SharedModule,
        MessageRoutingModule,
        ComponentsModule
    ],
    declarations: [
        ...COMPONENTS,
        ...COMPONENTS_NOROUNT,
    ],
    entryComponents: COMPONENTS_NOROUNT,
    providers: [MessageService]
})
export class MessageModule { }