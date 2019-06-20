import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITokenMap } from 'app/shared/model/token-map.model';

type EntityResponseType = HttpResponse<ITokenMap>;
type EntityArrayResponseType = HttpResponse<ITokenMap[]>;

@Injectable({ providedIn: 'root' })
export class TokenMapService {
  public resourceUrl = SERVER_API_URL + 'api/token-maps';

  constructor(protected http: HttpClient) {}

  create(tokenMap: ITokenMap): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tokenMap);
    return this.http
      .post<ITokenMap>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tokenMap: ITokenMap): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tokenMap);
    return this.http
      .put<ITokenMap>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITokenMap>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITokenMap[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(tokenMap: ITokenMap): ITokenMap {
    const copy: ITokenMap = Object.assign({}, tokenMap, {
      validTo: tokenMap.validTo != null && tokenMap.validTo.isValid() ? tokenMap.validTo.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.validTo = res.body.validTo != null ? moment(res.body.validTo) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((tokenMap: ITokenMap) => {
        tokenMap.validTo = tokenMap.validTo != null ? moment(tokenMap.validTo) : null;
      });
    }
    return res;
  }
}
