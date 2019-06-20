import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { MyCvApiSharedModule } from 'app/shared';
import {
  AcademicExperienceComponent,
  AcademicExperienceDetailComponent,
  AcademicExperienceUpdateComponent,
  AcademicExperienceDeletePopupComponent,
  AcademicExperienceDeleteDialogComponent,
  academicExperienceRoute,
  academicExperiencePopupRoute
} from './';

const ENTITY_STATES = [...academicExperienceRoute, ...academicExperiencePopupRoute];

@NgModule({
  imports: [MyCvApiSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AcademicExperienceComponent,
    AcademicExperienceDetailComponent,
    AcademicExperienceUpdateComponent,
    AcademicExperienceDeleteDialogComponent,
    AcademicExperienceDeletePopupComponent
  ],
  entryComponents: [
    AcademicExperienceComponent,
    AcademicExperienceUpdateComponent,
    AcademicExperienceDeleteDialogComponent,
    AcademicExperienceDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyCvApiAcademicExperienceModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
