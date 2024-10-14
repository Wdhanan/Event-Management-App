import { Component, OnInit } from '@angular/core';
import { EventDto } from '../models/EventDto';
import { DashboardService } from '../service/dashboard.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  events: EventDto[] = [];
  newEvent: EventDto = { id: 0, name: '', description: '', location: '', startDate: '', endDate: '', capacity: 0 };

  constructor(private dashboardService: DashboardService, private router:Router) {}

  ngOnInit(): void {
    this.loadEvents();
  }

  loadEvents(): void {
    this.dashboardService.getAllEvents().subscribe(data => {
      this.events = data;
    });
  }

  createEvent(): void {
    this.dashboardService.createEvent(this.newEvent).subscribe(event => {
      this.events.push(event);
      this.newEvent = { id: 0, name: '', description: '', location: '', startDate: '', endDate: '', capacity: 0 };
    });
  }

  deleteEvent(id: number): void {
    this.dashboardService.deleteEvent(id).subscribe(() => {
      this.events = this.events.filter(event => event.id !== id);
    });
  }

  logout() {
    // Logique pour effacer les tokens et rediriger vers la page de login
    localStorage.removeItem('token'); // Exemple de nettoyage du token
    this.router.navigate(['/login']);
  }

}
