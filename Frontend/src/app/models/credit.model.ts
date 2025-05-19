export interface Credit {
  id?: number;
  dateDemande: Date;
  statut: string;
  dateAcception?: Date;
  montant: number;
  dureeRemboursement: number;
  tauxInteret: number;
  clientId: number;
  clientNom?: string;
  type?: string;
  remboursements?: any[];
}

export interface CreditImmobilier extends Credit {
  typeBien: string;
  tauxAssurance: number;
  objetFinance: string;
}

export interface CreditPersonnel extends Credit {
  motif: string;
}

export interface CreditProfessionnel extends Credit {
  motif: string;
  secteurActivite: string;
  raisonSocialeEntreprise: string;
}

export type Statut = 'EN_COURS' | 'ACCEPTE' | 'REFUSE' | 'TERMINE';

export enum TypeRemboursement {
  MENSUALITE = 'MENSUALITE',
  ANTICIPE = 'ANTICIPE'
}

export interface Remboursement {
  id?: number;
  date: Date;
  montant: number;
  type: TypeRemboursement;
  creditId: number;
} 