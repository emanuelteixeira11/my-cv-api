import { Moment } from 'moment';
import { IPerson } from 'app/shared/model/person.model';

export interface ILocation {
  id?: number;
  country?: string;
  city?: string;
  fromDate?: Moment;
  toDate?: Moment;
  person?: IPerson;
}

export class Location implements ILocation {
  constructor(
    public id?: number,
    public country?: string,
    public city?: string,
    public fromDate?: Moment,
    public toDate?: Moment,
    public person?: IPerson
  ) {}
}
