import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { MyCvApiSharedModule } from 'app/shared';
import {
  RewardComponent,
  RewardDetailComponent,
  RewardUpdateComponent,
  RewardDeletePopupComponent,
  RewardDeleteDialogComponent,
  rewardRoute,
  rewardPopupRoute
} from './';

const ENTITY_STATES = [...rewardRoute, ...rewardPopupRoute];

@NgModule({
  imports: [MyCvApiSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [RewardComponent, RewardDetailComponent, RewardUpdateComponent, RewardDeleteDialogComponent, RewardDeletePopupComponent],
  entryComponents: [RewardComponent, RewardUpdateComponent, RewardDeleteDialogComponent, RewardDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyCvApiRewardModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
