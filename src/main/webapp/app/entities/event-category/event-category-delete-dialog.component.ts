import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEventCategory } from 'app/shared/model/event-category.model';
import { EventCategoryService } from './event-category.service';

@Component({
  templateUrl: './event-category-delete-dialog.component.html'
})
export class EventCategoryDeleteDialogComponent {
  eventCategory?: IEventCategory;

  constructor(
    protected eventCategoryService: EventCategoryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.eventCategoryService.delete(id).subscribe(() => {
      this.eventManager.broadcast('eventCategoryListModification');
      this.activeModal.close();
    });
  }
}
