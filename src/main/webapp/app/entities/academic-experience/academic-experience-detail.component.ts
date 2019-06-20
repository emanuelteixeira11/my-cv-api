import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAcademicExperience } from 'app/shared/model/academic-experience.model';

@Component({
  selector: 'jhi-academic-experience-detail',
  templateUrl: './academic-experience-detail.component.html'
})
export class AcademicExperienceDetailComponent implements OnInit {
  academicExperience: IAcademicExperience;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ academicExperience }) => {
      this.academicExperience = academicExperience;
    });
  }

  previousState() {
    window.history.back();
  }
}
