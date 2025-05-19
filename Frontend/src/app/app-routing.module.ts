import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from './login/login.component';
import {NotAuthorizedComponent} from './not-authorized/not-authorized.component';
import {authenticationGuard} from './guards/authentication.guard';
import {AdminTemplateComponent} from './admin-template/admin-template.component';

const routes: Routes = [
  { path: "", redirectTo: "login", pathMatch: "full" },
  { path: "login", component: LoginComponent },
  { path: "not-authorized", component: NotAuthorizedComponent },
  {
    path: "admin",
    component: AdminTemplateComponent,
    canActivate: [authenticationGuard],
    children: [
      { path: "", redirectTo: "home", pathMatch: "full" },
  ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
