package ru.rsreu.Yamschikov.datalayer.data.scientificActivity;

public enum AwardValueEnum {
	INTERNATIONAL("международный уровень"), FEDERAL("федеральный уровень"), REGIONAL("региональный уровень");

	private String value;

	public static AwardValueEnum getType(String str) {

		if (str.equals(AwardValueEnum.INTERNATIONAL.getValue())) {
			return AwardValueEnum.INTERNATIONAL;
		} else if (str.equals(AwardValueEnum.FEDERAL.getValue())) {
			return AwardValueEnum.FEDERAL;
		} else if (str.equals(AwardValueEnum.REGIONAL.getValue())) {
			return AwardValueEnum.REGIONAL;
		}

		return null;
	}

	AwardValueEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
