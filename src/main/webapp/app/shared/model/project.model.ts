import { Moment } from 'moment';
import { ITechnology } from 'app/shared/model/technology.model';
import { IProfessionalExperience } from 'app/shared/model/professional-experience.model';

export interface IProject {
  id?: number;
  shortDescription?: string;
  longDescription?: string;
  startDate?: Moment;
  endDate?: Moment;
  technologies?: ITechnology[];
  profissionalExperience?: IProfessionalExperience;
}

export class Project implements IProject {
  constructor(
    public id?: number,
    public shortDescription?: string,
    public longDescription?: string,
    public startDate?: Moment,
    public endDate?: Moment,
    public technologies?: ITechnology[],
    public profissionalExperience?: IProfessionalExperience
  ) {}
}
