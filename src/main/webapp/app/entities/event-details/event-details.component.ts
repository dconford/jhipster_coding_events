import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEventDetails } from 'app/shared/model/event-details.model';
import { EventDetailsService } from './event-details.service';
import { EventDetailsDeleteDialogComponent } from './event-details-delete-dialog.component';

@Component({
  selector: 'jhi-event-details',
  templateUrl: './event-details.component.html'
})
export class EventDetailsComponent implements OnInit, OnDestroy {
  eventDetails?: IEventDetails[];
  eventSubscriber?: Subscription;

  constructor(
    protected eventDetailsService: EventDetailsService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.eventDetailsService.query().subscribe((res: HttpResponse<IEventDetails[]>) => (this.eventDetails = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEventDetails();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEventDetails): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEventDetails(): void {
    this.eventSubscriber = this.eventManager.subscribe('eventDetailsListModification', () => this.loadAll());
  }

  delete(eventDetails: IEventDetails): void {
    const modalRef = this.modalService.open(EventDetailsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.eventDetails = eventDetails;
  }
}
