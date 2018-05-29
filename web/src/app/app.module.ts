import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { MetricsComponent } from './metrics/metrics.component';
import {AppRoutingModule} from './app-routing/app-routing.module';
import {FormsModule} from '@angular/forms';
import { GroupComponent } from './group/group.component';
import { GroupListComponent } from './group/group-list/group-list.component';
import {GroupService} from './commons/group.service';
import {HttpClientModule} from '@angular/common/http';
import { PathComponent } from './path/path.component';
import { PathListComponent } from './path/path-list/path-list.component';

@NgModule({
  declarations: [
    AppComponent,
    MetricsComponent,
    GroupComponent,
    GroupListComponent,
    PathComponent,
    PathListComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [GroupService],
  bootstrap: [AppComponent]
})
export class AppModule { }
