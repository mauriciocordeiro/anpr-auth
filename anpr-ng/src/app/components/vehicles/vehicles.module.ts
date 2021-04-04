import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { VehiclesRoutingModule } from './vehicles-routing.module';
import { VehiclesComponent } from './vehicles.component';
import { VehicleDetailComponent } from './vehicle-detail/vehicle-detail.component';
import { MaterialModule } from 'src/app/material.module';
import { ReactiveFormsModule } from '@angular/forms';
import { FlexLayoutModule, FlexModule } from '@angular/flex-layout';
import { NgxMaskModule } from 'ngx-mask';


@NgModule({
  declarations: [
    VehiclesComponent,
    VehicleDetailComponent
  ],
  imports: [
    CommonModule,
    VehiclesRoutingModule,
    MaterialModule,
    ReactiveFormsModule,
    FlexLayoutModule,
    FlexModule,

    NgxMaskModule,
  ]
})
export class VehiclesModule { }
