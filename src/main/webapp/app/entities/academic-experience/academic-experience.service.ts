import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAcademicExperience } from 'app/shared/model/academic-experience.model';

type EntityResponseType = HttpResponse<IAcademicExperience>;
type EntityArrayResponseType = HttpResponse<IAcademicExperience[]>;

@Injectable({ providedIn: 'root' })
export class AcademicExperienceService {
  public resourceUrl = SERVER_API_URL + 'api/academic-experiences';

  constructor(protected http: HttpClient) {}

  create(academicExperience: IAcademicExperience): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(academicExperience);
    return this.http
      .post<IAcademicExperience>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(academicExperience: IAcademicExperience): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(academicExperience);
    return this.http
      .put<IAcademicExperience>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAcademicExperience>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAcademicExperience[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(academicExperience: IAcademicExperience): IAcademicExperience {
    const copy: IAcademicExperience = Object.assign({}, academicExperience, {
      startDate:
        academicExperience.startDate != null && academicExperience.startDate.isValid()
          ? academicExperience.startDate.format(DATE_FORMAT)
          : null,
      endDate:
        academicExperience.endDate != null && academicExperience.endDate.isValid() ? academicExperience.endDate.format(DATE_FORMAT) : null
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
      res.body.forEach((academicExperience: IAcademicExperience) => {
        academicExperience.startDate = academicExperience.startDate != null ? moment(academicExperience.startDate) : null;
        academicExperience.endDate = academicExperience.endDate != null ? moment(academicExperience.endDate) : null;
      });
    }
    return res;
  }
}
