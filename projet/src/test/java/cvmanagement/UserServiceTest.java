package cvmanagement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import cvmanagement.DTO.UserDTO;
import cvmanagement.DTO.cvDTO;
import cvmanagement.entities.User;
import cvmanagement.exception.CustomException;
import cvmanagement.repositories.UserRepository;
import cvmanagement.services.UserService;

@SpringBootTest
@DisplayName("Test sur les fonctionnalités liés au Service User de l'application")
class UserServiceTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@ParameterizedTest
	@Transactional
	@CsvSource({ "0,15", "0,60", "0, 35", "1,1" })
	@DisplayName("Test de récupéreration de tous les utilisateurs par ensemble.")
	void testGetAllUsers(int pageNo, int pageSize) {
		User user1 = new User("name1", "firstName1", "email1", new Date(), "pwd1");
		user1 = userRepository.save(user1);

		final List<cvDTO> listUser = userService.searchUsers("", pageNo, pageSize);
		assertTrue((listUser.size() > 0) && (listUser.size() <= pageSize));
		userRepository.delete(user1);
	}

	@Test
	@Transactional
	@DisplayName("Test de récupération d'un utilisateur grâce à son ID.")
	void testGetUserById() {
		User user1 = new User("name1", "firstName1", "email1", new Date(), "pwd1");
		user1 = userRepository.save(user1);

		final User cv = userService.findById(user1.getidUser());
		assertEquals(user1.getNom(), cv.getNom());
		assertEquals(user1.getFirstname(), cv.getFirstname());
	}

	@Test
	@DisplayName("Test du login du service.")
	void testLogin() {
		final String password = passwordEncoder.encode("password");
		User user1 = new User("name1", "firstName1", "email1@", new Date(), password);
		user1 = userRepository.save(user1);

		final String jwtToken = userService.login(user1.getEmail(), "password");
		assertEquals(jwtToken, userRepository.findById(user1.getidUser()).get().getJwtToken());
		userRepository.delete(user1);
	}

	@Test
	@DisplayName("Test des exceptions du login.")
	void testLoginWithNullUser() {
		assertThrows(CustomException.class, () -> {
			userService.login(null, "password");
		});
	}

	@Test
	@DisplayName("Test des exceptions du login.")
	void testLoginWithBadPassword() {
		User user1 = new User("name1", "firstName1", "email1@", new Date(), "pwd1");
		user1 = userRepository.save(user1);

		final String mailUser1 = user1.getEmail();
		final String pwdUser1 = user1.getPassword();
		assertThrows(CustomException.class, () -> {
			userService.login(mailUser1, pwdUser1);
		});
		userRepository.delete(user1);
	}

	@Test
	@DisplayName("Test du logout du service.")
	void testLogout() {
		final String password = passwordEncoder.encode("password");
		User user1 = new User("name1", "firstName1", "email1@", new Date(), password);
		user1 = userRepository.save(user1);

		final String jwtToken = userService.login(user1.getEmail(), "password");
		assertEquals(jwtToken, userRepository.findById(user1.getidUser()).get().getJwtToken());

		userService.logout(user1);
		assertNull(userRepository.findById(user1.getidUser()).get().getJwtToken());

		userRepository.delete(user1);
	}

	@Test
	@DisplayName("Test de l'exception du logout quand le user est null.")
	void testLogoutWithUserNull() {
		User user1 = new User("name1", "firstName1", "email1@", new Date(), "pwd1");
		user1 = userRepository.save(user1);
		final User user2 = user1;
		user2.setEmail("");
		assertThrows(CustomException.class, () -> {
			userService.logout(user2);
		});

		userRepository.delete(user1);
	}

	@Test
	@DisplayName("Test de l'enregistrement d'un utilisateur.")
	void testSignUp() {
		final UserDTO user1 = new UserDTO("name1", "firstName1", "email1@", new Date(), "pwd1");
		userService.createUser(user1);
		assertNotEquals("pwd1", userRepository.findUserByEmail(user1.getEmail()).getPassword());
		userRepository.delete(userRepository.findUserByEmail(user1.getEmail()));
	}

	@Test
	@DisplayName("Test de l'exception du signin quand le username existe déjà.")
	void testSignUpWhenUsernameAlreadyExistThrowException() {
		User user1 = new User("name1", "firstName1", "email1", new Date(), "pwd1");
		user1 = userRepository.save(user1);
		final ModelMapper mapper = new ModelMapper();
		final UserDTO user2 = mapper.map(user1, UserDTO.class);
		assertThrows(CustomException.class, () -> {
			userService.createUser(user2);
		});
	}

	@ParameterizedTest
	@ValueSource(strings = { "email1", "Christophe", "Jean" })
	@DisplayName("Test de la recherche d'un utilisateur en fonction d'un critère.")
	@Transactional
	void testSearchUsers(String criteria) {
		User user1 = new User("name1", "firstName1", criteria + "@", new Date(), "pwd1");
		user1 = userRepository.save(user1);

		final List<cvDTO> listUser = userService.searchUsers(criteria, 0, 15);
		assertTrue(listUser.size() >= 1);

		userRepository.delete(user1);
	}

	@ParameterizedTest
	@ValueSource(strings = { "email1", "Christophe", "Jean" })
	@DisplayName("Test de la récupération du nombre d'utilisateur correspondant à un critère.")
	@Transactional
	void testGetDataSize(String criteria) {
		User user1 = new User("name1", "firstName1", criteria + "@", new Date(), "pwd1");
		user1 = userRepository.save(user1);

		assertTrue(userService.getDataSize(criteria) >= 1);
		userRepository.delete(user1);
	}

}
