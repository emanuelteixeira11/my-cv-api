/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MyCvApiTestModule } from '../../../test.module';
import { RewardDeleteDialogComponent } from 'app/entities/reward/reward-delete-dialog.component';
import { RewardService } from 'app/entities/reward/reward.service';

describe('Component Tests', () => {
  describe('Reward Management Delete Component', () => {
    let comp: RewardDeleteDialogComponent;
    let fixture: ComponentFixture<RewardDeleteDialogComponent>;
    let service: RewardService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyCvApiTestModule],
        declarations: [RewardDeleteDialogComponent]
      })
        .overrideTemplate(RewardDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RewardDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RewardService);
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
