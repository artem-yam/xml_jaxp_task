package ru.rsreu.Yamschikov.datalayer.data.scientificActivity;

public enum EventTypeEnum {
	EXHIBITION("выставка"), CONFERENCE("конференция");

	private String value;

	public static EventTypeEnum getType(String str) {

		if (str.equals(EventTypeEnum.EXHIBITION.getValue())) {
			return EventTypeEnum.EXHIBITION;
		} else if (str.equals(EventTypeEnum.CONFERENCE.getValue())) {
			return EventTypeEnum.CONFERENCE;
		}

		return null;
	}

	EventTypeEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
