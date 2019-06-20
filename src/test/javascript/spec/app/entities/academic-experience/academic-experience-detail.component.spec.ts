/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MyCvApiTestModule } from '../../../test.module';
import { AcademicExperienceDetailComponent } from 'app/entities/academic-experience/academic-experience-detail.component';
import { AcademicExperience } from 'app/shared/model/academic-experience.model';

describe('Component Tests', () => {
  describe('AcademicExperience Management Detail Component', () => {
    let comp: AcademicExperienceDetailComponent;
    let fixture: ComponentFixture<AcademicExperienceDetailComponent>;
    const route = ({ data: of({ academicExperience: new AcademicExperience(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyCvApiTestModule],
        declarations: [AcademicExperienceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AcademicExperienceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AcademicExperienceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.academicExperience).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
