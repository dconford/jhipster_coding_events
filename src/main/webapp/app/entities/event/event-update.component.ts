import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IEvent, Event } from 'app/shared/model/event.model';
import { EventService } from './event.service';
import { IEventDetails } from 'app/shared/model/event-details.model';
import { EventDetailsService } from 'app/entities/event-details/event-details.service';
import { IEventCategory } from 'app/shared/model/event-category.model';
import { EventCategoryService } from 'app/entities/event-category/event-category.service';

type SelectableEntity = IEventDetails | IEventCategory;

@Component({
  selector: 'jhi-event-update',
  templateUrl: './event-update.component.html'
})
export class EventUpdateComponent implements OnInit {
  isSaving = false;
  eventdetails: IEventDetails[] = [];
  eventcategories: IEventCategory[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
    eventDetailsId: [],
    eventCategoryId: []
  });

  constructor(
    protected eventService: EventService,
    protected eventDetailsService: EventDetailsService,
    protected eventCategoryService: EventCategoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ event }) => {
      this.updateForm(event);

      this.eventDetailsService
        .query({ filter: 'event-is-null' })
        .pipe(
          map((res: HttpResponse<IEventDetails[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IEventDetails[]) => {
          if (!event.eventDetailsId) {
            this.eventdetails = resBody;
          } else {
            this.eventDetailsService
              .find(event.eventDetailsId)
              .pipe(
                map((subRes: HttpResponse<IEventDetails>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IEventDetails[]) => (this.eventdetails = concatRes));
          }
        });

      this.eventCategoryService.query().subscribe((res: HttpResponse<IEventCategory[]>) => (this.eventcategories = res.body || []));
    });
  }

  updateForm(event: IEvent): void {
    this.editForm.patchValue({
      id: event.id,
      name: event.name,
      eventDetailsId: event.eventDetailsId,
      eventCategoryId: event.eventCategoryId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const event = this.createFromForm();
    if (event.id !== undefined) {
      this.subscribeToSaveResponse(this.eventService.update(event));
    } else {
      this.subscribeToSaveResponse(this.eventService.create(event));
    }
  }

  private createFromForm(): IEvent {
    return {
      ...new Event(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      eventDetailsId: this.editForm.get(['eventDetailsId'])!.value,
      eventCategoryId: this.editForm.get(['eventCategoryId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEvent>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
