package TestTasks.TaskTracker.repositories;

import TestTasks.TaskTracker.models.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    List<TaskEntity> findAllByContractorId(int id);
    List<TaskEntity> findAllByAuthorId(int id);
}
