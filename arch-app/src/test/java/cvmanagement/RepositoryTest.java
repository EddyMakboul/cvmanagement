package cvmanagement;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

}
