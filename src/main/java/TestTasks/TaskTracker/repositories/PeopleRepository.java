package TestTasks.TaskTracker.repositories;

import TestTasks.TaskTracker.models.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PeopleRepository extends JpaRepository<PersonEntity, Integer> {
    Optional<PersonEntity> findByEmail(String email);

    Optional<PersonEntity> findById(int id);
}
