package cvmanagement.services;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cvmanagement.DTO.cvDTO;
import cvmanagement.entities.User;
import cvmanagement.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;

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
}
