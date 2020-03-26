export interface IEvent {
  id?: number;
  name?: string;
  eventDetailsId?: number;
  eventCategoryId?: number;
}

export class Event implements IEvent {
  constructor(public id?: number, public name?: string, public eventDetailsId?: number, public eventCategoryId?: number) {}
}
