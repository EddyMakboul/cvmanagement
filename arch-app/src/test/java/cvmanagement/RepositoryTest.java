package cvmanagement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import cvmanagement.entities.User;
import cvmanagement.repositories.ActivityRepository;
import cvmanagement.repositories.UserRepository;

@SpringBootTest
class RepositoryTest {

	@Autowired
	UserRepository userRepo;

	@Autowired
	ActivityRepository activityRepo;

	private User user = new User("Nom user", "first name user", "email user", new Date(), "password user");

	@BeforeEach
	public void init() {
		user = userRepo.save(user);
	}

	@AfterEach
	public void delete() {
		userRepo.delete(user);
	}

	@Test
	void testSaveUser() {
		User user1 = new User("name1", "firstName1", "email1", new Date(), "pwd1");
		user1 = userRepo.save(user1);

		assertEquals(user1.getNom(), userRepo.findById(user1.getidUser()).get().getNom());

		userRepo.delete(user1);
	}

	@Test
	void testDataNullThrowException() {
		User user1 = new User(null, null, null, null, null);
		assertThrows(DataIntegrityViolationException.class, ()->{
			userRepo.save(user1);
		});
	}

	@Test
	void testUpdateUserAfterSave() {
		User user1 = new User("name1", "firstName1", "email1", new Date(), "pwd1");
		user1 = userRepo.save(user1);

		assertEquals(user1.getNom(), userRepo.findById(user1.getidUser()).get().getNom());
		final String newName = "finalName";
		user1.setNom(newName);
		userRepo.save(user1);
		assertEquals(newName, userRepo.findById(user1.getidUser()).get().getNom());
	}

	@Test
	void testGettingUserByEmail() {
		User user1 = new User("name1", "firstName1", "email1", new Date(), "pwd1");
		User user2 = new User("name1", "firstName1", "email2", new Date(), "pwd1");
		user1 = userRepo.save(user1);
		user2 = userRepo.save(user2);
		assertEquals(user1.getidUser(), userRepo.findUserByEmail(user1.getEmail()).getidUser());
		assertEquals(user2.getidUser(), userRepo.findUserByEmail(user2.getEmail()).getidUser());
	}

}

























