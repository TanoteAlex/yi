


import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { QuestionTypeRoutingModule } from './question-type-routing.module';
import { ListQuestionTypeComponent } from './list-question-type/list-question-type.component';
import { AddQuestionTypeComponent } from './add-question-type/add-question-type.component';
import { EditQuestionTypeComponent } from './edit-question-type/edit-question-type.component';
import { FormQuestionTypeComponent } from './form-question-type/form-question-type.component';
import { ViewQuestionTypeComponent } from './view-question-type/view-question-type.component';
import { ComponentsModule } from "../../components/components.module";
import { QuestionTypeService } from '../../services/question-type.service';

const COMPONENTS = [
  ListQuestionTypeComponent,
  AddQuestionTypeComponent,
  EditQuestionTypeComponent,
  FormQuestionTypeComponent,
  ViewQuestionTypeComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
QuestionTypeRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[QuestionTypeService]
})
export class QuestionTypeModule { }