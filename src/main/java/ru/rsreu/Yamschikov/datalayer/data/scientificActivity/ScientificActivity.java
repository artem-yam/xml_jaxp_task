package ru.rsreu.Yamschikov.datalayer.data.scientificActivity;

import java.util.Date;

import ru.rsreu.Yamschikov.datalayer.data.user.User;

public class ScientificActivity {

	private int id;
	private String name;
	private User owner;
	private ScientificActivityTypeEnum type;
	private Date date;
	private String description;
	private int rating;
	
	public ScientificActivity() {
		
	}

	public ScientificActivity(int id, String name, User owner, ScientificActivityTypeEnum type, Date date,
			String description, int rating) {
		super();
		this.id = id;
		this.name = name;
		this.owner = owner;
		this.type = type;
		this.date = date;
		this.description = description;
		this.rating = rating;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public ScientificActivityTypeEnum getType() {
		return type;
	}

	public void setType(ScientificActivityTypeEnum type) {
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

}
