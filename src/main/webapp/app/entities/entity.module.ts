import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'event',
        loadChildren: () => import('./event/event.module').then(m => m.JhipsterCodingEventsEventModule)
      },
      {
        path: 'event-category',
        loadChildren: () => import('./event-category/event-category.module').then(m => m.JhipsterCodingEventsEventCategoryModule)
      },
      {
        path: 'event-details',
        loadChildren: () => import('./event-details/event-details.module').then(m => m.JhipsterCodingEventsEventDetailsModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class JhipsterCodingEventsEntityModule {}
