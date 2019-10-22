import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';

import { IonicModule } from '@ionic/angular';

import { InvitesSharePagePage } from './invites-share-page.page';

const routes: Routes = [
  {
    path: '',
    component: InvitesSharePagePage
  }
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RouterModule.forChild(routes)
  ],
  declarations: [InvitesSharePagePage]
})
export class InvitesSharePagePageModule {}
