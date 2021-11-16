package cvmanagement.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cvmanagement.DTO.cvDTO;
import cvmanagement.services.UserService;

@RequestMapping("api/users")
@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<List<cvDTO>> getAllCvDTO(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "15") Integer pageSize) {

		final List<cvDTO> usersDTO = userService.getAllUsers(pageNo, pageSize);

		return new ResponseEntity<>(usersDTO, HttpStatus.OK);

	}

	@GetMapping("/{id}")
	public ResponseEntity<cvDTO> getcvDTO(@PathVariable Long id) {

		final cvDTO cvDTO = userService.findById(id);

		return new ResponseEntity<>(cvDTO, HttpStatus.OK);
	}
}
