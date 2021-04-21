import { Component, OnInit } from '@angular/core';
import { SnackBarService } from 'src/app/core/services/snackbar.service';
import { AuthorizationService } from 'src/app/services/authorization.service';

@Component({
  selector: 'app-authorization',
  templateUrl: './authorization.component.html',
  styleUrls: ['./authorization.component.css']
})
export class AuthorizationComponent implements OnInit {

  file: File = null;

  constructor(
    private authorizationService:AuthorizationService,
    private snackBar: SnackBarService
    ) { }

  ngOnInit(): void {
  }

  onChange(event) {
    this.file = event.target.files[0];
  }

  onUpload() {
    this.authorizationService.check(this.file).subscribe(
      vehicle => {
        this.snackBar.success(vehicle.allowed ? 'OK' : 'NOT OK', vehicle.plate)
      },
      err => {
        console.log(err);
      }
    );
  }

}
