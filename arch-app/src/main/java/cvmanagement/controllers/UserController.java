package cvmanagement.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cvmanagement.DTO.UserDTO;
import cvmanagement.DTO.cvDTO;
import cvmanagement.entities.User;
import cvmanagement.exception.CustomException;
import cvmanagement.security.JwtTokenProvider;
import cvmanagement.services.UserService;

@RequestMapping("api/users")
@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private JwtTokenProvider provider;

	@Autowired
	private LocalValidatorFactoryBean validators;

	@GetMapping
	public ResponseEntity<List<cvDTO>> getAllCvDTO(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "15") Integer pageSize) {

		final List<cvDTO> usersDTO = userService.getAllUsers(pageNo, pageSize);

		return new ResponseEntity<>(usersDTO, HttpStatus.OK);

	}

	@GetMapping("/size")
	public ResponseEntity<Integer> getDataSize(@RequestParam(defaultValue = "") String criteria) {

		final Integer size = userService.getDataSize(criteria);

		return new ResponseEntity<>(size, HttpStatus.OK);
	}

	@GetMapping("/search")
	public ResponseEntity<List<cvDTO>> searchCvDto(@RequestParam(required = true) String criteria,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "15") Integer pageSize) {

		final List<cvDTO> usersDTO = userService.searchUsers(criteria, pageNo, pageSize);

		return new ResponseEntity<>(usersDTO, HttpStatus.OK);

	}

	@GetMapping("/{id}")
	public ResponseEntity<cvDTO> getcvDTO(@PathVariable Long id) {

		final cvDTO cvDTO = userService.findById(id);

		return new ResponseEntity<>(cvDTO, HttpStatus.OK);
	}

	@PostMapping("/signin")
	public ResponseEntity<String> signin(@RequestParam String email, @RequestParam String password) {

		try {
			final String jwtToken = userService.login(email, password);
			return new ResponseEntity<>(jwtToken, HttpStatus.OK);
		} catch (final CustomException e) {
			return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
		}

	}

	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody User user) {

		try {
			userService.signup(user);
			return new ResponseEntity<>("ok", HttpStatus.OK);
		} catch (final CustomException e) {
			return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
		}

	}

	@PostMapping("/logout")
	public ResponseEntity<String> logout(HttpServletRequest req) {

		try {
			userService.logout(userService.whoami(req));
			return new ResponseEntity<>("ok", HttpStatus.OK);
		} catch (final CustomException e) {
			return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
		}

	}

	@PutMapping()
	public ResponseEntity<cvDTO> updateUser(HttpServletRequest req, @RequestBody cvDTO cv) {

		try {
			// provider.tokenExist(provider.resolveToken(req));
			final cvDTO cvDTO = userService.updateUser(req, cv);

			return new ResponseEntity<>(cvDTO, HttpStatus.OK);

		} catch (final Exception e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

	}

	@PostMapping()
	public ResponseEntity<Map<String, String>> createUser(HttpServletRequest req, @RequestBody UserDTO user) {
		final Set<ConstraintViolation<UserDTO>> constraints = validators.validate(user);
		final Map<String, String> errors = new HashMap<>();

		for (final ConstraintViolation<UserDTO> constraint : constraints) {
			errors.put(constraint.getPropertyPath().toString(), constraint.getMessage());
		}

		if (errors.isEmpty()) {
			userService.createUser(user);
			return new ResponseEntity<>(HttpStatus.OK);
		}

		return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
	}

	@GetMapping("/isconnected")
	public ResponseEntity<Boolean> isConnected(HttpServletRequest req) {

		final String token = provider.resolveToken(req);
		if (provider.validateToken(token)) {
			final User user = userService.whoami(req);
			if (user != null) {
				return new ResponseEntity<>(true, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(false, HttpStatus.OK);

	}
}
