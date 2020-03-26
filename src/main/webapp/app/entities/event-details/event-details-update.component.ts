import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEventDetails, EventDetails } from 'app/shared/model/event-details.model';
import { EventDetailsService } from './event-details.service';

@Component({
  selector: 'jhi-event-details-update',
  templateUrl: './event-details-update.component.html'
})
export class EventDetailsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    description: [null, [Validators.maxLength(500)]],
    email: []
  });

  constructor(protected eventDetailsService: EventDetailsService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eventDetails }) => {
      this.updateForm(eventDetails);
    });
  }

  updateForm(eventDetails: IEventDetails): void {
    this.editForm.patchValue({
      id: eventDetails.id,
      description: eventDetails.description,
      email: eventDetails.email
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const eventDetails = this.createFromForm();
    if (eventDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.eventDetailsService.update(eventDetails));
    } else {
      this.subscribeToSaveResponse(this.eventDetailsService.create(eventDetails));
    }
  }

  private createFromForm(): IEventDetails {
    return {
      ...new EventDetails(),
      id: this.editForm.get(['id'])!.value,
      description: this.editForm.get(['description'])!.value,
      email: this.editForm.get(['email'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEventDetails>>): void {
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
