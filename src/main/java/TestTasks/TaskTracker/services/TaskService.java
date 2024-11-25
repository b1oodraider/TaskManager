package TestTasks.TaskTracker.services;

import TestTasks.TaskTracker.models.Priority;
import TestTasks.TaskTracker.models.Status;
import TestTasks.TaskTracker.models.TaskEntity;
import TestTasks.TaskTracker.models.TaskCommentsEntity;
import TestTasks.TaskTracker.repositories.TaskCommentsRepository;
import TestTasks.TaskTracker.repositories.TaskRepository;
import TestTasks.TaskTracker.util.exceptions.PropertyUpdateException;
import TestTasks.TaskTracker.util.exceptions.TaskNotFoundException;
import TestTasks.TaskTracker.util.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskCommentsRepository taskCommentsRepository;
    private final PersonService personService;

    @Autowired
    public TaskService(TaskRepository taskRepository, TaskCommentsRepository taskCommentsRepository, PersonService personService) {
        this.taskRepository = taskRepository;
        this.taskCommentsRepository = taskCommentsRepository;
        this.personService = personService;
    }

    public List<TaskEntity> getTasks() {
        return taskRepository.findAll();
    }

    public TaskEntity getTaskById(long id) {
        return taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
    }

    public void addTask(TaskEntity taskEntity) {
        taskRepository.save(taskEntity);
    }

    public void deleteTaskById(long id) {
        Optional<TaskEntity> task = taskRepository.findById(id);
        if (task.isEmpty()) {
            throw new TaskNotFoundException();
        }
        taskRepository.deleteById(id);
    }

    public void updatePriority(long id, String priority) {
        try{
            TaskEntity taskEntity = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
            Priority newPriority = Priority.valueOf(priority);
            taskEntity.setPriority(newPriority);
            taskRepository.saveAndFlush(taskEntity);
        } catch (TaskNotFoundException e) {
            throw new PropertyUpdateException("Cannot update priority of unexisting task: " + id);
        } catch (IllegalArgumentException e) {
            throw new PropertyUpdateException("Cannot set priority \" " + priority + "\"");
        }

    }

    public void updateStatus(long id, String status) {
        try{
            TaskEntity taskEntity = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
            taskEntity.setStatus(Status.valueOf(status));
            taskRepository.saveAndFlush(taskEntity);
        } catch (TaskNotFoundException e) {
            throw new PropertyUpdateException("Cannot update status of unexisting task: " + id);
        } catch (IllegalArgumentException e) {
            throw new PropertyUpdateException("Cannot set status \" " + status + "\"");
        }
    }

    public void updateContractor(long id, int contractor_id) {
        try{
            TaskEntity taskEntity = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
            if(!personService.isPerson(contractor_id)) {
                throw new UserNotFoundException();
            }
            taskEntity.setContractorId(contractor_id);
            taskRepository.saveAndFlush(taskEntity);
        } catch (TaskNotFoundException e) {
            throw new PropertyUpdateException("Cannot update contractor of unexisting task: " + id);
        } catch (UserNotFoundException e) {
            throw new PropertyUpdateException("Cannot set unexisting user: " + contractor_id + " for task: " + id);
        }
    }

    public void addComment(long id, String comment) {
        TaskCommentsEntity tComments = new TaskCommentsEntity();
        tComments.setComment(comment);
        try {
            tComments.setTaskEntity(taskRepository.findById(id).orElseThrow(TaskNotFoundException::new));
        } catch (TaskNotFoundException e) {
            throw new PropertyUpdateException("Cannot add comment to unexisting task: " + id);
        }
        taskCommentsRepository.save(tComments);
    }

    public List<TaskEntity> getTasksByContractorId(int id) {
        if(!personService.isPerson(id)) {
            throw new UserNotFoundException("User with id: " + id + " does not exist");
        }
        return taskRepository.findAllByContractorId(id);
    }

    public List<TaskEntity> getTasksByAuthorId(int id) {
        if(!personService.isPerson(id)) {
            throw new UserNotFoundException("User with id: " + id + " does not exist");
        }
        return taskRepository.findAllByAuthorId(id);
    }
}
