/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { MyCvApiTestModule } from '../../../test.module';
import { RewardUpdateComponent } from 'app/entities/reward/reward-update.component';
import { RewardService } from 'app/entities/reward/reward.service';
import { Reward } from 'app/shared/model/reward.model';

describe('Component Tests', () => {
  describe('Reward Management Update Component', () => {
    let comp: RewardUpdateComponent;
    let fixture: ComponentFixture<RewardUpdateComponent>;
    let service: RewardService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyCvApiTestModule],
        declarations: [RewardUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RewardUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RewardUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RewardService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Reward(123);
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
        const entity = new Reward();
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
