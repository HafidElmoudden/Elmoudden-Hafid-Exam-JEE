import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { Remboursement, TypeRemboursement } from '../../../models/remboursement.model';
import { RemboursementService } from '../../../services/remboursement.service';
import { CreditService } from '../../../services/credit.service';
import { Credit } from '../../../models/credit.model';

@Component({
  selector: 'app-remboursement-form',
  templateUrl: './remboursement-form.component.html',
  styleUrls: ['./remboursement-form.component.css'],
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule]
})
export class RemboursementFormComponent implements OnInit {
  remboursementForm!: FormGroup;
  remboursementId?: number;
  creditId!: number;
  credit: Credit | null = null;
  isEditMode: boolean = false;
  loading: boolean = false;
  error: string | null = null;
  submitted: boolean = false;
  
  typeOptions = [
    { value: TypeRemboursement.MENSUALITE, label: 'Mensualité' },
    { value: TypeRemboursement.ANTICIPE, label: 'Anticipé' }
  ];

  constructor(
    private formBuilder: FormBuilder,
    private remboursementService: RemboursementService,
    private creditService: CreditService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.initForm();
    
    this.route.params.subscribe(params => {
      if (params['creditId']) {
        this.creditId = +params['creditId'];
        this.loadCreditDetails();
        this.remboursementForm.patchValue({ creditId: this.creditId });
      }
      
      if (params['id']) {
        this.remboursementId = +params['id'];
        this.isEditMode = true;
        this.loadRemboursement(this.remboursementId);
      }
    });
  }

  initForm(): void {
    this.remboursementForm = this.formBuilder.group({
      creditId: ['', Validators.required],
      date: [new Date().toISOString().split('T')[0], Validators.required],
      montant: ['', [Validators.required, Validators.min(1)]],
      type: [TypeRemboursement.MENSUALITE, Validators.required]
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

  loadRemboursement(id: number): void {
    this.loading = true;
    this.remboursementService.getRemboursementById(id).subscribe({
      next: (remboursement) => {
        const formData = {
          creditId: remboursement.creditId,
          date: new Date(remboursement.date).toISOString().split('T')[0],
          montant: remboursement.montant,
          type: remboursement.type
        };
        
        this.remboursementForm.patchValue(formData);
        this.loading = false;
      },
      error: (err: any) => {
        this.error = 'Erreur lors du chargement du remboursement: ' + err.message;
        this.loading = false;
      }
    });
  }

  onSubmit(): void {
    this.submitted = true;

    if (this.remboursementForm.invalid) {
      return;
    }

    const formValues = this.remboursementForm.value;
    const remboursement: Remboursement = {
      creditId: formValues.creditId,
      date: new Date(formValues.date),
      montant: formValues.montant,
      type: formValues.type
    };

    this.loading = true;
    this.error = null;

    if (this.isEditMode && this.remboursementId) {
      remboursement.id = this.remboursementId;
      this.remboursementService.updateRemboursement(remboursement).subscribe({
        next: () => {
          this.navigateAfterSave();
        },
        error: (err: any) => {
          this.error = 'Erreur lors de la mise à jour du remboursement: ' + err.message;
          this.loading = false;
        }
      });
    } else {
      this.remboursementService.createRemboursement(remboursement).subscribe({
        next: () => {
          this.navigateAfterSave();
        },
        error: (err: any) => {
          this.error = 'Erreur lors de la création du remboursement: ' + err.message;
          this.loading = false;
        }
      });
    }
  }

  navigateAfterSave(): void {
    this.router.navigate(['/admin/credits', this.creditId, 'remboursements']);
  }

  get f() { return this.remboursementForm.controls; }
} 