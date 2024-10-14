export interface EventDto {
    id: number;
    name: string;
    description: string;
    location: string;
    startDate: string; // Utilisez string pour le format ISO
    endDate: string; 
    capacity: number;
  }
  