import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { Client } from '../../../models/client.model';
import { ClientService } from '../../../services/client.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-client-form',
  templateUrl: './client-form.component.html',
  styleUrls: ['./client-form.component.css'],
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule]
})
export class ClientFormComponent implements OnInit {
  clientForm!: FormGroup;
  clientId?: number;
  isEditMode: boolean = false;
  loading: boolean = false;
  error: string | null = null;
  submitted: boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private clientService: ClientService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.initForm();
    
    this.route.params.subscribe(params => {
      if (params['id']) {
        this.clientId = +params['id'];
        this.isEditMode = true;
        this.loadClient(this.clientId);
      }
    });
  }

  initForm(): void {
    this.clientForm = this.formBuilder.group({
      nom: ['', [Validators.required, Validators.minLength(2)]],
      email: ['', [Validators.required, Validators.email]]
    });
  }

  loadClient(id: number): void {
    this.loading = true;
    this.clientService.getClientById(id).subscribe({
      next: (client) => {
        this.clientForm.patchValue({
          nom: client.nom,
          email: client.email
        });
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Erreur lors du chargement du client: ' + err.message;
        this.loading = false;
      }
    });
  }

  onSubmit(): void {
    this.submitted = true;

    if (this.clientForm.invalid) {
      return;
    }

    const client: Client = {
      ...this.clientForm.value
    };

    this.loading = true;
    this.error = null;

    if (this.isEditMode && this.clientId) {
      client.id = this.clientId;
      this.clientService.updateClient(client).subscribe({
        next: () => {
          this.router.navigate(['/clients']);
        },
        error: (err) => {
          this.error = 'Erreur lors de la mise à jour du client: ' + err.message;
          this.loading = false;
        }
      });
    } else {
      this.clientService.createClient(client).subscribe({
        next: () => {
          this.router.navigate(['/clients']);
        },
        error: (err) => {
          this.error = 'Erreur lors de la création du client: ' + err.message;
          this.loading = false;
        }
      });
    }
  }

  get f() { return this.clientForm.controls; }
} 