package cvmanagement.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(indexes = @Index(columnList = "nom, firstname, email"))
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idUser;

	@Basic
	@Column(nullable = false)
	private String nom;

	@Basic
	@Column(nullable = false)
	private String firstname;

	@Basic
	@Column(nullable = false)
	private String email;

	@Basic
	@Column
	private String webSite;

	@Basic
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date birthDay;

	@Basic
	@Column(nullable = false)
	private String password;

	@OneToMany(mappedBy = "user")
	private final List<Activity> activities = new ArrayList<>();

	private String jwtToken;

	@ElementCollection(fetch = FetchType.EAGER)
	List<AppUserRole> appUserRoles;

	public List<AppUserRole> getAppUserRoles() {
		return appUserRoles;
	}

	public void setAppUserRoles(List<AppUserRole> appUserRoles) {
		this.appUserRoles = appUserRoles;
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

	public User() {
		super();
	}

	public User(String nom, String firstname, String email, Date birthDay, String password) {
		super();
		this.nom = nom;
		this.firstname = firstname;
		this.email = email;
		this.birthDay = birthDay;
		this.password = password;
	}

	public Long getidUser() {
		return idUser;
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

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Activity> getActivities() {
		return activities;
	}

}
