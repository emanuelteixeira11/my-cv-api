import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IAcademicExperience, AcademicExperience } from 'app/shared/model/academic-experience.model';
import { AcademicExperienceService } from './academic-experience.service';
import { IPerson } from 'app/shared/model/person.model';
import { PersonService } from 'app/entities/person';

@Component({
  selector: 'jhi-academic-experience-update',
  templateUrl: './academic-experience-update.component.html'
})
export class AcademicExperienceUpdateComponent implements OnInit {
  isSaving: boolean;

  people: IPerson[];
  startDateDp: any;
  endDateDp: any;

  editForm = this.fb.group({
    id: [],
    school: [null, [Validators.required]],
    degree: [null, [Validators.required]],
    startDate: [null, [Validators.required]],
    endDate: [],
    classification: [],
    person: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected academicExperienceService: AcademicExperienceService,
    protected personService: PersonService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ academicExperience }) => {
      this.updateForm(academicExperience);
    });
    this.personService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPerson[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPerson[]>) => response.body)
      )
      .subscribe((res: IPerson[]) => (this.people = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(academicExperience: IAcademicExperience) {
    this.editForm.patchValue({
      id: academicExperience.id,
      school: academicExperience.school,
      degree: academicExperience.degree,
      startDate: academicExperience.startDate,
      endDate: academicExperience.endDate,
      classification: academicExperience.classification,
      person: academicExperience.person
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const academicExperience = this.createFromForm();
    if (academicExperience.id !== undefined) {
      this.subscribeToSaveResponse(this.academicExperienceService.update(academicExperience));
    } else {
      this.subscribeToSaveResponse(this.academicExperienceService.create(academicExperience));
    }
  }

  private createFromForm(): IAcademicExperience {
    const entity = {
      ...new AcademicExperience(),
      id: this.editForm.get(['id']).value,
      school: this.editForm.get(['school']).value,
      degree: this.editForm.get(['degree']).value,
      startDate: this.editForm.get(['startDate']).value,
      endDate: this.editForm.get(['endDate']).value,
      classification: this.editForm.get(['classification']).value,
      person: this.editForm.get(['person']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAcademicExperience>>) {
    result.subscribe((res: HttpResponse<IAcademicExperience>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackPersonById(index: number, item: IPerson) {
    return item.id;
  }
}
