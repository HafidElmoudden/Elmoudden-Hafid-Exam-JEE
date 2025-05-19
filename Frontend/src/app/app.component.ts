import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from './navbar/navbar.component';
import { AuthService } from './services/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  standalone: true,
  imports: [RouterModule, CommonModule, NavbarComponent]
})
export class AppComponent implements OnInit {
  title = 'Frontend';

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    this.authService.loadJwtTokenFromLocalStorage();
  }
}
