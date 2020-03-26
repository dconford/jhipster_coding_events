import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEventDetails } from 'app/shared/model/event-details.model';
import { EventDetailsService } from './event-details.service';

@Component({
  templateUrl: './event-details-delete-dialog.component.html'
})
export class EventDetailsDeleteDialogComponent {
  eventDetails?: IEventDetails;

  constructor(
    protected eventDetailsService: EventDetailsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.eventDetailsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('eventDetailsListModification');
      this.activeModal.close();
    });
  }
}
