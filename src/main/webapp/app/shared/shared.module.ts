import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { MyCvApiSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [MyCvApiSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [MyCvApiSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyCvApiSharedModule {
  static forRoot() {
    return {
      ngModule: MyCvApiSharedModule
    };
  }
}
