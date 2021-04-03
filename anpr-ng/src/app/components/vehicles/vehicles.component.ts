import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Vehicle } from 'src/app/model/vehicle';
import { VehicleService } from 'src/app/services/vehicle.service';

@Component({
  selector: 'app-vehicles',
  templateUrl: './vehicles.component.html',
  styleUrls: ['./vehicles.component.css']
})
export class VehiclesComponent implements OnInit {

  vehicles: Array<Vehicle>;

  constructor(
    private vehicleService:VehicleService,
    private router:Router
  ) { }

  ngOnInit(): void {
    this.getAll();
  }

  open(id) {
    console.log(id)
    this.router.navigate(['/', id]);
  }

  getAll() {
    this.vehicleService.getAll().subscribe(
      list => {
        this.vehicles = list;
      },
      error => {

      }
    );
  }

}
