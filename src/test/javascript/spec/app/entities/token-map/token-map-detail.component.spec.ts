/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MyCvApiTestModule } from '../../../test.module';
import { TokenMapDetailComponent } from 'app/entities/token-map/token-map-detail.component';
import { TokenMap } from 'app/shared/model/token-map.model';

describe('Component Tests', () => {
  describe('TokenMap Management Detail Component', () => {
    let comp: TokenMapDetailComponent;
    let fixture: ComponentFixture<TokenMapDetailComponent>;
    const route = ({ data: of({ tokenMap: new TokenMap(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyCvApiTestModule],
        declarations: [TokenMapDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TokenMapDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TokenMapDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tokenMap).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
