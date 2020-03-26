import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterCodingEventsTestModule } from '../../../test.module';
import { EventDetailsComponent } from 'app/entities/event-details/event-details.component';
import { EventDetailsService } from 'app/entities/event-details/event-details.service';
import { EventDetails } from 'app/shared/model/event-details.model';

describe('Component Tests', () => {
  describe('EventDetails Management Component', () => {
    let comp: EventDetailsComponent;
    let fixture: ComponentFixture<EventDetailsComponent>;
    let service: EventDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterCodingEventsTestModule],
        declarations: [EventDetailsComponent]
      })
        .overrideTemplate(EventDetailsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EventDetailsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EventDetailsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EventDetails(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.eventDetails && comp.eventDetails[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
