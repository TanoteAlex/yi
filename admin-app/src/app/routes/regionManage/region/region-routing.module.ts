import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ListRegionComponent} from './list-region/list-region.component';
import {AddRegionComponent} from './add-region/add-region.component';
import {EditRegionComponent} from './edit-region/edit-region.component';
import {ViewRegionComponent} from './view-region/view-region.component';
import {RegionService} from '../../services/region.service';
import {AreaService} from "../../services/area.service";


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListRegionComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddRegionComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditRegionComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewRegionComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [RegionService,AreaService],
})
export class RegionRoutingModule {
}
