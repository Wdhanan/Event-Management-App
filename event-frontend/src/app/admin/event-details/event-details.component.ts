import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { EventDto } from '../models/EventDto';

@Component({
  selector: 'app-event-details',
  templateUrl: './event-details.component.html',
  styleUrls: ['./event-details.component.scss']
})
export class EventDetailsComponent {

  event: EventDto | undefined;

constructor(private route: ActivatedRoute) {}

ngOnInit() {
  const eventId = this.route.snapshot.paramMap.get('id');
  if (eventId) {
    // Appelle un service pour récupérer les détails de l'événement par ID
    this.loadEventDetails(+eventId);
  }
}

loadEventDetails(id: number) {
  // Logique pour charger les détails de l’événement depuis le backend
}

editEvent(){

}

deleteEvent(){
  
}

}
