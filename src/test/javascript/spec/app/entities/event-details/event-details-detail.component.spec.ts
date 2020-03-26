import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterCodingEventsTestModule } from '../../../test.module';
import { EventDetailsDetailComponent } from 'app/entities/event-details/event-details-detail.component';
import { EventDetails } from 'app/shared/model/event-details.model';

describe('Component Tests', () => {
  describe('EventDetails Management Detail Component', () => {
    let comp: EventDetailsDetailComponent;
    let fixture: ComponentFixture<EventDetailsDetailComponent>;
    const route = ({ data: of({ eventDetails: new EventDetails(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterCodingEventsTestModule],
        declarations: [EventDetailsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EventDetailsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EventDetailsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load eventDetails on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.eventDetails).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
