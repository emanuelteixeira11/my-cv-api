import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IProject, Project } from 'app/shared/model/project.model';
import { ProjectService } from './project.service';
import { ITechnology } from 'app/shared/model/technology.model';
import { TechnologyService } from 'app/entities/technology';
import { IProfessionalExperience } from 'app/shared/model/professional-experience.model';
import { ProfessionalExperienceService } from 'app/entities/professional-experience';

@Component({
  selector: 'jhi-project-update',
  templateUrl: './project-update.component.html'
})
export class ProjectUpdateComponent implements OnInit {
  isSaving: boolean;

  technologies: ITechnology[];

  professionalexperiences: IProfessionalExperience[];
  startDateDp: any;
  endDateDp: any;

  editForm = this.fb.group({
    id: [],
    shortDescription: [null, [Validators.required]],
    longDescription: [],
    startDate: [],
    endDate: [],
    technologies: [],
    profissionalExperience: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected projectService: ProjectService,
    protected technologyService: TechnologyService,
    protected professionalExperienceService: ProfessionalExperienceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ project }) => {
      this.updateForm(project);
    });
    this.technologyService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITechnology[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITechnology[]>) => response.body)
      )
      .subscribe((res: ITechnology[]) => (this.technologies = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.professionalExperienceService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProfessionalExperience[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProfessionalExperience[]>) => response.body)
      )
      .subscribe(
        (res: IProfessionalExperience[]) => (this.professionalexperiences = res),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(project: IProject) {
    this.editForm.patchValue({
      id: project.id,
      shortDescription: project.shortDescription,
      longDescription: project.longDescription,
      startDate: project.startDate,
      endDate: project.endDate,
      technologies: project.technologies,
      profissionalExperience: project.profissionalExperience
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const project = this.createFromForm();
    if (project.id !== undefined) {
      this.subscribeToSaveResponse(this.projectService.update(project));
    } else {
      this.subscribeToSaveResponse(this.projectService.create(project));
    }
  }

  private createFromForm(): IProject {
    const entity = {
      ...new Project(),
      id: this.editForm.get(['id']).value,
      shortDescription: this.editForm.get(['shortDescription']).value,
      longDescription: this.editForm.get(['longDescription']).value,
      startDate: this.editForm.get(['startDate']).value,
      endDate: this.editForm.get(['endDate']).value,
      technologies: this.editForm.get(['technologies']).value,
      profissionalExperience: this.editForm.get(['profissionalExperience']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProject>>) {
    result.subscribe((res: HttpResponse<IProject>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackTechnologyById(index: number, item: ITechnology) {
    return item.id;
  }

  trackProfessionalExperienceById(index: number, item: IProfessionalExperience) {
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
