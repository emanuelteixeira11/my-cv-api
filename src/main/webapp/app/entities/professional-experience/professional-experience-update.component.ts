import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IProfessionalExperience, ProfessionalExperience } from 'app/shared/model/professional-experience.model';
import { ProfessionalExperienceService } from './professional-experience.service';
import { IPerson } from 'app/shared/model/person.model';
import { PersonService } from 'app/entities/person';

@Component({
  selector: 'jhi-professional-experience-update',
  templateUrl: './professional-experience-update.component.html'
})
export class ProfessionalExperienceUpdateComponent implements OnInit {
  isSaving: boolean;

  people: IPerson[];
  startDateDp: any;
  endDateDp: any;

  editForm = this.fb.group({
    id: [],
    title: [null, [Validators.required]],
    company: [null, [Validators.required]],
    startDate: [null, [Validators.required]],
    endDate: [],
    person: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected professionalExperienceService: ProfessionalExperienceService,
    protected personService: PersonService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ professionalExperience }) => {
      this.updateForm(professionalExperience);
    });
    this.personService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPerson[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPerson[]>) => response.body)
      )
      .subscribe((res: IPerson[]) => (this.people = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(professionalExperience: IProfessionalExperience) {
    this.editForm.patchValue({
      id: professionalExperience.id,
      title: professionalExperience.title,
      company: professionalExperience.company,
      startDate: professionalExperience.startDate,
      endDate: professionalExperience.endDate,
      person: professionalExperience.person
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const professionalExperience = this.createFromForm();
    if (professionalExperience.id !== undefined) {
      this.subscribeToSaveResponse(this.professionalExperienceService.update(professionalExperience));
    } else {
      this.subscribeToSaveResponse(this.professionalExperienceService.create(professionalExperience));
    }
  }

  private createFromForm(): IProfessionalExperience {
    const entity = {
      ...new ProfessionalExperience(),
      id: this.editForm.get(['id']).value,
      title: this.editForm.get(['title']).value,
      company: this.editForm.get(['company']).value,
      startDate: this.editForm.get(['startDate']).value,
      endDate: this.editForm.get(['endDate']).value,
      person: this.editForm.get(['person']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProfessionalExperience>>) {
    result.subscribe((res: HttpResponse<IProfessionalExperience>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
