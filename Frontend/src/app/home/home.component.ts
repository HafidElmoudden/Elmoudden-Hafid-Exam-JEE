import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <div class="container">
      <div class="content mt-4">
        <div class="jumbotron">
          <h1 class="display-4">Système de Gestion de Crédits Bancaires</h1>
          <p class="lead">Bienvenue dans votre application de gestion de crédits bancaires.</p>
          <hr class="my-4">
          <p>Utilisez la barre de navigation pour accéder aux différentes fonctionnalités.</p>
          <div class="mt-4">
            <a [routerLink]="['/clients']" class="btn btn-primary me-2">
              <i class="bi bi-people"></i> Gestion des Clients
            </a>
            <a [routerLink]="['/credits']" class="btn btn-success me-2">
              <i class="bi bi-cash"></i> Gestion des Crédits
            </a>
          </div>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .container {
      max-width: 1200px;
      margin: 0 auto;
    }
    .jumbotron {
      padding: 2rem;
      background-color: #f8f9fa;
      border-radius: 0.3rem;
    }
    .content {
      padding: 20px;
    }
  `]
})
export class HomeComponent { } 