import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Vehicle } from '../model/vehicle';

const API = environment.apiCheck;

@Injectable({
  providedIn: 'root'
})
export class AuthorizationService {

  constructor(private http: HttpClient) { }

  public check(image:File): Observable<Vehicle> {
    var formData: any = new FormData();
    formData.append("image", image);

    return this.http.post<Vehicle>(`${API}/check`, formData);
  }


}
