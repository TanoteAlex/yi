


import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { QuestionRoutingModule } from './question-routing.module';
import { ListQuestionComponent } from './list-question/list-question.component';
import { AddQuestionComponent } from './add-question/add-question.component';
import { EditQuestionComponent } from './edit-question/edit-question.component';
import { FormQuestionComponent } from './form-question/form-question.component';
import { ViewQuestionComponent } from './view-question/view-question.component';
import { ComponentsModule } from "../../components/components.module";
import { QuestionService } from '../../services/question.service';
import {QuestionTypeService} from "../../services/question-type.service";

const COMPONENTS = [
  ListQuestionComponent,
  AddQuestionComponent,
  EditQuestionComponent,
  FormQuestionComponent,
  ViewQuestionComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
QuestionRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[QuestionService,QuestionTypeService]
})
export class QuestionModule { }
