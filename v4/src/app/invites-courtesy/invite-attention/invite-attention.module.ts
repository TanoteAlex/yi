import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';

import { IonicModule } from '@ionic/angular';

import { InviteAttentionPage } from './invite-attention.page';

const routes: Routes = [
  {
    path: '',
    component: InviteAttentionPage
  }
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RouterModule.forChild(routes)
  ],
  declarations: [InviteAttentionPage]
})
export class InviteAttentionPageModule {}
