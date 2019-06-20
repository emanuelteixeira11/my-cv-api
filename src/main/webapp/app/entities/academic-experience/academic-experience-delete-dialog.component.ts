import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAcademicExperience } from 'app/shared/model/academic-experience.model';
import { AcademicExperienceService } from './academic-experience.service';

@Component({
  selector: 'jhi-academic-experience-delete-dialog',
  templateUrl: './academic-experience-delete-dialog.component.html'
})
export class AcademicExperienceDeleteDialogComponent {
  academicExperience: IAcademicExperience;

  constructor(
    protected academicExperienceService: AcademicExperienceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.academicExperienceService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'academicExperienceListModification',
        content: 'Deleted an academicExperience'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-academic-experience-delete-popup',
  template: ''
})
export class AcademicExperienceDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ academicExperience }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AcademicExperienceDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.academicExperience = academicExperience;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/academic-experience', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/academic-experience', { outlets: { popup: null } }]);
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
