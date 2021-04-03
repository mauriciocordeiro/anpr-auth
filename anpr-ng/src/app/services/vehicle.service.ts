import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { Vehicle } from '../model/vehicle';

const API = environment.apiVehicles;

@Injectable({
  providedIn: 'root'
})
export class VehicleService {

  constructor(private http: HttpClient, private router: Router) { }

  public getAll(): Observable<Array<Vehicle>> {
    return this.http.get<Array<Vehicle>>(`${API}`);
  }

  public get(id:string): Observable<Vehicle> {
    return this.http.get<Vehicle>(`${API}/${id}`);
  }

}
