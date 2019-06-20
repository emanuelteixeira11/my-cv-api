import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProfessionalExperience } from 'app/shared/model/professional-experience.model';

type EntityResponseType = HttpResponse<IProfessionalExperience>;
type EntityArrayResponseType = HttpResponse<IProfessionalExperience[]>;

@Injectable({ providedIn: 'root' })
export class ProfessionalExperienceService {
  public resourceUrl = SERVER_API_URL + 'api/professional-experiences';

  constructor(protected http: HttpClient) {}

  create(professionalExperience: IProfessionalExperience): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(professionalExperience);
    return this.http
      .post<IProfessionalExperience>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(professionalExperience: IProfessionalExperience): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(professionalExperience);
    return this.http
      .put<IProfessionalExperience>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IProfessionalExperience>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IProfessionalExperience[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(professionalExperience: IProfessionalExperience): IProfessionalExperience {
    const copy: IProfessionalExperience = Object.assign({}, professionalExperience, {
      startDate:
        professionalExperience.startDate != null && professionalExperience.startDate.isValid()
          ? professionalExperience.startDate.format(DATE_FORMAT)
          : null,
      endDate:
        professionalExperience.endDate != null && professionalExperience.endDate.isValid()
          ? professionalExperience.endDate.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startDate = res.body.startDate != null ? moment(res.body.startDate) : null;
      res.body.endDate = res.body.endDate != null ? moment(res.body.endDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((professionalExperience: IProfessionalExperience) => {
        professionalExperience.startDate = professionalExperience.startDate != null ? moment(professionalExperience.startDate) : null;
        professionalExperience.endDate = professionalExperience.endDate != null ? moment(professionalExperience.endDate) : null;
      });
    }
    return res;
  }
}
