import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TokenMap } from 'app/shared/model/token-map.model';
import { TokenMapService } from './token-map.service';
import { TokenMapComponent } from './token-map.component';
import { TokenMapDetailComponent } from './token-map-detail.component';
import { TokenMapUpdateComponent } from './token-map-update.component';
import { TokenMapDeletePopupComponent } from './token-map-delete-dialog.component';
import { ITokenMap } from 'app/shared/model/token-map.model';

@Injectable({ providedIn: 'root' })
export class TokenMapResolve implements Resolve<ITokenMap> {
  constructor(private service: TokenMapService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITokenMap> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<TokenMap>) => response.ok),
        map((tokenMap: HttpResponse<TokenMap>) => tokenMap.body)
      );
    }
    return of(new TokenMap());
  }
}

export const tokenMapRoute: Routes = [
  {
    path: '',
    component: TokenMapComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'myCvApiApp.tokenMap.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TokenMapDetailComponent,
    resolve: {
      tokenMap: TokenMapResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'myCvApiApp.tokenMap.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TokenMapUpdateComponent,
    resolve: {
      tokenMap: TokenMapResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'myCvApiApp.tokenMap.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TokenMapUpdateComponent,
    resolve: {
      tokenMap: TokenMapResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'myCvApiApp.tokenMap.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const tokenMapPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TokenMapDeletePopupComponent,
    resolve: {
      tokenMap: TokenMapResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'myCvApiApp.tokenMap.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
