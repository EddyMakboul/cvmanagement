package cvmanagement.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cvmanagement.DTO.ActivityDTO;
import cvmanagement.entities.Activity;
import cvmanagement.entities.User;
import cvmanagement.repositories.ActivityRepository;

@Service
public class ActivityService {

	@Autowired
	private ActivityRepository activityRepo;

	public Activity updateActivity(ActivityDTO activity) {
		User us = activityRepo.findById(activity.getIdActivity()).get().getUser();
		Activity activityToSave = transformDTOToActivity(activity);
		activityToSave.setUser(us);
		return activityRepo.save(activityToSave);
	}

	public Activity transformDTOToActivity(ActivityDTO activityDTO) {
		final ModelMapper modelMapper = new ModelMapper();
		Activity activity = modelMapper.map(activityDTO, Activity.class);
		activity.setIdActivity(activityDTO.getIdActivity());
		return activity;
	}

	public Activity remove(long idActivity) {
		Activity activity = activityRepo.findById(idActivity).get();
		if(activity != null) {
			activityRepo.delete(activity);
			return activity;
		}
		return null;
	}

}
