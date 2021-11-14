package cvmanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cvmanagement.entities.Activity;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

}
