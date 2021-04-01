import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Router } from '@angular/router';
import { JwtHelperService } from "@auth0/angular-jwt";
import { Credencial } from '../model/credencial';
import { Autorizacao } from '../model/autorizacao';

const jwtHelper = new JwtHelperService();
const API = environment.api;

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient, private router: Router) { }

  public login(credencial: Credencial) : Observable<Autorizacao> {
    return this.http.post<any>(API+'/auth', credencial);
  }

  public isLoggedIn(){
    let token = this.getToken();
    return token !== null;
  }

  public isTokenValid(): boolean {
    return this.getToken()!==null && !jwtHelper.isTokenExpired(this.getToken());
  }

  public getToken(): string {
    if(this.getAutorizacao())
      return this.getAutorizacao().token
    return null;
  }

  public getAutorizacao(): Autorizacao {
    return JSON.parse(sessionStorage.getItem('autorizacao'));
  }

  public setAutorizacao(autorizacao:Autorizacao) {
    sessionStorage.setItem('autorizacao', JSON.stringify(autorizacao));
  }

  public logout(){
    sessionStorage.removeItem('autorizacao');
    this.router.navigateByUrl('login');
  }

}
