import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterCodingEventsTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { EventCategoryDeleteDialogComponent } from 'app/entities/event-category/event-category-delete-dialog.component';
import { EventCategoryService } from 'app/entities/event-category/event-category.service';

describe('Component Tests', () => {
  describe('EventCategory Management Delete Component', () => {
    let comp: EventCategoryDeleteDialogComponent;
    let fixture: ComponentFixture<EventCategoryDeleteDialogComponent>;
    let service: EventCategoryService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterCodingEventsTestModule],
        declarations: [EventCategoryDeleteDialogComponent]
      })
        .overrideTemplate(EventCategoryDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EventCategoryDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EventCategoryService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
