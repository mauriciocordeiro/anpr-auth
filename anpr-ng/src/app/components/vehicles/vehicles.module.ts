import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { VehiclesRoutingModule } from './vehicles-routing.module';
import { VehiclesComponent } from './vehicles.component';
import { VehicleDetailComponent } from './vehicle-detail/vehicle-detail.component';
import { MaterialModule } from 'src/app/material.module';


@NgModule({
  declarations: [
    VehiclesComponent,
    VehicleDetailComponent
  ],
  imports: [
    CommonModule,
    VehiclesRoutingModule,
    MaterialModule
  ]
})
export class VehiclesModule { }
