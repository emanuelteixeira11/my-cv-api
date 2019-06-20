import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AcademicExperience } from 'app/shared/model/academic-experience.model';
import { AcademicExperienceService } from './academic-experience.service';
import { AcademicExperienceComponent } from './academic-experience.component';
import { AcademicExperienceDetailComponent } from './academic-experience-detail.component';
import { AcademicExperienceUpdateComponent } from './academic-experience-update.component';
import { AcademicExperienceDeletePopupComponent } from './academic-experience-delete-dialog.component';
import { IAcademicExperience } from 'app/shared/model/academic-experience.model';

@Injectable({ providedIn: 'root' })
export class AcademicExperienceResolve implements Resolve<IAcademicExperience> {
  constructor(private service: AcademicExperienceService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAcademicExperience> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AcademicExperience>) => response.ok),
        map((academicExperience: HttpResponse<AcademicExperience>) => academicExperience.body)
      );
    }
    return of(new AcademicExperience());
  }
}

export const academicExperienceRoute: Routes = [
  {
    path: '',
    component: AcademicExperienceComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'myCvApiApp.academicExperience.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AcademicExperienceDetailComponent,
    resolve: {
      academicExperience: AcademicExperienceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'myCvApiApp.academicExperience.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AcademicExperienceUpdateComponent,
    resolve: {
      academicExperience: AcademicExperienceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'myCvApiApp.academicExperience.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AcademicExperienceUpdateComponent,
    resolve: {
      academicExperience: AcademicExperienceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'myCvApiApp.academicExperience.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const academicExperiencePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AcademicExperienceDeletePopupComponent,
    resolve: {
      academicExperience: AcademicExperienceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'myCvApiApp.academicExperience.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
