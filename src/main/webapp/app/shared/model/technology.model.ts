import { IProject } from 'app/shared/model/project.model';

export interface ITechnology {
  id?: number;
  techName?: string;
  isFramework?: boolean;
  experienceRate?: number;
  projects?: IProject[];
}

export class Technology implements ITechnology {
  constructor(
    public id?: number,
    public techName?: string,
    public isFramework?: boolean,
    public experienceRate?: number,
    public projects?: IProject[]
  ) {
    this.isFramework = this.isFramework || false;
  }
}
