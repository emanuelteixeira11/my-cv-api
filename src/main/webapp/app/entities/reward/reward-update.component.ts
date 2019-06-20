import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IReward, Reward } from 'app/shared/model/reward.model';
import { RewardService } from './reward.service';
import { IPerson } from 'app/shared/model/person.model';
import { PersonService } from 'app/entities/person';

@Component({
  selector: 'jhi-reward-update',
  templateUrl: './reward-update.component.html'
})
export class RewardUpdateComponent implements OnInit {
  isSaving: boolean;

  people: IPerson[];
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    title: [null, [Validators.required]],
    date: [null, [Validators.required]],
    issuerEntity: [null, [Validators.required]],
    shortDescription: [null, [Validators.required]],
    person: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected rewardService: RewardService,
    protected personService: PersonService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ reward }) => {
      this.updateForm(reward);
    });
    this.personService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPerson[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPerson[]>) => response.body)
      )
      .subscribe((res: IPerson[]) => (this.people = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(reward: IReward) {
    this.editForm.patchValue({
      id: reward.id,
      title: reward.title,
      date: reward.date,
      issuerEntity: reward.issuerEntity,
      shortDescription: reward.shortDescription,
      person: reward.person
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const reward = this.createFromForm();
    if (reward.id !== undefined) {
      this.subscribeToSaveResponse(this.rewardService.update(reward));
    } else {
      this.subscribeToSaveResponse(this.rewardService.create(reward));
    }
  }

  private createFromForm(): IReward {
    const entity = {
      ...new Reward(),
      id: this.editForm.get(['id']).value,
      title: this.editForm.get(['title']).value,
      date: this.editForm.get(['date']).value,
      issuerEntity: this.editForm.get(['issuerEntity']).value,
      shortDescription: this.editForm.get(['shortDescription']).value,
      person: this.editForm.get(['person']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReward>>) {
    result.subscribe((res: HttpResponse<IReward>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
