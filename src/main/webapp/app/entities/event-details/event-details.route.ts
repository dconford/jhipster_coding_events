import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEventDetails, EventDetails } from 'app/shared/model/event-details.model';
import { EventDetailsService } from './event-details.service';
import { EventDetailsComponent } from './event-details.component';
import { EventDetailsDetailComponent } from './event-details-detail.component';
import { EventDetailsUpdateComponent } from './event-details-update.component';

@Injectable({ providedIn: 'root' })
export class EventDetailsResolve implements Resolve<IEventDetails> {
  constructor(private service: EventDetailsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEventDetails> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((eventDetails: HttpResponse<EventDetails>) => {
          if (eventDetails.body) {
            return of(eventDetails.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EventDetails());
  }
}

export const eventDetailsRoute: Routes = [
  {
    path: '',
    component: EventDetailsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'EventDetails'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EventDetailsDetailComponent,
    resolve: {
      eventDetails: EventDetailsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'EventDetails'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EventDetailsUpdateComponent,
    resolve: {
      eventDetails: EventDetailsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'EventDetails'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EventDetailsUpdateComponent,
    resolve: {
      eventDetails: EventDetailsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'EventDetails'
    },
    canActivate: [UserRouteAccessService]
  }
];
