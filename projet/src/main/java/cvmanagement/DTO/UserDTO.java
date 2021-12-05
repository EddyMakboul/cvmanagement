package cvmanagement.DTO;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDTO {

	private Long idUser;

	@NotBlank(message = "the name must be not null")
	private String nom;

	@NotBlank(message = "the firstname must be not null")
	private String firstname;

	@Email(message = "must be an email")
	@NotBlank(message = "the email must be not null")
	private String email;

	private String webSite;

	@NotNull(message = "the birthday must be not null")
	private Date birthDay;

	@Size(min = 6, max = 15, message = "the size of the password need 6 as 15 charachtère")
	@NotBlank(message = "the password must be not null")
	private String password;

	public String getNom() {
		return nom;
	}

	public UserDTO(String nom, String firstname, String email, Date birthDay, String password) {
		super();
		this.nom = nom;
		this.firstname = firstname;
		this.email = email;
		this.birthDay = birthDay;
		this.password = password;
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

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public UserDTO() {
		super();
	}

}
