package ru.rsreu.Yamschikov.datalayer.data.scientificActivity;

public enum ScientificActivityTypeEnum {
	DISSERTATION("диссертация"), PUBLICATION("публикация"), EVENT("мероприятие"), PATENT("патент"), AWARD("награда");

	private String value;

	public static ScientificActivityTypeEnum getType(String str) {

		if (str.equals(ScientificActivityTypeEnum.DISSERTATION.getValue())) {
			return ScientificActivityTypeEnum.DISSERTATION;
		} else if (str.equals(ScientificActivityTypeEnum.PUBLICATION.getValue())) {
			return ScientificActivityTypeEnum.PUBLICATION;
		} else if (str.equals(ScientificActivityTypeEnum.EVENT.getValue())) {
			return ScientificActivityTypeEnum.EVENT;
		} else if (str.equals(ScientificActivityTypeEnum.PATENT.getValue())) {
			return ScientificActivityTypeEnum.PATENT;
		} else if (str.equals(ScientificActivityTypeEnum.AWARD.getValue())) {
			return ScientificActivityTypeEnum.AWARD;
		}

		return null;
	}

	ScientificActivityTypeEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
