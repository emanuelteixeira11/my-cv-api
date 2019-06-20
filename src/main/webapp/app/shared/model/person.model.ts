import { Moment } from 'moment';
import { IProfessionalExperience } from 'app/shared/model/professional-experience.model';
import { IAcademicExperience } from 'app/shared/model/academic-experience.model';
import { IContact } from 'app/shared/model/contact.model';
import { ILocation } from 'app/shared/model/location.model';
import { IReward } from 'app/shared/model/reward.model';
import { ITokenMap } from 'app/shared/model/token-map.model';
import { ILanguage } from 'app/shared/model/language.model';

export interface IPerson {
  id?: number;
  firstName?: string;
  lastName?: string;
  picturePath?: string;
  birthDate?: Moment;
  birthTown?: string;
  shortDescription?: string;
  longDescription?: string;
  professionalExperiences?: IProfessionalExperience[];
  academicExperiences?: IAcademicExperience[];
  contacts?: IContact[];
  locations?: ILocation[];
  rewards?: IReward[];
  tokenMaps?: ITokenMap[];
  languages?: ILanguage[];
}

export class Person implements IPerson {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public picturePath?: string,
    public birthDate?: Moment,
    public birthTown?: string,
    public shortDescription?: string,
    public longDescription?: string,
    public professionalExperiences?: IProfessionalExperience[],
    public academicExperiences?: IAcademicExperience[],
    public contacts?: IContact[],
    public locations?: ILocation[],
    public rewards?: IReward[],
    public tokenMaps?: ITokenMap[],
    public languages?: ILanguage[]
  ) {}
}
