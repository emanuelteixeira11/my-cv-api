import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { MyCvApiSharedModule } from 'app/shared';
import {
  TechnologyComponent,
  TechnologyDetailComponent,
  TechnologyUpdateComponent,
  TechnologyDeletePopupComponent,
  TechnologyDeleteDialogComponent,
  technologyRoute,
  technologyPopupRoute
} from './';

const ENTITY_STATES = [...technologyRoute, ...technologyPopupRoute];

@NgModule({
  imports: [MyCvApiSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TechnologyComponent,
    TechnologyDetailComponent,
    TechnologyUpdateComponent,
    TechnologyDeleteDialogComponent,
    TechnologyDeletePopupComponent
  ],
  entryComponents: [TechnologyComponent, TechnologyUpdateComponent, TechnologyDeleteDialogComponent, TechnologyDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyCvApiTechnologyModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
