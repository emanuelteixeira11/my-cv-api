/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MyCvApiTestModule } from '../../../test.module';
import { ProfessionalExperienceDetailComponent } from 'app/entities/professional-experience/professional-experience-detail.component';
import { ProfessionalExperience } from 'app/shared/model/professional-experience.model';

describe('Component Tests', () => {
  describe('ProfessionalExperience Management Detail Component', () => {
    let comp: ProfessionalExperienceDetailComponent;
    let fixture: ComponentFixture<ProfessionalExperienceDetailComponent>;
    const route = ({ data: of({ professionalExperience: new ProfessionalExperience(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyCvApiTestModule],
        declarations: [ProfessionalExperienceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProfessionalExperienceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProfessionalExperienceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.professionalExperience).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
