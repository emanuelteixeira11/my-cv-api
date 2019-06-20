/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { MyCvApiTestModule } from '../../../test.module';
import { TokenMapUpdateComponent } from 'app/entities/token-map/token-map-update.component';
import { TokenMapService } from 'app/entities/token-map/token-map.service';
import { TokenMap } from 'app/shared/model/token-map.model';

describe('Component Tests', () => {
  describe('TokenMap Management Update Component', () => {
    let comp: TokenMapUpdateComponent;
    let fixture: ComponentFixture<TokenMapUpdateComponent>;
    let service: TokenMapService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyCvApiTestModule],
        declarations: [TokenMapUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TokenMapUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TokenMapUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TokenMapService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TokenMap(123);
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
        const entity = new TokenMap();
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
