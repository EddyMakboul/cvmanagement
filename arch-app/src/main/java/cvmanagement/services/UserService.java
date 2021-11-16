package cvmanagement.services;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cvmanagement.DTO.cvDTO;
import cvmanagement.entities.User;
import cvmanagement.exception.CustomException;
import cvmanagement.repositories.UserRepository;
import cvmanagement.security.JwtTokenProvider;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private AuthenticationManager authenticationManager;

	public List<cvDTO> getAllUsers(Integer pageNo, Integer pageSize) {

		final Pageable page = PageRequest.of(pageNo, pageSize);

		final Page<User> pageResult = userRepo.findAll(page);
		final ModelMapper modelMapper = new ModelMapper();

		final List<cvDTO> cvDtos = new ArrayList<>();

		for (final User user : pageResult.getContent()) {
			cvDtos.add(modelMapper.map(user, cvDTO.class));
		}

		return cvDtos;
	}

	public cvDTO findById(Long id) {
		final ModelMapper modelMapper = new ModelMapper();
		final User cv = userRepo.findById(id).get();
		return modelMapper.map(cv, cvDTO.class);
	}

	public String login(String email, String password) {
		final User user = userRepo.findUserByEmail(email);

		if (user == null) {
			throw new CustomException("User don't exist", HttpStatus.UNPROCESSABLE_ENTITY);
		}
		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
		}

		return jwtTokenProvider.createToken(email);

	}

	public void signup(User user) {
		if (userRepo.existsByEmail(user.getEmail())) {
			throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepo.save(user);

	}

	public void logout(User user) {
		if (!userRepo.existsByEmail(user.getEmail())) {
			throw new CustomException("User don't exist", HttpStatus.UNPROCESSABLE_ENTITY);
		}

		user.setJwtToken(null);
		userRepo.save(user);
	}
}
