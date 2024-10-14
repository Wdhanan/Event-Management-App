import { Component } from '@angular/core';
import { EventDto } from '../models/EventDto';
import { DashboardService } from '../service/dashboard.service';

@Component({
  selector: 'app-create-event',
  templateUrl: './create-event.component.html',
  styleUrls: ['./create-event.component.scss']
})
export class CreateEventComponent {

  events: EventDto[] = [];
  newEvent: EventDto = { id: 0, name: '', description: '', location: '', startDate: '', endDate: '', capacity: 0 };

  constructor(private dashboardService: DashboardService) {}

  createEvent(): void {
    this.dashboardService.createEvent(this.newEvent).subscribe(event => {
      this.events.push(event);
      this.newEvent = { id: 0, name: '', description: '', location: '', startDate: '', endDate: '', capacity: 0 };
    });
  }

}
