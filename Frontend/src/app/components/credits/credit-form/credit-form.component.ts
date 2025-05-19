import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { Credit, CreditImmobilier, CreditPersonnel, CreditProfessionnel } from '../../../models/credit.model';
import { CreditService } from '../../../services/credit.service';
import { ClientService } from '../../../services/client.service';
import { Client } from '../../../models/client.model';

@Component({
  selector: 'app-credit-form',
  templateUrl: './credit-form.component.html',
  styleUrls: ['./credit-form.component.css'],
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule]
})
export class CreditFormComponent implements OnInit {
  creditForm!: FormGroup;
  creditId?: number;
  clientId?: number;
  isEditMode: boolean = false;
  loading: boolean = false;
  error: string | null = null;
  submitted: boolean = false;
  creditTypes = [
    { value: 'PERSONNEL', label: 'Personnel' },
    { value: 'IMMOBILIER', label: 'Immobilier' },
    { value: 'PROFESSIONNEL', label: 'Professionnel' }
  ];
  statusOptions = [
    { value: 'EN_COURS', label: 'En cours' },
    { value: 'ACCEPTE', label: 'Accepté' },
    { value: 'REFUSE', label: 'Refusé' },
    { value: 'TERMINE', label: 'Terminé' }
  ];
  clients: Client[] = [];

  constructor(
    private formBuilder: FormBuilder,
    private creditService: CreditService,
    private clientService: ClientService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loadClients();
    this.initForm();
    
    this.route.params.subscribe(params => {
      if (params['clientId']) {
        this.clientId = +params['clientId'];
        this.creditForm.patchValue({ clientId: this.clientId });
      }
      
      if (params['id']) {
        this.creditId = +params['id'];
        this.isEditMode = true;
        this.loadCredit(this.creditId);
      }
    });
  }

  initForm(): void {
    this.creditForm = this.formBuilder.group({
      clientId: ['', Validators.required],
      type: ['', Validators.required],
      montant: ['', [Validators.required, Validators.min(1000)]],
      dureeRemboursement: ['', [Validators.required, Validators.min(1), Validators.max(360)]],
      tauxInteret: ['', [Validators.required, Validators.min(0.001), Validators.max(0.2)]],
      statut: ['EN_COURS', Validators.required],
      // Champs spécifiques au type de crédit
      motif: [''],
      typeBien: [''],
      tauxAssurance: [''],
      objetFinance: [''],
      secteurActivite: [''],
      raisonSocialeEntreprise: ['']
    });

    // Réagir aux changements du type de crédit
    this.creditForm.get('type')?.valueChanges.subscribe(type => {
      this.updateFormValidators(type);
    });
  }

  updateFormValidators(creditType: string): void {
    const motifControl = this.creditForm.get('motif');
    const typeBienControl = this.creditForm.get('typeBien');
    const tauxAssuranceControl = this.creditForm.get('tauxAssurance');
    const objetFinanceControl = this.creditForm.get('objetFinance');
    const secteurActiviteControl = this.creditForm.get('secteurActivite');
    const raisonSocialeEntrepriseControl = this.creditForm.get('raisonSocialeEntreprise');
    
    // Réinitialiser les validateurs
    [motifControl, typeBienControl, tauxAssuranceControl, objetFinanceControl, 
     secteurActiviteControl, raisonSocialeEntrepriseControl].forEach(control => {
      if (control) {
        control.clearValidators();
        control.updateValueAndValidity();
      }
    });
    
    // Appliquer les validateurs selon le type
    if (creditType === 'IMMOBILIER') {
      typeBienControl?.setValidators([Validators.required]);
      tauxAssuranceControl?.setValidators([Validators.required, Validators.min(0), Validators.max(0.05)]);
      objetFinanceControl?.setValidators([Validators.required]);
    } else if (creditType === 'PERSONNEL') {
      motifControl?.setValidators([Validators.required]);
    } else if (creditType === 'PROFESSIONNEL') {
      motifControl?.setValidators([Validators.required]);
      secteurActiviteControl?.setValidators([Validators.required]);
      raisonSocialeEntrepriseControl?.setValidators([Validators.required]);
    }
    
    [motifControl, typeBienControl, tauxAssuranceControl, objetFinanceControl, 
     secteurActiviteControl, raisonSocialeEntrepriseControl].forEach(control => {
      if (control) control.updateValueAndValidity();
    });
  }

  loadClients(): void {
    this.clientService.getAllClients().subscribe({
      next: (clients) => {
        this.clients = clients;
      },
      error: (err: any) => {
        this.error = 'Erreur lors du chargement des clients: ' + err.message;
      }
    });
  }

  loadCredit(id: number): void {
    this.loading = true;
    this.creditService.getCreditById(id).subscribe({
      next: (credit) => {
        // Create formData as Record type to allow indexing
        const formData: Record<string, any> = {
          clientId: credit.clientId,
          type: credit.type,
          montant: credit.montant,
          dureeRemboursement: credit.dureeRemboursement,
          tauxInteret: credit.tauxInteret,
          statut: credit.statut
        };

        if (credit.type === 'IMMOBILIER') {
          const immobilier = credit as CreditImmobilier;
          formData['typeBien'] = immobilier.typeBien;
          formData['tauxAssurance'] = immobilier.tauxAssurance;
          formData['objetFinance'] = immobilier.objetFinance;
        } else if (credit.type === 'PROFESSIONNEL') {
          const professionnel = credit as CreditProfessionnel;
          formData['motif'] = professionnel.motif;
          formData['secteurActivite'] = professionnel.secteurActivite;
          formData['raisonSocialeEntreprise'] = professionnel.raisonSocialeEntreprise;
        } else if (credit.type === 'PERSONNEL') {
          const personnel = credit as CreditPersonnel;
          formData['motif'] = personnel.motif;
        }
        
        this.creditForm.patchValue(formData);
        this.updateFormValidators(credit.type || '');
        this.loading = false;
      },
      error: (err: any) => {
        this.error = 'Erreur lors du chargement du crédit: ' + err.message;
        this.loading = false;
      }
    });
  }

  onSubmit(): void {
    this.submitted = true;

    if (this.creditForm.invalid) {
      return;
    }

    const formValues = this.creditForm.value;
    const baseCredit: Credit = {
      clientId: formValues.clientId,
      montant: formValues.montant,
      dureeRemboursement: formValues.dureeRemboursement,
      tauxInteret: formValues.tauxInteret,
      statut: formValues.statut,
      dateDemande: new Date(),
      type: formValues.type
    };

    let credit: Credit | CreditImmobilier | CreditPersonnel | CreditProfessionnel = baseCredit;

    // Ajouter les champs spécifiques selon le type
    if (formValues.type === 'IMMOBILIER') {
      credit = {
        ...baseCredit,
        typeBien: formValues.typeBien,
        tauxAssurance: formValues.tauxAssurance,
        objetFinance: formValues.objetFinance
      } as CreditImmobilier;
    } else if (formValues.type === 'PROFESSIONNEL') {
      credit = {
        ...baseCredit,
        motif: formValues.motif,
        secteurActivite: formValues.secteurActivite,
        raisonSocialeEntreprise: formValues.raisonSocialeEntreprise
      } as CreditProfessionnel;
    } else if (formValues.type === 'PERSONNEL') {
      credit = {
        ...baseCredit,
        motif: formValues.motif
      } as CreditPersonnel;
    }

    this.loading = true;
    this.error = null;

    if (this.isEditMode && this.creditId) {
      credit.id = this.creditId;
      this.creditService.update(credit).subscribe({
        next: () => {
          this.navigateAfterSave();
        },
        error: (err: any) => {
          this.error = 'Erreur lors de la mise à jour du crédit: ' + err.message;
          this.loading = false;
        }
      });
    } else {
      this.creditService.create(credit).subscribe({
        next: () => {
          this.navigateAfterSave();
        },
        error: (err: any) => {
          this.error = 'Erreur lors de la création du crédit: ' + err.message;
          this.loading = false;
        }
      });
    }
  }

  navigateAfterSave(): void {
    if (this.clientId) {
      this.router.navigate(['/admin/clients', this.clientId, 'credits']);
    } else {
      this.router.navigate(['/admin/credits']);
    }
  }

  get f() { return this.creditForm.controls; }
}