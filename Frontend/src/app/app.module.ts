import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { NotAuthorizedComponent } from './not-authorized/not-authorized.component';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { HttpClientModule, provideHttpClient, withInterceptors } from '@angular/common/http';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatMenuModule } from '@angular/material/menu';
import { RouterModule } from '@angular/router';

// Import standalone components
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { NavbarComponent } from './navbar/navbar.component';
import { HomeComponent } from './home/home.component';
import { ClientListComponent } from './components/clients/client-list/client-list.component';
import { ClientFormComponent } from './components/clients/client-form/client-form.component';
import { CreditListComponent } from './components/credits/credit-list/credit-list.component';
import { CreditFormComponent } from './components/credits/credit-form/credit-form.component';
import { appHttpInterceptor } from './interceptors/app-http.interceptor';

@NgModule({
  declarations: [
    NotAuthorizedComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    RouterModule,
    MatCardModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    FormsModule,
    MatIconModule,
    MatInputModule,
    MatButtonModule,
    MatToolbarModule,
    MatMenuModule,
    // Standalone components
    AppComponent,
    LoginComponent,
    NavbarComponent,
    HomeComponent,
    ClientListComponent,
    ClientFormComponent,
    CreditListComponent,
    CreditFormComponent
  ],
  providers: [provideHttpClient(withInterceptors([appHttpInterceptor]))],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppModule { }
