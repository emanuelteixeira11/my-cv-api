import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReward } from 'app/shared/model/reward.model';

@Component({
  selector: 'jhi-reward-detail',
  templateUrl: './reward-detail.component.html'
})
export class RewardDetailComponent implements OnInit {
  reward: IReward;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ reward }) => {
      this.reward = reward;
    });
  }

  previousState() {
    window.history.back();
  }
}
