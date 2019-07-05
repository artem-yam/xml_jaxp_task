package ru.rsreu.Yamschikov.datalayer.data.user;

public class User {

	private int id;
	private String login;
	private String password;
	private UserPersonalData userData;
	private UserTypeEnum userType;
	private String status;

	public User(int id, String login, String password, UserPersonalData userData, UserTypeEnum userType,
			String status) {
		super();
		this.id = id;
		this.login = login;
		this.password = password;
		this.userData = userData;
		this.userType = userType;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserPersonalData getUserData() {
		return userData;
	}

	public void setUserData(UserPersonalData userData) {
		this.userData = userData;
	}

	public UserTypeEnum getUserType() {
		return userType;
	}

	public void setUserType(UserTypeEnum userType) {
		this.userType = userType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return getUserData().getFullName().getLastName() + " " + getUserData().getFullName().getFirstName() + " "
				+ getUserData().getFullName().getMiddleName();
	}

}
