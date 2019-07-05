package ru.rsreu.Yamschikov.datalayer.data.scientificActivity;

import java.util.Date;

import ru.rsreu.Yamschikov.datalayer.data.user.User;

public class Patent extends ScientificActivity {

	private PatentTypeEnum patentType;
	private String declarant;

	public Patent() {
		super();
	}

	public Patent(int id, String name, User user, ScientificActivityTypeEnum type, Date date, String description,
			int rating, PatentTypeEnum patentType, String declarant) {
		super(id, name, user, type, date, description, rating);

		// определение рейтинга
		int rat = 20;
		super.setRating(rat);

		this.patentType = patentType;
		this.declarant = declarant;
	}

	public PatentTypeEnum getPatentType() {
		return patentType;
	}

	public void setPatentType(PatentTypeEnum patentType) {
		this.patentType = patentType;
	}

	public String getDeclarant() {
		return declarant;
	}

	public void setDeclarant(String declarant) {
		this.declarant = declarant;
	}

}
