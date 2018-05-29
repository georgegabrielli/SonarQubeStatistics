import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {MetricsComponent} from '../metrics/metrics.component';
import {GroupComponent} from '../group/group.component';
import {PathComponent} from '../path/path.component';

const routes: Routes = [
  {path: 'metrics', component: MetricsComponent},
  {path: 'groups', component: GroupComponent},
  {path: 'groups/paths/:id', component: PathComponent}
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule],
  declarations: []
})
export class AppRoutingModule {
}
