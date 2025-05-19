import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { NotAuthorizedComponent } from './not-authorized/not-authorized.component';
import { authenticationGuard } from './guards/authentication.guard';
import { ClientListComponent } from './components/clients/client-list/client-list.component';
import { ClientFormComponent } from './components/clients/client-form/client-form.component';
import { CreditListComponent } from './components/credits/credit-list/credit-list.component';
import { CreditFormComponent } from './components/credits/credit-form/credit-form.component';
import { RemboursementListComponent } from './components/remboursements/remboursement-list/remboursement-list.component';
import { RemboursementFormComponent } from './components/remboursements/remboursement-form/remboursement-form.component';
import { HomeComponent } from './home/home.component';

export const routes: Routes = [
  { path: "", redirectTo: "home", pathMatch: "full" },
  { path: "home", component: HomeComponent, canActivate: [authenticationGuard] },
  { path: "login", component: LoginComponent },
  { path: "not-authorized", component: NotAuthorizedComponent },
  { path: "clients", component: ClientListComponent, canActivate: [authenticationGuard] },
  { path: "clients/new", component: ClientFormComponent, canActivate: [authenticationGuard] },
  { path: "clients/:id/edit", component: ClientFormComponent, canActivate: [authenticationGuard] },
  { path: "credits", component: CreditListComponent, canActivate: [authenticationGuard] },
  { path: "credits/new", component: CreditFormComponent, canActivate: [authenticationGuard] },
  { path: "credits/:id/edit", component: CreditFormComponent, canActivate: [authenticationGuard] },
  { path: "clients/:clientId/credits", component: CreditListComponent, canActivate: [authenticationGuard] },
  { path: "clients/:clientId/credits/new", component: CreditFormComponent, canActivate: [authenticationGuard] },
  { path: "credits/:creditId/remboursements", component: RemboursementListComponent, canActivate: [authenticationGuard] },
  { path: "credits/:creditId/remboursements/new", component: RemboursementFormComponent, canActivate: [authenticationGuard] },
  { path: "credits/:creditId/remboursements/:id/edit", component: RemboursementFormComponent, canActivate: [authenticationGuard] },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
