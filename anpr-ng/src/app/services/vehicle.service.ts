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

  public getAll(plate?:string): Observable<Array<Vehicle>> {
    if(plate)
      return this.http.get<Array<Vehicle>>(`${API}?plate=${plate}`);
    else
      return this.http.get<Array<Vehicle>>(`${API}`);
  }

  public get(id:string): Observable<Vehicle> {
    return this.http.get<Vehicle>(`${API}/${id}`);
  }

  public save(vehicle: Vehicle): Observable<Vehicle> {
    if(vehicle._id)
      return this.update(vehicle);
    else
      return this.insert(vehicle);
  }

  private insert(vehicle: Vehicle): Observable<Vehicle> {
    return this.http.post<Vehicle>(`${API}`, vehicle);
  }
  
  private update(vehicle: Vehicle): Observable<Vehicle> {
    return this.http.put<Vehicle>(`${API}/${vehicle._id}`, vehicle);
  }

}
