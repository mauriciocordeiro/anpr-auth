import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { SnackBarService } from 'src/app/core/services/snackbar.service';
import { Vehicle } from 'src/app/model/vehicle';
import { VehicleService } from 'src/app/services/vehicle.service';

@Component({
  selector: 'app-vehicle-detail',
  templateUrl: './vehicle-detail.component.html',
  styleUrls: ['./vehicle-detail.component.css']
})
export class VehicleDetailComponent implements OnInit {

  formGroup:FormGroup;
  vehicle: Vehicle;

  constructor(
    private activatedRoute: ActivatedRoute, 
    private vServices: VehicleService,
    private snackBar: SnackBarService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.formGroup = this.build(new Vehicle());

    this.load();
  }

  onSubmit() {
    if(this.formGroup.invalid) {
      this.snackBar.alert("There are invalid fields.", "OK");
      return;
    }

    let v:Vehicle = this.formGroup.value;

    this.vServices.save(v).subscribe(
      _vehicle => {
        this.snackBar.success("Saved successfully!");
        this.router.navigate(['/vehicles'])
      },
      err => {
        let error = err.error;
        this.snackBar.error(error.message, error.status);
      }
    );
  }

  private load() {
    this.activatedRoute.paramMap.subscribe(params => {
      if(params.get('id')) {
        this.vServices.get(params.get('id'))
        .subscribe(
          vehicle => {
            this.vehicle = vehicle;
            this.formGroup = this.build(this.vehicle);
          },
          err => {
            let error = err.error;
            this.snackBar.error(error.message, error.status);
          }
        );
      }
    });
  }

  private build(vehicle:Vehicle):FormGroup {
    return new FormGroup({
      _id: new FormControl(vehicle._id),
      plate: new FormControl(vehicle.plate, [ Validators.required ]),
      brand: new FormControl(vehicle.brand),
      model: new FormControl(vehicle.model),
      owner: new FormControl(vehicle.owner),
      address: new FormControl(vehicle.address),
      phone: new FormControl(vehicle.phone, [ Validators.required ]),
      allowed: new FormControl(vehicle.allowed)
    });
  }

}
