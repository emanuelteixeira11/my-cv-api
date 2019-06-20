import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITokenMap } from 'app/shared/model/token-map.model';
import { TokenMapService } from './token-map.service';

@Component({
  selector: 'jhi-token-map-delete-dialog',
  templateUrl: './token-map-delete-dialog.component.html'
})
export class TokenMapDeleteDialogComponent {
  tokenMap: ITokenMap;

  constructor(protected tokenMapService: TokenMapService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.tokenMapService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'tokenMapListModification',
        content: 'Deleted an tokenMap'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-token-map-delete-popup',
  template: ''
})
export class TokenMapDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tokenMap }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TokenMapDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.tokenMap = tokenMap;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/token-map', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/token-map', { outlets: { popup: null } }]);
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
