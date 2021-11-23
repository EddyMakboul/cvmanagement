package cvmanagement.DTO;

import java.util.ArrayList;
import java.util.List;

import cvmanagement.entities.Activity;

public class cvDTO {

	private long idUser;

	private String nom;

	private String firstname;

	private String email;

	private String webSite;

	private List<Activity> activities = new ArrayList<>();

	public cvDTO() {
		super();
	}

	public cvDTO(long idUser, String nom, String firstname, String email, String webSite, List<Activity> activities) {
		super();
		this.idUser = idUser;
		this.nom = nom;
		this.firstname = firstname;
		this.email = email;
		this.webSite = webSite;
		this.activities = activities;
	}

	public long getIdUser() {
		return idUser;
	}

	public void setIdUser(long idUser) {
		this.idUser = idUser;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

}
