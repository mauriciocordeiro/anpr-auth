import { Component, OnInit } from '@angular/core';
import { SnackBarService } from 'src/app/core/services/snackbar.service';
import { Vehicle } from 'src/app/model/vehicle';
import { AuthorizationService } from 'src/app/services/authorization.service';

@Component({
  selector: 'app-authorization',
  templateUrl: './authorization.component.html',
  styleUrls: ['./authorization.component.css']
})
export class AuthorizationComponent implements OnInit {

  file: File = null;
  image;

  vehicle: Vehicle;

  constructor(
    private authorizationService: AuthorizationService,
    private snackBar: SnackBarService
  ) { }

  ngOnInit(): void {
  }

  onChange(event) {
    this.vehicle = null;

    this.file = event.target.files[0];

    let reader = new FileReader();
    reader.onload = (e: any) => {
      this.image = e.target.result;
    }
    reader.readAsDataURL(event.target.files[0]);

    this.onUpload();
  }

  onUpload() {
    this.authorizationService.check(this.file).subscribe(
      vehicle => {
        this.vehicle = vehicle;
        this.snackBar.success(vehicle.allowed ? 'Allowed' : 'NOT Allowed', vehicle.plate)
      },
      err => {
        this.vehicle = null;
        console.log(err);
      }
    );
  }

}
