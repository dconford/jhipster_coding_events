import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterCodingEventsTestModule } from '../../../test.module';
import { EventDetailsUpdateComponent } from 'app/entities/event-details/event-details-update.component';
import { EventDetailsService } from 'app/entities/event-details/event-details.service';
import { EventDetails } from 'app/shared/model/event-details.model';

describe('Component Tests', () => {
  describe('EventDetails Management Update Component', () => {
    let comp: EventDetailsUpdateComponent;
    let fixture: ComponentFixture<EventDetailsUpdateComponent>;
    let service: EventDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterCodingEventsTestModule],
        declarations: [EventDetailsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EventDetailsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EventDetailsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EventDetailsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EventDetails(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new EventDetails();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
