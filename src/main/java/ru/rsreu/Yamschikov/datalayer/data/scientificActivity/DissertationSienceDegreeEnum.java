package ru.rsreu.Yamschikov.datalayer.data.scientificActivity;

public enum DissertationSienceDegreeEnum {
	DOCTOR("доктор наук"), CANDIDATE("кандидат наук");

	private String value;

	public static DissertationSienceDegreeEnum getType(String str) {

		if (str.equals(DissertationSienceDegreeEnum.DOCTOR.getValue())) {
			return DissertationSienceDegreeEnum.DOCTOR;
		} else if (str.equals(DissertationSienceDegreeEnum.CANDIDATE.getValue())) {
			return DissertationSienceDegreeEnum.CANDIDATE;	
		}

		return null;
	}

	DissertationSienceDegreeEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
