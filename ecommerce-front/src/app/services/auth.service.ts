import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import {User} from '../common/user';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private isAuthenticated = false;
  private token: string | undefined;
  private user: any ;
  private tokenTimer: any;
  private url: string = "http://localhost:8080/auth"

  constructor(private http: HttpClient, private router: Router) {}

  signup(user: User): Observable<{ jwt: any , id: string , role: string }> {
    return this.http.post<{ jwt: any , id: string , role: string }>(this.url + '/signup' , user);
  }

  login(email: string, password: string)  {
    return this.http.post<{ jwt: any , id: string , role: string }>(this.url + "/login", { email: email, password: password })
  }

  logout() {
    localStorage.clear()
    this.router.navigate(['']);
  }

  saveAuthData(token: string, id: string , role: string) {
    localStorage.setItem("token", token);
    localStorage.setItem("id" , id);
    localStorage.setItem("role" , role);
  }

  getUserId() {
    return localStorage.getItem("id")
  }
}
