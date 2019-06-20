import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ITechnology, Technology } from 'app/shared/model/technology.model';
import { TechnologyService } from './technology.service';
import { IProject } from 'app/shared/model/project.model';
import { ProjectService } from 'app/entities/project';

@Component({
  selector: 'jhi-technology-update',
  templateUrl: './technology-update.component.html'
})
export class TechnologyUpdateComponent implements OnInit {
  isSaving: boolean;

  projects: IProject[];

  editForm = this.fb.group({
    id: [],
    techName: [null, [Validators.required]],
    isFramework: [null, [Validators.required]],
    experienceRate: [null, [Validators.required]]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected technologyService: TechnologyService,
    protected projectService: ProjectService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ technology }) => {
      this.updateForm(technology);
    });
    this.projectService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProject[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProject[]>) => response.body)
      )
      .subscribe((res: IProject[]) => (this.projects = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(technology: ITechnology) {
    this.editForm.patchValue({
      id: technology.id,
      techName: technology.techName,
      isFramework: technology.isFramework,
      experienceRate: technology.experienceRate
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const technology = this.createFromForm();
    if (technology.id !== undefined) {
      this.subscribeToSaveResponse(this.technologyService.update(technology));
    } else {
      this.subscribeToSaveResponse(this.technologyService.create(technology));
    }
  }

  private createFromForm(): ITechnology {
    const entity = {
      ...new Technology(),
      id: this.editForm.get(['id']).value,
      techName: this.editForm.get(['techName']).value,
      isFramework: this.editForm.get(['isFramework']).value,
      experienceRate: this.editForm.get(['experienceRate']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITechnology>>) {
    result.subscribe((res: HttpResponse<ITechnology>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackProjectById(index: number, item: IProject) {
    return item.id;
  }

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
