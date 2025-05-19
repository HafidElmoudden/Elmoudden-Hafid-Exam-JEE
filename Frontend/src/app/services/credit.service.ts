import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Credit, CreditImmobilier, CreditPersonnel, CreditProfessionnel, Statut } from '../models/credit.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CreditService {
  private apiUrl = environment.apiUrl + '/credits';

  constructor(private http: HttpClient) { }

  getAllCredits(): Observable<Credit[]> {
    return this.http.get<Credit[]>(this.apiUrl);
  }

  getCreditById(id: number): Observable<Credit> {
    return this.http.get<Credit>(`${this.apiUrl}/${id}`);
  }

  getCreditsByClientId(clientId: number): Observable<Credit[]> {
    return this.http.get<Credit[]>(`${this.apiUrl}/client/${clientId}`);
  }

  getCreditsByStatut(statut: Statut): Observable<Credit[]> {
    return this.http.get<Credit[]>(`${this.apiUrl}/status/${statut}`);
  }

  updateCreditStatut(id: number, statut: Statut): Observable<Credit> {
    return this.http.patch<Credit>(`${this.apiUrl}/${id}/status?statut=${statut}`, {});
  }

  deleteCredit(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  create(credit: Credit): Observable<Credit> {
    const url = `${this.apiUrl}/${credit.type?.toLowerCase()}`;
    return this.http.post<Credit>(url, credit);
  }

  update(credit: Credit): Observable<Credit> {
    const url = `${this.apiUrl}/${credit.type?.toLowerCase()}/${credit.id}`;
    return this.http.put<Credit>(url, credit);
  }

  // Credit Personnel methods
  createCreditPersonnel(credit: CreditPersonnel): Observable<CreditPersonnel> {
    return this.http.post<CreditPersonnel>(`${this.apiUrl}/personnel`, credit);
  }

  updateCreditPersonnel(credit: CreditPersonnel): Observable<CreditPersonnel> {
    return this.http.put<CreditPersonnel>(`${this.apiUrl}/personnel/${credit.id}`, credit);
  }

  getCreditPersonnelByMotif(motif: string): Observable<CreditPersonnel[]> {
    return this.http.get<CreditPersonnel[]>(`${this.apiUrl}/personnel/motif/${motif}`);
  }

  // Credit Immobilier methods
  createCreditImmobilier(credit: CreditImmobilier): Observable<CreditImmobilier> {
    return this.http.post<CreditImmobilier>(`${this.apiUrl}/immobilier`, credit);
  }

  updateCreditImmobilier(credit: CreditImmobilier): Observable<CreditImmobilier> {
    return this.http.put<CreditImmobilier>(`${this.apiUrl}/immobilier/${credit.id}`, credit);
  }

  // Credit Professionnel methods
  createCreditProfessionnel(credit: CreditProfessionnel): Observable<CreditProfessionnel> {
    return this.http.post<CreditProfessionnel>(`${this.apiUrl}/professionnel`, credit);
  }

  updateCreditProfessionnel(credit: CreditProfessionnel): Observable<CreditProfessionnel> {
    return this.http.put<CreditProfessionnel>(`${this.apiUrl}/professionnel/${credit.id}`, credit);
  }

  // Statistics methods
  getCreditStatistics(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/statistics`);
  }
}