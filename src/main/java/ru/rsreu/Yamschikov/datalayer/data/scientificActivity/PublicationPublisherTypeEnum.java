package ru.rsreu.Yamschikov.datalayer.data.scientificActivity;

public enum PublicationPublisherTypeEnum {
	FOREIGN("зарубежное издательство"), RUSSIAN("российское издательство"), UNIVERSITY("вузовское издательство");

	private String value;

	public static PublicationPublisherTypeEnum getType(String str) {

		if (str.equals(PublicationPublisherTypeEnum.FOREIGN.getValue())) {
			return PublicationPublisherTypeEnum.FOREIGN;
		} else if (str.equals(PublicationPublisherTypeEnum.RUSSIAN.getValue())) {
			return PublicationPublisherTypeEnum.RUSSIAN;
		} else if (str.equals(PublicationPublisherTypeEnum.UNIVERSITY.getValue())) {
			return PublicationPublisherTypeEnum.UNIVERSITY;
		}

		return null;
	}

	PublicationPublisherTypeEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
