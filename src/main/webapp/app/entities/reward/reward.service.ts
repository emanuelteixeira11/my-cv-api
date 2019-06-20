import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IReward } from 'app/shared/model/reward.model';

type EntityResponseType = HttpResponse<IReward>;
type EntityArrayResponseType = HttpResponse<IReward[]>;

@Injectable({ providedIn: 'root' })
export class RewardService {
  public resourceUrl = SERVER_API_URL + 'api/rewards';

  constructor(protected http: HttpClient) {}

  create(reward: IReward): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(reward);
    return this.http
      .post<IReward>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(reward: IReward): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(reward);
    return this.http
      .put<IReward>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IReward>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IReward[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(reward: IReward): IReward {
    const copy: IReward = Object.assign({}, reward, {
      date: reward.date != null && reward.date.isValid() ? reward.date.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date != null ? moment(res.body.date) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((reward: IReward) => {
        reward.date = reward.date != null ? moment(reward.date) : null;
      });
    }
    return res;
  }
}
