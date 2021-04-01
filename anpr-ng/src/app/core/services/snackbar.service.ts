import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class SnackBarService {

  constructor(private snackBar: MatSnackBar) { }

  alert(message: string, action: string = 'OK', duration:number = 2000) {
    this.snackBar.open(message, action, 
      { duration: duration, panelClass: ['alert-snackbar'] });
  }

  error(message: string, action: string = 'OK', duration:number = 2000) {
    this.snackBar.open(message, action, 
      { duration: duration, panelClass: ['error-snackbar'] });
  }

  success(message: string, action: string = 'OK', duration:number = 2000) {
    this.snackBar.open(message, action, 
      { duration: duration, panelClass: ['success-snackbar'] });
  }
}
