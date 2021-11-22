package cvmanagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import cvmanagement.DTO.ActivityDTO;
import cvmanagement.entities.Activity;
import cvmanagement.services.ActivityService;

@RequestMapping("api/activities")
@Controller
public class ActivityController {

	@Autowired
	ActivityService activityService;

	@PutMapping
	public ResponseEntity<Activity> updateActivity(@RequestBody ActivityDTO activity){
		try {
			Activity activityResult = activityService.updateActivity(activity);
			return new ResponseEntity<>(activityResult, HttpStatus.OK);
		}catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}

}
