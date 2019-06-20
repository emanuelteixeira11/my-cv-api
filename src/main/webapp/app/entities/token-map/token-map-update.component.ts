import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ITokenMap, TokenMap } from 'app/shared/model/token-map.model';
import { TokenMapService } from './token-map.service';
import { IPerson } from 'app/shared/model/person.model';
import { PersonService } from 'app/entities/person';

@Component({
  selector: 'jhi-token-map-update',
  templateUrl: './token-map-update.component.html'
})
export class TokenMapUpdateComponent implements OnInit {
  isSaving: boolean;

  people: IPerson[];
  validToDp: any;

  editForm = this.fb.group({
    id: [],
    token: [null, [Validators.required]],
    entityName: [null, [Validators.required]],
    validTo: [null, [Validators.required]],
    person: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected tokenMapService: TokenMapService,
    protected personService: PersonService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ tokenMap }) => {
      this.updateForm(tokenMap);
    });
    this.personService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPerson[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPerson[]>) => response.body)
      )
      .subscribe((res: IPerson[]) => (this.people = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(tokenMap: ITokenMap) {
    this.editForm.patchValue({
      id: tokenMap.id,
      token: tokenMap.token,
      entityName: tokenMap.entityName,
      validTo: tokenMap.validTo,
      person: tokenMap.person
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const tokenMap = this.createFromForm();
    if (tokenMap.id !== undefined) {
      this.subscribeToSaveResponse(this.tokenMapService.update(tokenMap));
    } else {
      this.subscribeToSaveResponse(this.tokenMapService.create(tokenMap));
    }
  }

  private createFromForm(): ITokenMap {
    const entity = {
      ...new TokenMap(),
      id: this.editForm.get(['id']).value,
      token: this.editForm.get(['token']).value,
      entityName: this.editForm.get(['entityName']).value,
      validTo: this.editForm.get(['validTo']).value,
      person: this.editForm.get(['person']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITokenMap>>) {
    result.subscribe((res: HttpResponse<ITokenMap>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
