import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';

import { IonicModule } from '@ionic/angular';

import { CashbackGroupPurchasePage } from './cashback-group-purchase.page';

const routes: Routes = [
  {
    path: '',
    component: CashbackGroupPurchasePage
  }
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RouterModule.forChild(routes)
  ],
  declarations: [CashbackGroupPurchasePage]
})
export class CashbackGroupPurchasePageModule {}
