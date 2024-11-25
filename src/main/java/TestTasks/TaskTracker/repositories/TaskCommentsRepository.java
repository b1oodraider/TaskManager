package TestTasks.TaskTracker.repositories;

import TestTasks.TaskTracker.models.TaskCommentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskCommentsRepository extends JpaRepository<TaskCommentsEntity, Integer> {
}
