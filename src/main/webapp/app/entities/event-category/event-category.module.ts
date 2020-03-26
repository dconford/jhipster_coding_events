import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterCodingEventsSharedModule } from 'app/shared/shared.module';
import { EventCategoryComponent } from './event-category.component';
import { EventCategoryDetailComponent } from './event-category-detail.component';
import { EventCategoryUpdateComponent } from './event-category-update.component';
import { EventCategoryDeleteDialogComponent } from './event-category-delete-dialog.component';
import { eventCategoryRoute } from './event-category.route';

@NgModule({
  imports: [JhipsterCodingEventsSharedModule, RouterModule.forChild(eventCategoryRoute)],
  declarations: [EventCategoryComponent, EventCategoryDetailComponent, EventCategoryUpdateComponent, EventCategoryDeleteDialogComponent],
  entryComponents: [EventCategoryDeleteDialogComponent]
})
export class JhipsterCodingEventsEventCategoryModule {}
