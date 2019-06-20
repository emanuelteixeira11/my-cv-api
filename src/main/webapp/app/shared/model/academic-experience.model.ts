import { Moment } from 'moment';
import { IPerson } from 'app/shared/model/person.model';

export interface IAcademicExperience {
  id?: number;
  school?: string;
  degree?: string;
  startDate?: Moment;
  endDate?: Moment;
  classification?: number;
  person?: IPerson;
}

export class AcademicExperience implements IAcademicExperience {
  constructor(
    public id?: number,
    public school?: string,
    public degree?: string,
    public startDate?: Moment,
    public endDate?: Moment,
    public classification?: number,
    public person?: IPerson
  ) {}
}
