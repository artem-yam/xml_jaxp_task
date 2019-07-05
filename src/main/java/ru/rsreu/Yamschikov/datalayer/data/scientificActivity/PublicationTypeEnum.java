package ru.rsreu.Yamschikov.datalayer.data.scientificActivity;

public enum PublicationTypeEnum {
	MONOGRAPH("монография"), COLLECTION_OF_SCIENTIFIC_PAPERS("сборник научных трудов"), TEXTBOOK("учебник"), SCHOOLBOOK(
			"учебное пособие"), ARTICLE("статья");

	private String value;

	public static PublicationTypeEnum getType(String str) {

		if (str.equals(PublicationTypeEnum.MONOGRAPH.getValue())) {
			return PublicationTypeEnum.MONOGRAPH;
		} else if (str.equals(PublicationTypeEnum.COLLECTION_OF_SCIENTIFIC_PAPERS.getValue())) {
			return PublicationTypeEnum.COLLECTION_OF_SCIENTIFIC_PAPERS;
		} else if (str.equals(PublicationTypeEnum.TEXTBOOK.getValue())) {
			return PublicationTypeEnum.TEXTBOOK;
		} else if (str.equals(PublicationTypeEnum.SCHOOLBOOK.getValue())) {
			return PublicationTypeEnum.SCHOOLBOOK;
		} else if (str.equals(PublicationTypeEnum.ARTICLE.getValue())) {
			return PublicationTypeEnum.ARTICLE;
		}

		return null;
	}

	PublicationTypeEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
