import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProfessionalExperience } from 'app/shared/model/professional-experience.model';
import { ProfessionalExperienceService } from './professional-experience.service';

@Component({
  selector: 'jhi-professional-experience-delete-dialog',
  templateUrl: './professional-experience-delete-dialog.component.html'
})
export class ProfessionalExperienceDeleteDialogComponent {
  professionalExperience: IProfessionalExperience;

  constructor(
    protected professionalExperienceService: ProfessionalExperienceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.professionalExperienceService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'professionalExperienceListModification',
        content: 'Deleted an professionalExperience'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-professional-experience-delete-popup',
  template: ''
})
export class ProfessionalExperienceDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ professionalExperience }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProfessionalExperienceDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.professionalExperience = professionalExperience;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/professional-experience', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/professional-experience', { outlets: { popup: null } }]);
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
