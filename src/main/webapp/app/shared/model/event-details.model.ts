export interface IEventDetails {
  id?: number;
  description?: string;
  email?: string;
  eventId?: number;
}

export class EventDetails implements IEventDetails {
  constructor(public id?: number, public description?: string, public email?: string, public eventId?: number) {}
}
