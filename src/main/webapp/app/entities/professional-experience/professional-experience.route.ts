import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProfessionalExperience } from 'app/shared/model/professional-experience.model';
import { ProfessionalExperienceService } from './professional-experience.service';
import { ProfessionalExperienceComponent } from './professional-experience.component';
import { ProfessionalExperienceDetailComponent } from './professional-experience-detail.component';
import { ProfessionalExperienceUpdateComponent } from './professional-experience-update.component';
import { ProfessionalExperienceDeletePopupComponent } from './professional-experience-delete-dialog.component';
import { IProfessionalExperience } from 'app/shared/model/professional-experience.model';

@Injectable({ providedIn: 'root' })
export class ProfessionalExperienceResolve implements Resolve<IProfessionalExperience> {
  constructor(private service: ProfessionalExperienceService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProfessionalExperience> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ProfessionalExperience>) => response.ok),
        map((professionalExperience: HttpResponse<ProfessionalExperience>) => professionalExperience.body)
      );
    }
    return of(new ProfessionalExperience());
  }
}

export const professionalExperienceRoute: Routes = [
  {
    path: '',
    component: ProfessionalExperienceComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'myCvApiApp.professionalExperience.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProfessionalExperienceDetailComponent,
    resolve: {
      professionalExperience: ProfessionalExperienceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'myCvApiApp.professionalExperience.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProfessionalExperienceUpdateComponent,
    resolve: {
      professionalExperience: ProfessionalExperienceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'myCvApiApp.professionalExperience.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProfessionalExperienceUpdateComponent,
    resolve: {
      professionalExperience: ProfessionalExperienceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'myCvApiApp.professionalExperience.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const professionalExperiencePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProfessionalExperienceDeletePopupComponent,
    resolve: {
      professionalExperience: ProfessionalExperienceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'myCvApiApp.professionalExperience.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
