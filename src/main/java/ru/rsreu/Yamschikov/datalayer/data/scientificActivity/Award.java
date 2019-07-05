package ru.rsreu.Yamschikov.datalayer.data.scientificActivity;

import java.util.Date;

import ru.rsreu.Yamschikov.datalayer.data.user.User;

public class Award extends ScientificActivity {

	private AwardTypeEnum awardType;
	private AwardValueEnum awardValue;

	public Award() {
		super();
	}

	public Award(int id, String name, User user, ScientificActivityTypeEnum type, Date date, String description,
			int rating, AwardTypeEnum awardType, AwardValueEnum awardValue) {
		super(id, name, user, type, date, description, rating);

		// определение рейтинга
		int rat = 0;

		switch (awardValue) {
		case FEDERAL:
			rat = 20;
			break;
		case INTERNATIONAL:
			rat = 20;
			break;
		case REGIONAL:
			rat = 10;
			break;
		default:
			break;
		}
		super.setRating(rat);

		this.awardType = awardType;
		this.awardValue = awardValue;
	}

	public AwardTypeEnum getAwardType() {
		return awardType;
	}

	public void setAwardType(AwardTypeEnum awardType) {
		this.awardType = awardType;
	}

	public AwardValueEnum getAwardValue() {
		return awardValue;
	}

	public void setAwardValue(AwardValueEnum awardValue) {
		this.awardValue = awardValue;
	}

}
