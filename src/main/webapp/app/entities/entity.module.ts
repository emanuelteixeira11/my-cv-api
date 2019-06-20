import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'token-map',
        loadChildren: './token-map/token-map.module#MyCvApiTokenMapModule'
      },
      {
        path: 'person',
        loadChildren: './person/person.module#MyCvApiPersonModule'
      },
      {
        path: 'professional-experience',
        loadChildren: './professional-experience/professional-experience.module#MyCvApiProfessionalExperienceModule'
      },
      {
        path: 'project',
        loadChildren: './project/project.module#MyCvApiProjectModule'
      },
      {
        path: 'technology',
        loadChildren: './technology/technology.module#MyCvApiTechnologyModule'
      },
      {
        path: 'academic-experience',
        loadChildren: './academic-experience/academic-experience.module#MyCvApiAcademicExperienceModule'
      },
      {
        path: 'contact',
        loadChildren: './contact/contact.module#MyCvApiContactModule'
      },
      {
        path: 'location',
        loadChildren: './location/location.module#MyCvApiLocationModule'
      },
      {
        path: 'language',
        loadChildren: './language/language.module#MyCvApiLanguageModule'
      },
      {
        path: 'reward',
        loadChildren: './reward/reward.module#MyCvApiRewardModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyCvApiEntityModule {}
