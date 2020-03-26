import { IEvent } from 'app/shared/model/event.model';

export interface IEventCategory {
  id?: number;
  name?: string;
  events?: IEvent[];
}

export class EventCategory implements IEventCategory {
  constructor(public id?: number, public name?: string, public events?: IEvent[]) {}
}
