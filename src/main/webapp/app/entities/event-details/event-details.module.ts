import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterCodingEventsSharedModule } from 'app/shared/shared.module';
import { EventDetailsComponent } from './event-details.component';
import { EventDetailsDetailComponent } from './event-details-detail.component';
import { EventDetailsUpdateComponent } from './event-details-update.component';
import { EventDetailsDeleteDialogComponent } from './event-details-delete-dialog.component';
import { eventDetailsRoute } from './event-details.route';

@NgModule({
  imports: [JhipsterCodingEventsSharedModule, RouterModule.forChild(eventDetailsRoute)],
  declarations: [EventDetailsComponent, EventDetailsDetailComponent, EventDetailsUpdateComponent, EventDetailsDeleteDialogComponent],
  entryComponents: [EventDetailsDeleteDialogComponent]
})
export class JhipsterCodingEventsEventDetailsModule {}
