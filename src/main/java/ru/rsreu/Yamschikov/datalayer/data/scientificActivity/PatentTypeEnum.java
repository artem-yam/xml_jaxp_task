package ru.rsreu.Yamschikov.datalayer.data.scientificActivity;

public enum PatentTypeEnum {
	RUSSIAN_PATENT("патент Росcии"), FOREIGN_PATENT("зарубежный патент"), SUPPORTED_PATENT("поддерживаемый патент");

	private String value;

	public static PatentTypeEnum getType(String str) {

		if (str.equals(PatentTypeEnum.RUSSIAN_PATENT.getValue())) {
			return PatentTypeEnum.RUSSIAN_PATENT;
		} else if (str.equals(PatentTypeEnum.FOREIGN_PATENT.getValue())) {
			return PatentTypeEnum.FOREIGN_PATENT;
		} else if (str.equals(PatentTypeEnum.SUPPORTED_PATENT.getValue())) {
			return PatentTypeEnum.SUPPORTED_PATENT;
		}

		return null;
	}

	PatentTypeEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
