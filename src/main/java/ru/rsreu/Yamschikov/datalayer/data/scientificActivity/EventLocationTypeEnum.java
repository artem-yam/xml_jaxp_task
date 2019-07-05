package ru.rsreu.Yamschikov.datalayer.data.scientificActivity;

public enum EventLocationTypeEnum {
	INTERNATIONAL("международная"), ALL_RUSSIAN("всероссийская"), INTERREGIONAL("межрегиональная"), UNIVERSITY_BASED(
			"на базе вуза");

	private String value;

	public static EventLocationTypeEnum getType(String str) {

		if (str.equals(EventLocationTypeEnum.INTERNATIONAL.getValue())) {
			return EventLocationTypeEnum.INTERNATIONAL;
		} else if (str.equals(EventLocationTypeEnum.ALL_RUSSIAN.getValue())) {
			return EventLocationTypeEnum.ALL_RUSSIAN;
		} else if (str.equals(EventLocationTypeEnum.INTERREGIONAL.getValue())) {
			return EventLocationTypeEnum.INTERREGIONAL;
		} else if (str.equals(EventLocationTypeEnum.UNIVERSITY_BASED.getValue())) {
			return EventLocationTypeEnum.UNIVERSITY_BASED;
		}

		return null;
	}

	EventLocationTypeEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
