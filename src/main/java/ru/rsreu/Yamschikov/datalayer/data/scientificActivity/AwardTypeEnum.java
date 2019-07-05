package ru.rsreu.Yamschikov.datalayer.data.scientificActivity;

public enum AwardTypeEnum {
	PRIZE("премия"), AWARD("награда"), DIPLOMA("диплом"), MEDAL("медаль");

	private String value;

	public static AwardTypeEnum getType(String str) {

		if (str.equals(AwardTypeEnum.PRIZE.getValue())) {
			return AwardTypeEnum.PRIZE;
		} else if (str.equals(AwardTypeEnum.AWARD.getValue())) {
			return AwardTypeEnum.AWARD;
		} else if (str.equals(AwardTypeEnum.DIPLOMA.getValue())) {
			return AwardTypeEnum.DIPLOMA;
		} else if (str.equals(AwardTypeEnum.MEDAL.getValue())) {
			return AwardTypeEnum.MEDAL;
		}

		return null;
	}

	AwardTypeEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
