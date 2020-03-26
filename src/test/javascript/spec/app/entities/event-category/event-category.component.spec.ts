import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterCodingEventsTestModule } from '../../../test.module';
import { EventCategoryComponent } from 'app/entities/event-category/event-category.component';
import { EventCategoryService } from 'app/entities/event-category/event-category.service';
import { EventCategory } from 'app/shared/model/event-category.model';

describe('Component Tests', () => {
  describe('EventCategory Management Component', () => {
    let comp: EventCategoryComponent;
    let fixture: ComponentFixture<EventCategoryComponent>;
    let service: EventCategoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterCodingEventsTestModule],
        declarations: [EventCategoryComponent]
      })
        .overrideTemplate(EventCategoryComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EventCategoryComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EventCategoryService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EventCategory(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.eventCategories && comp.eventCategories[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
