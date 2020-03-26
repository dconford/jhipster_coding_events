import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEventCategory, EventCategory } from 'app/shared/model/event-category.model';
import { EventCategoryService } from './event-category.service';

@Component({
  selector: 'jhi-event-category-update',
  templateUrl: './event-category-update.component.html'
})
export class EventCategoryUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.minLength(3)]]
  });

  constructor(protected eventCategoryService: EventCategoryService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eventCategory }) => {
      this.updateForm(eventCategory);
    });
  }

  updateForm(eventCategory: IEventCategory): void {
    this.editForm.patchValue({
      id: eventCategory.id,
      name: eventCategory.name
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const eventCategory = this.createFromForm();
    if (eventCategory.id !== undefined) {
      this.subscribeToSaveResponse(this.eventCategoryService.update(eventCategory));
    } else {
      this.subscribeToSaveResponse(this.eventCategoryService.create(eventCategory));
    }
  }

  private createFromForm(): IEventCategory {
    return {
      ...new EventCategory(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEventCategory>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
