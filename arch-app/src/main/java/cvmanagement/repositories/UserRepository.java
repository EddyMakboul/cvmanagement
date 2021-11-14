package cvmanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cvmanagement.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
