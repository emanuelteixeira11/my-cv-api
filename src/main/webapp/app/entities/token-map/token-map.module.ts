import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { MyCvApiSharedModule } from 'app/shared';
import {
  TokenMapComponent,
  TokenMapDetailComponent,
  TokenMapUpdateComponent,
  TokenMapDeletePopupComponent,
  TokenMapDeleteDialogComponent,
  tokenMapRoute,
  tokenMapPopupRoute
} from './';

const ENTITY_STATES = [...tokenMapRoute, ...tokenMapPopupRoute];

@NgModule({
  imports: [MyCvApiSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TokenMapComponent,
    TokenMapDetailComponent,
    TokenMapUpdateComponent,
    TokenMapDeleteDialogComponent,
    TokenMapDeletePopupComponent
  ],
  entryComponents: [TokenMapComponent, TokenMapUpdateComponent, TokenMapDeleteDialogComponent, TokenMapDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyCvApiTokenMapModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
