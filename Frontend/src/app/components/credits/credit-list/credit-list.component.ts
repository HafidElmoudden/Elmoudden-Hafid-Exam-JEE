import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, ActivatedRoute, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Credit } from '../../../models/credit.model';
import { CreditService } from '../../../services/credit.service';
import { ClientService } from '../../../services/client.service';
import { Client } from '../../../models/client.model';

@Component({
  selector: 'app-credit-list',
  templateUrl: './credit-list.component.html',
  styleUrls: ['./credit-list.component.css'],
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule]
})
export class CreditListComponent implements OnInit {
  credits: Credit[] = [];
  client: Client | null = null;
  clientId?: number;
  loading: boolean = false;
  error: string | null = null;
  
  constructor(
    private creditService: CreditService,
    private clientService: ClientService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      if (params['clientId']) {
        this.clientId = +params['clientId'];
        this.loadClientCredits(this.clientId);
        this.loadClientDetails(this.clientId);
      } else {
        this.loadAllCredits();
      }
    });
  }

  loadAllCredits(): void {
    this.loading = true;
    this.error = null;
    this.creditService.getAllCredits().subscribe({
      next: (data) => {
        this.credits = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Erreur lors du chargement des crédits: ' + err.message;
        this.loading = false;
      }
    });
  }

  loadClientCredits(clientId: number): void {
    this.loading = true;
    this.error = null;
    this.creditService.getCreditsByClientId(clientId).subscribe({
      next: (data) => {
        this.credits = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Erreur lors du chargement des crédits du client: ' + err.message;
        this.loading = false;
      }
    });
  }

  loadClientDetails(clientId: number): void {
    this.clientService.getClientById(clientId).subscribe({
      next: (data) => {
        this.client = data;
      },
      error: (err) => {
        this.error = 'Erreur lors du chargement des détails du client: ' + err.message;
      }
    });
  }

  deleteCredit(id: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer ce crédit?')) {
      this.creditService.deleteCredit(id).subscribe({
        next: () => {
          this.credits = this.credits.filter(credit => credit.id !== id);
        },
        error: (err) => {
          this.error = 'Erreur lors de la suppression: ' + err.message;
        }
      });
    }
  }

  getCreditTypeName(credit: Credit): string {
    if (credit.type === 'IMMOBILIER') {
      return 'Immobilier';
    } else if (credit.type === 'PERSONNEL') {
      return 'Personnel';
    } else if (credit.type === 'PROFESSIONNEL') {
      return 'Professionnel';
    }
    return 'Inconnu';
  }

  getStatusLabel(status: string): string {
    switch (status) {
      case 'EN_COURS': return 'En cours';
      case 'ACCEPTE': return 'Accepté';
      case 'REFUSE': return 'Refusé';
      case 'TERMINE': return 'Terminé';
      default: return status;
    }
  }

  getStatusClass(status: string): string {
    switch (status) {
      case 'EN_COURS': return 'badge bg-warning';
      case 'ACCEPTE': return 'badge bg-success';
      case 'REFUSE': return 'badge bg-danger';
      case 'TERMINE': return 'badge bg-secondary';
      default: return 'badge bg-info';
    }
  }
} 