package cvmanagement.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cvmanagement.DTO.ActivityDTO;
import cvmanagement.entities.Activity;
import cvmanagement.services.ActivityService;

@RequestMapping("api/activities")
@Controller
public class ActivityController {

	@Autowired
	ActivityService activityService;

	@Autowired
	private LocalValidatorFactoryBean validators;

	@PutMapping()
	public ResponseEntity<Activity> updateActivity(@RequestBody ActivityDTO activity) {
		try {
			final Activity activityResult = activityService.updateActivity(activity);
			return new ResponseEntity<>(activityResult, HttpStatus.OK);
		} catch (final Exception exception) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}

	@DeleteMapping()
	public ResponseEntity<Void> removeActivity(@RequestParam Long idActivity) {
		try {
			activityService.remove(idActivity);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (final Exception exception) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}

	@PostMapping()
	public ResponseEntity<Map<String, String>> addActivity(@RequestBody ActivityDTO activityDTO) {
		final Set<ConstraintViolation<ActivityDTO>> constraints = validators.validate(activityDTO);

		final Map<String, String> errors = new HashMap<>();

		for (final ConstraintViolation<ActivityDTO> constraint : constraints) {
			errors.put(constraint.getPropertyPath().toString(), constraint.getMessage());
		}
		if (errors.isEmpty()) {
			try {
				activityService.add(activityDTO);
				return new ResponseEntity<>(HttpStatus.OK);
			} catch (final Exception exception) {
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			}
		}
		return new ResponseEntity<>(errors, HttpStatus.CONFLICT);

	}

}
