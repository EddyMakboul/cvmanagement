package cvmanagement.DTO;

import cvmanagement.entities.User;

public class ActivityDTO {

	private long idActivity;

	private int year;

	private String nature;

	private String title;

	private String description;

	private String webSite;

	private User user;

	public ActivityDTO() {
		super();
	}

	public ActivityDTO(int year, String nature, String title) {
		super();
		this.year = year;
		this.nature = nature;
		this.title = title;
	}

	public long getIdActivity() {
		return idActivity;
	}

	public void setIdActivity(long idActivity) {
		this.idActivity = idActivity;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}



}
