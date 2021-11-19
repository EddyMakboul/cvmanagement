package cvmanagement.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cvmanagement.entities.User;
import cvmanagement.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyUserDetails implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		final User appUser = userRepository.findUserByEmail(email);

		if (appUser == null) {
			throw new UsernameNotFoundException("User '" + email + "' not found");
		}

		return org.springframework.security.core.userdetails.User.withUsername(email).password(appUser.getPassword())
				.authorities(appUser.getAppUserRoles()).accountExpired(false).accountLocked(false)
				.credentialsExpired(false).disabled(false).build();

	}

}
