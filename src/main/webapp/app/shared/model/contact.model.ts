import { IPerson } from 'app/shared/model/person.model';

export const enum ContactType {
  EMAIL = 'EMAIL',
  MOBILE = 'MOBILE',
  WEBSITE = 'WEBSITE',
  LINKEDIN = 'LINKEDIN'
}

export interface IContact {
  id?: number;
  type?: ContactType;
  value?: string;
  isActive?: boolean;
  person?: IPerson;
}

export class Contact implements IContact {
  constructor(public id?: number, public type?: ContactType, public value?: string, public isActive?: boolean, public person?: IPerson) {
    this.isActive = this.isActive || false;
  }
}
