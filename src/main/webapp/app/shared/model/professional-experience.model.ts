import { Moment } from 'moment';
import { IProject } from 'app/shared/model/project.model';
import { IPerson } from 'app/shared/model/person.model';

export interface IProfessionalExperience {
  id?: number;
  title?: string;
  company?: string;
  startDate?: Moment;
  endDate?: Moment;
  projects?: IProject[];
  person?: IPerson;
}

export class ProfessionalExperience implements IProfessionalExperience {
  constructor(
    public id?: number,
    public title?: string,
    public company?: string,
    public startDate?: Moment,
    public endDate?: Moment,
    public projects?: IProject[],
    public person?: IPerson
  ) {}
}
