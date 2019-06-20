import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITokenMap } from 'app/shared/model/token-map.model';

@Component({
  selector: 'jhi-token-map-detail',
  templateUrl: './token-map-detail.component.html'
})
export class TokenMapDetailComponent implements OnInit {
  tokenMap: ITokenMap;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tokenMap }) => {
      this.tokenMap = tokenMap;
    });
  }

  previousState() {
    window.history.back();
  }
}
