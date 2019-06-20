/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MyCvApiTestModule } from '../../../test.module';
import { RewardDetailComponent } from 'app/entities/reward/reward-detail.component';
import { Reward } from 'app/shared/model/reward.model';

describe('Component Tests', () => {
  describe('Reward Management Detail Component', () => {
    let comp: RewardDetailComponent;
    let fixture: ComponentFixture<RewardDetailComponent>;
    const route = ({ data: of({ reward: new Reward(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyCvApiTestModule],
        declarations: [RewardDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RewardDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RewardDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.reward).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
