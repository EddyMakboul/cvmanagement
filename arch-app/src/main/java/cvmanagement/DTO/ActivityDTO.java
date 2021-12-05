package cvmanagement.DTO;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import cvmanagement.entities.User;

public class ActivityDTO {

	private long idActivity;

	@NotNull(message = "must be not null")
	@Min(value = 1, message = "must be not null")
	private int year;

	@NotBlank(message = "must be not null")
	private String nature;

	@NotBlank(message = "must be not null")
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
