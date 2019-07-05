package ru.rsreu.Yamschikov.datalayer.data.user;

public enum UserTypeEnum {
	ADMIN("администратор"), WORKER("сотрудник");

	private String value;

	public static UserTypeEnum getType(String str) {

		if (str.equals(UserTypeEnum.ADMIN.getValue())) {
			return UserTypeEnum.ADMIN;
		} else if (str.equals(UserTypeEnum.WORKER.getValue())) {
			return UserTypeEnum.WORKER;
		}

		return null;
	}

	UserTypeEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
