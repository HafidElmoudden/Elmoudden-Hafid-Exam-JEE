export interface Remboursement {
  id?: number;
  date: Date;
  montant: number;
  type: TypeRemboursement;
  creditId: number;
}

export enum TypeRemboursement {
  MENSUALITE = 'MENSUALITE',
  ANTICIPE = 'ANTICIPE'
} 