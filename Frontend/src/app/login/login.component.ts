import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../services/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit {
  loginFormGroup!:FormGroup;
  loginError!:string;
  hidePassword: boolean = true;
  constructor(private fb:FormBuilder,private authService:AuthService,private router:Router) {

  }

  ngOnInit(): void {
    this.loginFormGroup = this.fb.group({
      username: [null, [Validators.required, Validators.minLength(4)]],
      password: [null, [Validators.required, Validators.minLength(4)]]
    });
  }

  handleLogin() {
    let username=this.loginFormGroup.value.username;
    let password=this.loginFormGroup.value.password;
    this.authService.login(username, password).subscribe({
        next: (data) => {
          this.authService.loadProfile(data);
          this.router.navigateByUrl("/admin");
        },
        error: (err) => {
          this.loginError = err.message;
        }
      }
    )
  }
}
