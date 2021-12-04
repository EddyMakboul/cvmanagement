package cvmanagement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import cvmanagement.entities.User;
import cvmanagement.repositories.UserRepository;

@SpringBootTest
@DisplayName("Test sur les fonctionnalités liés au Repository User de l'application")
class UserRepositoryTest {

	@Autowired
	UserRepository userRepo;

	@Test
	@DisplayName("Test de la sauvegarde d'un utilisateur.")
	void testSaveUser() {
		User user1 = new User("name1", "firstName1", "email1", new Date(), "pwd1");
		user1 = userRepo.save(user1);

		assertEquals(user1.getNom(), userRepo.findById(user1.getidUser()).get().getNom());

		userRepo.delete(user1);
	}

	@Test
	@DisplayName("Test de la sauvegarde d'un utilisateur avec des valeurs obligatoires qui sont nulles.")
	void testDataNullThrowException() {
		User user1 = new User(null, null, null, null, null);
		assertThrows(DataIntegrityViolationException.class, ()->{
			userRepo.save(user1);
		});
	}

	@Test
	@DisplayName("Test de la mise à jour d'un utilisateur après sauvegarde dans la base de donnée.")
	void testUpdateUserAfterSave() {
		User user1 = new User("name1", "firstName1", "email1", new Date(), "pwd1");
		user1 = userRepo.save(user1);

		assertEquals(user1.getNom(), userRepo.findById(user1.getidUser()).get().getNom());
		final String newName = "finalName";
		user1.setNom(newName);
		userRepo.save(user1);
		assertEquals(newName, userRepo.findById(user1.getidUser()).get().getNom());
		userRepo.delete(user1);
	}

	@Test
	@DisplayName("Test mise à jour avec des données incorrectes.")
	void testInsertNullDataWhenUpdating() {
		User user1 = new User("name1", "firstName1", "email1", new Date(), "pwd1");
		user1 = userRepo.save(user1);

		assertEquals(user1.getNom(), userRepo.findById(user1.getidUser()).get().getNom());
		user1.setNom(null);
		final User newUser = user1;
		assertThrows(DataIntegrityViolationException.class, ()->{
			userRepo.save(newUser);
		});
		userRepo.delete(user1);
	}

	@ParameterizedTest
	@CsvSource({
		"email_1.fr,email_2.fr",
		"email_1, email_2"
	})
	@DisplayName("Test de la récupération des utilisateurs via leur email.")
	void testGettingUserByEmail(String email1, String email2) {
		User user1 = new User("name1", "firstName1", email1, new Date(), "pwd1");
		User user2 = new User("name1", "firstName1", email2, new Date(), "pwd1");
		user1 = userRepo.save(user1);
		user2 = userRepo.save(user2);
		assertEquals(user1.getidUser(), userRepo.findUserByEmail(user1.getEmail()).getidUser());
		assertEquals(user2.getidUser(), userRepo.findUserByEmail(user2.getEmail()).getidUser());
		userRepo.delete(user1);
		userRepo.delete(user2);
	}

	@ParameterizedTest
	@ValueSource(strings = {"email1", "email2"})
	@DisplayName("Test de l'existence d'un utilisateur à partir d'un mail.")
	void testVerifyExistingUserByEmail(String email) {
		User user1 = new User("name1", "firstName1", email, new Date(), "pwd1");
		user1 = userRepo.save(user1);
		assertTrue(userRepo.existsByEmail(email));
		userRepo.delete(user1);
	}

	@ParameterizedTest
	@ValueSource(strings = {"Philipe", "Christophe", "Bernard"})
	@DisplayName("Test de la récupération d'utilisateurs en fonction d'un critère.")
	void testGetUserByCriteria(String criteria) {
		User user1 = new User(criteria, "firstName1", "email1", new Date(), "pwd1");
		user1 = userRepo.save(user1);
		assertTrue(userRepo.sizeOfUser(criteria)>=1);
		User user2 = new User("name1", "firstName1", criteria +"@", new Date(), "pwd1");
		user2 = userRepo.save(user2);
		assertTrue(userRepo.sizeOfUser(criteria)>=2);
	}

}

























