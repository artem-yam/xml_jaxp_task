package ru.rsreu.Yamschikov.datalayer.data.scientificActivity;

import java.util.Date;

import ru.rsreu.Yamschikov.datalayer.data.user.User;

public class Event extends ScientificActivity {

	private EventTypeEnum eventType;
	private String location;
	private EventLocationTypeEnum locationType;
	private boolean participation;

	public Event() {
		super();
	}

	public Event(int id, String name, User user, ScientificActivityTypeEnum type, Date date, String description,
			int rating, EventTypeEnum eventType, String location, EventLocationTypeEnum locationType,
			boolean participation) {
		super(id, name, user, type, date, description, rating);

		// определение рейтинга
		int rat = 0;
		if (participation) {
			switch (locationType) {
			case ALL_RUSSIAN:
				rat = 5;
				break;
			case INTERNATIONAL:
				rat = 10;
				break;
			case INTERREGIONAL:
				break;
			case UNIVERSITY_BASED:
				break;
			default:
				break;
			}
		}

		super.setRating(rat);

		this.eventType = eventType;
		this.location = location;
		this.locationType = locationType;
		this.participation = participation;
	}

	public EventTypeEnum getEventType() {
		return eventType;
	}

	public void setEventType(EventTypeEnum eventType) {
		this.eventType = eventType;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public EventLocationTypeEnum getLocationType() {
		return locationType;
	}

	public void setLocationType(EventLocationTypeEnum locationType) {
		this.locationType = locationType;
	}

	public boolean isParticipation() {
		return participation;
	}

	public void setParticipation(boolean participation) {
		this.participation = participation;
	}

}
