import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Remboursement, TypeRemboursement } from '../../../models/remboursement.model';
import { RemboursementService } from '../../../services/remboursement.service';
import { CreditService } from '../../../services/credit.service';
import { Credit } from '../../../models/credit.model';

@Component({
  selector: 'app-remboursement-list',
  templateUrl: './remboursement-list.component.html',
  styleUrls: ['./remboursement-list.component.css'],
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule]
})
export class RemboursementListComponent implements OnInit {
  remboursements: Remboursement[] = [];
  credit: Credit | null = null;
  creditId!: number;
  loading: boolean = false;
  error: string | null = null;

  constructor(
    private remboursementService: RemboursementService,
    private creditService: CreditService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      if (params['creditId']) {
        this.creditId = +params['creditId'];
        this.loadRemboursements();
        this.loadCreditDetails();
      }
    });
  }

  loadRemboursements(): void {
    this.loading = true;
    this.error = null;
    this.remboursementService.getRemboursementsByCreditId(this.creditId).subscribe({
      next: (data) => {
        this.remboursements = data;
        this.loading = false;
      },
      error: (err: any) => {
        this.error = 'Erreur lors du chargement des remboursements: ' + err.message;
        this.loading = false;
      }
    });
  }

  loadCreditDetails(): void {
    this.creditService.getCreditById(this.creditId).subscribe({
      next: (data) => {
        this.credit = data;
      },
      error: (err: any) => {
        this.error = 'Erreur lors du chargement des détails du crédit: ' + err.message;
      }
    });
  }

  deleteRemboursement(id: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer ce remboursement?')) {
      this.remboursementService.deleteRemboursement(id).subscribe({
        next: () => {
          this.remboursements = this.remboursements.filter(r => r.id !== id);
        },
        error: (err: any) => {
          this.error = 'Erreur lors de la suppression: ' + err.message;
        }
      });
    }
  }

  getTypeLabel(type: TypeRemboursement): string {
    switch (type) {
      case TypeRemboursement.MENSUALITE: return 'Mensualité';
      case TypeRemboursement.ANTICIPE: return 'Anticipé';
      default: return type;
    }
  }
} 