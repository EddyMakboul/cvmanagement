package cvmanagement;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import cvmanagement.DTO.ActivityDTO;
import cvmanagement.entities.Activity;
import cvmanagement.repositories.ActivityRepository;
import cvmanagement.services.ActivityService;

@SpringBootTest
class ActivityServiceTest {

	@Autowired
	private ActivityRepository activityRepository;

	@Autowired
	private ActivityService activityService;

	@Test
	void testUpdateActivity() {
		Activity activity1 = new Activity(1999, "expérience", "title");
		activity1 = activityRepository.save(activity1);
		assertEquals(activity1.getNature(), activityRepository.findById(activity1.getIdActivity()).get().getNature());

		//		final ModelMapper modelMapper = new ModelMapper();
		//		final User cv = userRepo.findById(id).get();
		//		return modelMapper.map(cv, cvDTO.class);
		activity1.setNature("Projet");

		final ModelMapper modelMapper = new ModelMapper();
		ActivityDTO activityDTO = modelMapper.map(activity1, ActivityDTO.class);

		activityService.updateActivity(activityDTO);

		assertEquals(activity1.getNature(), activityRepository.findById(activity1.getIdActivity()).get().getNature());



	}

}





















