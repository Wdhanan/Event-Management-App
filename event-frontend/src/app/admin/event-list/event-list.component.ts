import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { DashboardService } from '../service/dashboard.service';
import { EventDto } from '../models/EventDto';

@Component({
  selector: 'app-event-list',
  templateUrl: './event-list.component.html',
  styleUrls: ['./event-list.component.scss']
})
export class EventListComponent implements OnInit {

  constructor(private router: Router, private dashboardService: DashboardService) {}
  viewDetails(eventId: number) {
    this.router.navigate(['/dashboard/event-details', eventId]);
  }

  events: EventDto[] = [];
  newEvent: EventDto = { id: 0, name: '', description: '', location: '', startDate: '', endDate: '', capacity: 0 };

 

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

}

