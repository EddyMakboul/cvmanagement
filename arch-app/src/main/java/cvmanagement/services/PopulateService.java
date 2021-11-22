package cvmanagement.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cvmanagement.entities.Activity;
import cvmanagement.entities.AppUserRole;
import cvmanagement.entities.User;
import cvmanagement.repositories.ActivityRepository;
import cvmanagement.repositories.UserRepository;

@Service
public class PopulateService {

	@Autowired
	private ActivityRepository activityRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostConstruct
	public void populate() {
		final Date birthDay = new Date();

		final String password = passwordEncoder.encode("password");

		for (int i = 0; i < 1500; i++) {

			final User user = new User("nom" + i, "firstname" + i, "email" + i + "@hotmail.fr", birthDay, password);
			if (i % 2 == 0) {
				user.setWebSite("myWebsite" + i + ".com");
			}
			user.setAppUserRoles(new ArrayList<AppUserRole>(List.of(AppUserRole.ROLE_CLIENT)));
			userRepo.save(user);

			Activity activity = new Activity(2018, "Projet", "MyProject");
			activity.setDescription("une description");
			activity.setWebSite("github");
			activity.setUser(user);
			activity = activityRepo.save(activity);

			activityRepo.save(activity);

		}

	}

}
