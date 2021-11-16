package cvmanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cvmanagement.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query("Select u From User u where u.email = :email")
	User findUserByEmail(@Param("email") String email);

	boolean existsByEmail(String email);

}
