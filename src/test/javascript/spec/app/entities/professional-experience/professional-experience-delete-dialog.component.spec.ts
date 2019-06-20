/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MyCvApiTestModule } from '../../../test.module';
import { ProfessionalExperienceDeleteDialogComponent } from 'app/entities/professional-experience/professional-experience-delete-dialog.component';
import { ProfessionalExperienceService } from 'app/entities/professional-experience/professional-experience.service';

describe('Component Tests', () => {
  describe('ProfessionalExperience Management Delete Component', () => {
    let comp: ProfessionalExperienceDeleteDialogComponent;
    let fixture: ComponentFixture<ProfessionalExperienceDeleteDialogComponent>;
    let service: ProfessionalExperienceService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyCvApiTestModule],
        declarations: [ProfessionalExperienceDeleteDialogComponent]
      })
        .overrideTemplate(ProfessionalExperienceDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProfessionalExperienceDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProfessionalExperienceService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
