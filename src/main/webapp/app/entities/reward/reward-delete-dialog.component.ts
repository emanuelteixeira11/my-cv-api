import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReward } from 'app/shared/model/reward.model';
import { RewardService } from './reward.service';

@Component({
  selector: 'jhi-reward-delete-dialog',
  templateUrl: './reward-delete-dialog.component.html'
})
export class RewardDeleteDialogComponent {
  reward: IReward;

  constructor(protected rewardService: RewardService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.rewardService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'rewardListModification',
        content: 'Deleted an reward'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-reward-delete-popup',
  template: ''
})
export class RewardDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ reward }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(RewardDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.reward = reward;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/reward', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/reward', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
