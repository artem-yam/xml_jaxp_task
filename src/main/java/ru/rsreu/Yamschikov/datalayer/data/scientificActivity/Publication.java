package ru.rsreu.Yamschikov.datalayer.data.scientificActivity;

import java.util.Date;

import ru.rsreu.Yamschikov.datalayer.data.user.User;

public class Publication extends ScientificActivity {

	private PublicationTypeEnum publicationType;
	private int pagesCount;
	private String publishingOfficeName;
	private PublicationPublisherTypeEnum publisherType;

	public Publication() {
		super();
	}

	public Publication(int id, String name, User user, ScientificActivityTypeEnum type, Date date, String description,
			int rating, PublicationTypeEnum publicationType, int pagesCount, String publishingOfficeName,
			PublicationPublisherTypeEnum publisherType) {
		super(id, name, user, type, date, description, rating);

		// определение рейтинга
		int rat = 0;
		switch (publicationType) {
		case ARTICLE:
			if (name.contains("ВАК") || description.contains("ВАК") || publishingOfficeName.contains("ВАК")) {
				rat = 15;
			} else {
				rat = 5;
			}
			break;
		case COLLECTION_OF_SCIENTIFIC_PAPERS:
			break;
		case MONOGRAPH:
			rat = 50;
			break;
		case SCHOOLBOOK:
			if (pagesCount >= 10) {
				rat = 40;
			} else {
				rat = 10;
			}
			break;
		case TEXTBOOK:
			if (pagesCount >= 10) {
				rat = 50;
			} else {
				rat = 20;
			}
			break;
		default:
			break;
		}
		super.setRating(rat);

		this.publicationType = publicationType;
		this.pagesCount = pagesCount;
		this.publishingOfficeName = publishingOfficeName;
		this.publisherType = publisherType;
	}

	public PublicationTypeEnum getPublicationType() {
		return publicationType;
	}

	public void setPublicationType(PublicationTypeEnum publicationType) {
		this.publicationType = publicationType;
	}

	public int getPagesCount() {
		return pagesCount;
	}

	public void setPagesCount(int pagesCount) {
		this.pagesCount = pagesCount;
	}

	public String getPublishingOfficeName() {
		return publishingOfficeName;
	}

	public void setPublishingOfficeName(String publishingOfficeName) {
		this.publishingOfficeName = publishingOfficeName;
	}

	public PublicationPublisherTypeEnum getPublisherType() {
		return publisherType;
	}

	public void setPublisherType(PublicationPublisherTypeEnum publisherType) {
		this.publisherType = publisherType;
	}

}
