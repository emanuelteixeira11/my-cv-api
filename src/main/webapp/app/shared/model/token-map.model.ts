import { Moment } from 'moment';
import { IPerson } from 'app/shared/model/person.model';

export interface ITokenMap {
  id?: number;
  token?: string;
  entityName?: string;
  validTo?: Moment;
  person?: IPerson;
}

export class TokenMap implements ITokenMap {
  constructor(public id?: number, public token?: string, public entityName?: string, public validTo?: Moment, public person?: IPerson) {}
}
