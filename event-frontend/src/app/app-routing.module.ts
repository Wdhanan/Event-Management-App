import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { DashboardComponent } from './admin/dashboard/dashboard.component';
import { CreateEventComponent } from './admin/create-event/create-event.component';
import { EventDetailsComponent } from './admin/event-details/event-details.component';
import { EventListComponent } from './admin/event-list/event-list.component';


const routes: Routes = [
  { path: '', redirectTo: 'auth/register', pathMatch: 'full' }, // Redirection correcte
  { 
    path: 'auth', 
    loadChildren: () => import('./auth/auth.module').then(m => m.AuthModule) 
  },
  { path: 'login', component: LoginComponent },
  { path: 'dashboard', component: DashboardComponent,
    children: [
      { path: 'create-event', component: CreateEventComponent },
      { path: 'event-list', component: EventListComponent },
      { path: 'event-details/:id', component: EventDetailsComponent }
    ]
   }
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
