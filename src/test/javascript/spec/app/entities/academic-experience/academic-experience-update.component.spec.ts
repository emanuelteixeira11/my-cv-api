/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { MyCvApiTestModule } from '../../../test.module';
import { AcademicExperienceUpdateComponent } from 'app/entities/academic-experience/academic-experience-update.component';
import { AcademicExperienceService } from 'app/entities/academic-experience/academic-experience.service';
import { AcademicExperience } from 'app/shared/model/academic-experience.model';

describe('Component Tests', () => {
  describe('AcademicExperience Management Update Component', () => {
    let comp: AcademicExperienceUpdateComponent;
    let fixture: ComponentFixture<AcademicExperienceUpdateComponent>;
    let service: AcademicExperienceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyCvApiTestModule],
        declarations: [AcademicExperienceUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AcademicExperienceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AcademicExperienceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AcademicExperienceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AcademicExperience(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new AcademicExperience();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
