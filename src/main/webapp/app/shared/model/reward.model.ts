import { Moment } from 'moment';
import { IPerson } from 'app/shared/model/person.model';

export interface IReward {
  id?: number;
  title?: string;
  date?: Moment;
  issuerEntity?: string;
  shortDescription?: string;
  person?: IPerson;
}

export class Reward implements IReward {
  constructor(
    public id?: number,
    public title?: string,
    public date?: Moment,
    public issuerEntity?: string,
    public shortDescription?: string,
    public person?: IPerson
  ) {}
}
