import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { JhipsterCodingEventsSharedModule } from 'app/shared/shared.module';
import { JhipsterCodingEventsCoreModule } from 'app/core/core.module';
import { JhipsterCodingEventsAppRoutingModule } from './app-routing.module';
import { JhipsterCodingEventsHomeModule } from './home/home.module';
import { JhipsterCodingEventsEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    JhipsterCodingEventsSharedModule,
    JhipsterCodingEventsCoreModule,
    JhipsterCodingEventsHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    JhipsterCodingEventsEntityModule,
    JhipsterCodingEventsAppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent]
})
export class JhipsterCodingEventsAppModule {}
