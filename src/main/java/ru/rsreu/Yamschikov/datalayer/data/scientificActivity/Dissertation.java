package ru.rsreu.Yamschikov.datalayer.data.scientificActivity;

import java.util.Date;

import ru.rsreu.Yamschikov.datalayer.data.user.User;

public class Dissertation extends ScientificActivity {

	private String organization;
	private DissertationSienceDegreeEnum sienceDegree;

	public Dissertation() {
		super();
	}

	public Dissertation(int id, String name, User user, ScientificActivityTypeEnum type, Date date, String description,
			int rating, String organization, DissertationSienceDegreeEnum degree) {
		super(id, name, user, type, date, description, rating);

		// определение рейтинга
		int rat = 0;

		switch (degree) {
		case CANDIDATE:
			rat = 50;
			break;
		case DOCTOR:
			rat = 100;
			break;
		default:
			break;
		}
		super.setRating(rat);

		this.organization = organization;
		this.sienceDegree = degree;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public DissertationSienceDegreeEnum getSienceDegree() {
		return sienceDegree;
	}

	public void setDegree(DissertationSienceDegreeEnum sienceDegree) {
		this.sienceDegree = sienceDegree;
	}

}
