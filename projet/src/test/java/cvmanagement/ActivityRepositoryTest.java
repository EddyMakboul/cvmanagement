package cvmanagement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.Random;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import cvmanagement.entities.Activity;
import cvmanagement.repositories.ActivityRepository;

@SpringBootTest
@DisplayName("Test sur les fonctionnalités liés au Repository Activité de l'application")
class ActivityRepositoryTest {

	@Autowired
	private ActivityRepository activityRepository;

	@Test
	@DisplayName("Test de l'ajout d'une activité dans la base de données.")
	void testSaveActivity() {
		Activity activity1 = new Activity(1999, "expérience", "title");
		activity1 = activityRepository.save(activity1);
		assertEquals(activity1.getNature(), activityRepository.findById(activity1.getIdActivity()).get().getNature());
		activityRepository.delete(activity1);
	}

	@Test
	@DisplayName("Test de l'ajout d'une activité avec des valeurs obligatoires nulles.")
	void testSaveActivityWithNullDataThrowException() {
		Activity activity1 = new Activity(1999, null, null);
		assertThrows(DataIntegrityViolationException.class, ()->{
			activityRepository.save(activity1);
		});
	}

	@Test
	@DisplayName("Test de la mise à jour d'une activité après sa sauvegarde.")
	void testUpdateActivityAfterSave() {
		Activity activity1 = new Activity(1999, "expérience", "title");
		activity1 = activityRepository.save(activity1);
		assertEquals(activity1.getNature(), activityRepository.findById(activity1.getIdActivity()).get().getNature());

		Random rnd = new Random();
		String newTitle = "new" + (rnd.nextInt()*100) +"Title";
		activity1.setTitle(newTitle);
		activity1 = activityRepository.save(activity1);
		assertEquals(newTitle, activityRepository.findById(activity1.getIdActivity()).get().getTitle());
		activityRepository.delete(activity1);
	}

	@Test
	@DisplayName("Test de la mise à jour d'une activité avec des données incorrecte.")
	void testUpdateActivityAfterSaveWithIncorrectDataThrowException() {
		Activity activity1 = new Activity(1999, "expérience", "title");
		activity1 = activityRepository.save(activity1);
		assertEquals(activity1.getNature(), activityRepository.findById(activity1.getIdActivity()).get().getNature());

		activity1.setTitle(null);
		final Activity activity2 = activity1;
		assertThrows(DataIntegrityViolationException.class, ()->{
			activityRepository.save(activity2);
		});

		activityRepository.delete(activity1);
	}

}



















