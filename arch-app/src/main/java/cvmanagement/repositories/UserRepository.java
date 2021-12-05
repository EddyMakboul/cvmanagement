package cvmanagement.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cvmanagement.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query("Select u From User u where u.email = :email")
	User findUserByEmail(@Param("email") String email);

	@Query("Select u From User u where u.jwtToken = :jwtToken")
	User findUserByJwt(@Param("jwtToken") String jwtToken);

	boolean existsByEmail(String email);

	boolean existsByJwtToken(String token);

	@Query("Select distinct u from User u Left Join Activity a on u.idUser = a.user.idUser where u.email like %:criteria% OR u.nom like %:criteria% OR u.firstname like %:criteria% OR a.title like %:criteria% ")
	Slice<User> SearchUserByCriteria(@Param("criteria") String criteria, Pageable pageable);

	@Query("Select distinct count(u) from User u Left Join Activity a on u.idUser = a.user.idUser where u.email like %:criteria% OR u.nom like %:criteria% OR u.firstname like %:criteria% OR a.title like %:criteria%")
	Integer sizeOfUser(String criteria);

}
