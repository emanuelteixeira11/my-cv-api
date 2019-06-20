import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Reward } from 'app/shared/model/reward.model';
import { RewardService } from './reward.service';
import { RewardComponent } from './reward.component';
import { RewardDetailComponent } from './reward-detail.component';
import { RewardUpdateComponent } from './reward-update.component';
import { RewardDeletePopupComponent } from './reward-delete-dialog.component';
import { IReward } from 'app/shared/model/reward.model';

@Injectable({ providedIn: 'root' })
export class RewardResolve implements Resolve<IReward> {
  constructor(private service: RewardService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IReward> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Reward>) => response.ok),
        map((reward: HttpResponse<Reward>) => reward.body)
      );
    }
    return of(new Reward());
  }
}

export const rewardRoute: Routes = [
  {
    path: '',
    component: RewardComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'myCvApiApp.reward.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RewardDetailComponent,
    resolve: {
      reward: RewardResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'myCvApiApp.reward.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RewardUpdateComponent,
    resolve: {
      reward: RewardResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'myCvApiApp.reward.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RewardUpdateComponent,
    resolve: {
      reward: RewardResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'myCvApiApp.reward.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const rewardPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: RewardDeletePopupComponent,
    resolve: {
      reward: RewardResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'myCvApiApp.reward.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
