package cvmanagement.security;

import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import cvmanagement.entities.AppUserRole;
import cvmanagement.exception.CustomException;
import cvmanagement.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {

	/**
	 * THIS IS NOT A SECURE PRACTICE! For simplicity, we are storing a static key
	 * here. Ideally, in a microservices environment, this key would be kept on a
	 * config-server.
	 */
	@Value("${security.jwt.token.secret-key:secret-key}")
	private String secretKey;

	@Value("${security.jwt.token.expire-length:3600000}")
	private final long validityInMilliseconds = 3600000; // 1h

	@Autowired
	private MyUserDetails myUserDetails;
	@Autowired
	private UserRepository userRepo;

	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	public String createToken(String username) {

		final Claims claims = Jwts.claims().setSubject(username);
		claims.put("auth", AppUserRole.ROLE_CLIENT);

		final Date now = new Date();
		final Date validity = new Date(now.getTime() + validityInMilliseconds);

		return Jwts.builder()//
				.setClaims(claims)//
				.setIssuedAt(now)//
				.setExpiration(validity)//
				.signWith(SignatureAlgorithm.HS256, secretKey)//
				.compact();
	}

	public Authentication getAuthentication(String token) {
		final UserDetails userDetails = myUserDetails.loadUserByUsername(getEmail(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	public String getEmail(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}

	public String resolveToken(HttpServletRequest req) {
		final String bearerToken = req.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return tokenExist(token);
		} catch (JwtException | IllegalArgumentException e) {
			throw new CustomException("Expired or invalid JWT token", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private boolean tokenExist(String token) {
		final Boolean user = userRepo.existsByJwtToken(token);

		return user;
	}

}
