import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEventCategory } from 'app/shared/model/event-category.model';
import { EventCategoryService } from './event-category.service';
import { EventCategoryDeleteDialogComponent } from './event-category-delete-dialog.component';

@Component({
  selector: 'jhi-event-category',
  templateUrl: './event-category.component.html'
})
export class EventCategoryComponent implements OnInit, OnDestroy {
  eventCategories?: IEventCategory[];
  eventSubscriber?: Subscription;

  constructor(
    protected eventCategoryService: EventCategoryService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.eventCategoryService.query().subscribe((res: HttpResponse<IEventCategory[]>) => (this.eventCategories = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEventCategories();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEventCategory): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEventCategories(): void {
    this.eventSubscriber = this.eventManager.subscribe('eventCategoryListModification', () => this.loadAll());
  }

  delete(eventCategory: IEventCategory): void {
    const modalRef = this.modalService.open(EventCategoryDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.eventCategory = eventCategory;
  }
}
