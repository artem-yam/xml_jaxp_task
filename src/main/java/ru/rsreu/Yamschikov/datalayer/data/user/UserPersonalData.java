package ru.rsreu.Yamschikov.datalayer.data.user;

public class UserPersonalData {

	private UserFullName fullName;
	private String phoneNumber;
	private String extraInfo;
	private String departmentName;
	private String degreeAndPost;

	public UserPersonalData(UserFullName fullName, String phoneNumber, String extraInfo, String departmentName,
			String degreeAndPost) {
		super();
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
		this.extraInfo = extraInfo;
		this.departmentName = departmentName;
		this.degreeAndPost = degreeAndPost;
	}

	public UserFullName getFullName() {
		return fullName;
	}

	public void setFullName(UserFullName fullName) {
		this.fullName = fullName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getExtraInfo() {
		return extraInfo;
	}

	public void setExtraInfo(String extraInfo) {
		this.extraInfo = extraInfo;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDegreeAndPost() {
		return degreeAndPost;
	}

	public void setDegreeAndPost(String degreeAndPost) {
		this.degreeAndPost = degreeAndPost;
	}
	
	@Override
	public String toString() {
		return getFullName().getLastName() + " " + getFullName().getFirstName() + " "
				+ getFullName().getMiddleName();
	}

}
