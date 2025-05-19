import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {jwtDecode} from 'jwt-decode';
import {Router} from '@angular/router';
import {environment} from '../../environments/environment';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  isAuthenticated:boolean=false;
  roles:any;
  username:any;
  accessToken!:any;

  constructor(private http:HttpClient,private router:Router) { }

  public login(credentials: {username: string, password: string}): Observable<any> {
    let options = {
      headers: new HttpHeaders().set("Content-type", "application/x-www-form-urlencoded")
    };
    let params = new HttpParams()
      .set('username', credentials.username)
      .set('password', credentials.password);
    
    return this.http.post(environment.apiUrl + "/auth/login", params, options)
      .pipe(
        tap(data => this.loadProfile(data))
      );
  }

  public logout(){
    this.isAuthenticated=false;
    this.roles=undefined;
    this.username=undefined;
    this.accessToken=undefined;
    window.localStorage.removeItem('jwt-token');
    this.router.navigateByUrl('/login').then();
  }

  loadProfile(data: any) {
    this.accessToken = data['access-token'];

    console.log('Access token received:', this.accessToken);

    if (typeof this.accessToken === 'string' && this.accessToken.trim() !== '') {
      this.isAuthenticated = true;
      const decodedJwt = jwtDecode(this.accessToken) as any;
      this.username = decodedJwt.sub;
      this.roles = decodedJwt.scope;
      window.localStorage.setItem('jwt-token', this.accessToken);
    } else {
      console.error('Invalid access token received:', this.accessToken);
      this.isAuthenticated = false;
    }
  }
  loadJwtTokenFromLocalStorage(){
    let token=window.localStorage.getItem('jwt-token');
    if(token){
      this.loadProfile({ 'access-token': token });
      console.log("Token loaded from local storage:", token);
      //this.router.navigateByUrl('/home').then();
    }
  }
}
