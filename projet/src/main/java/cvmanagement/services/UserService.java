package cvmanagement.services;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cvmanagement.DTO.UserDTO;
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

	public cvDTO findByIdDTO(Long id) {
		final ModelMapper modelMapper = new ModelMapper();
		final User cv = userRepo.findById(id).get();
		return modelMapper.map(cv, cvDTO.class);
	}

	public User findById(Long id) {
		return userRepo.findById(id).get();
	}

	public User findByJwt(String jwtToken) {
		return userRepo.findUserByJwt(jwtToken);
	}

	public String login(String email, String password) {
		final User user = userRepo.findUserByEmail(email);

		if (user == null) {
			throw new CustomException("User don't exist", HttpStatus.UNPROCESSABLE_ENTITY);
		}
		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
		}
		final String jwt = jwtTokenProvider.createToken(email);
		user.setJwtToken(jwt);
		userRepo.save(user);
		return jwt;

	}

	public void logout(User user) {
		if (!userRepo.existsByEmail(user.getEmail())) {
			throw new CustomException("User don't exist", HttpStatus.UNPROCESSABLE_ENTITY);
		}

		user.setJwtToken(null);
		userRepo.save(user);
	}

	public String updateUser(HttpServletRequest req, UserDTO cv) {
		final User user = whoami(req);
		user.setEmail(cv.getEmail());
		user.setWebSite(cv.getWebSite());
		user.setNom(cv.getNom());
		user.setFirstname(cv.getFirstname());
		user.setBirthDay(cv.getBirthDay());
		final String jwt = refresh(user.getEmail());
		user.setJwtToken(jwt);
		userRepo.save(user);
		return jwt;

	}

	public User whoami(HttpServletRequest req) {
		return userRepo.findUserByEmail(jwtTokenProvider.getEmail(jwtTokenProvider.resolveToken(req)));
	}

	public List<cvDTO> searchUsers(String criteria, Integer pageNo, Integer pageSize) {
		final Pageable page = PageRequest.of(pageNo, pageSize);
		final Slice<User> pageResult = userRepo.SearchUserByCriteria(criteria, page);
		final List<cvDTO> cvDtos = new ArrayList<>();
		final ModelMapper modelMapper = new ModelMapper();

		for (final User user : pageResult.getContent()) {
			cvDtos.add(modelMapper.map(user, cvDTO.class));
		}

		return cvDtos;
	}

	public Integer getDataSize(String criteria) {
		return userRepo.sizeOfUser(criteria);
	}

	public void createUser(UserDTO userDTO) {

		if (userRepo.existsByEmail(userDTO.getEmail())) {
			throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
		}

		final ModelMapper modelMapper = new ModelMapper();

		final User user = modelMapper.map(userDTO, User.class);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepo.save(user);

	}

	public String refresh(String email) {
		return jwtTokenProvider.createToken(email);
	}
}
