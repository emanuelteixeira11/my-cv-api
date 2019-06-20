import { IPerson } from 'app/shared/model/person.model';

export interface ILanguage {
  id?: number;
  language?: string;
  level?: string;
  person?: IPerson;
}

export class Language implements ILanguage {
  constructor(public id?: number, public language?: string, public level?: string, public person?: IPerson) {}
}
