import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from 'src/app/guards/auth.guard';
import { VehicleDetailComponent } from './vehicle-detail/vehicle-detail.component';
import { VehiclesComponent } from './vehicles.component';

const routes: Routes = [
  { path: '', component: VehiclesComponent, canActivate: [AuthGuard] },
  { path: 'new', component: VehicleDetailComponent, canActivate: [AuthGuard], data: { breadcrumb: 'New' } },
  { path: ':id', component: VehicleDetailComponent, canActivate: [AuthGuard], data: { breadcrumb: 'Vehicle' } }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class VehiclesRoutingModule { }
