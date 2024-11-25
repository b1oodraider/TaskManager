package TestTasks.TaskTracker.controllers;

import TestTasks.TaskTracker.models.TaskEntity;
import TestTasks.TaskTracker.services.PersonService;
import TestTasks.TaskTracker.services.TaskService;
import TestTasks.TaskTracker.util.Responses.PropertyUpdateErrorResponse;
import TestTasks.TaskTracker.util.Responses.TaskErrorResponse;
import TestTasks.TaskTracker.util.Responses.UserErrorResponse;
import TestTasks.TaskTracker.util.exceptions.PropertyUpdateException;
import TestTasks.TaskTracker.util.exceptions.TaskNotFoundException;
import TestTasks.TaskTracker.util.exceptions.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/Tasks")
@PreAuthorize("hasRole('ADMIN')")
public class TaskController {
    private final TaskService taskService;
    private final PersonService personService;

    public TaskController(TaskService taskService, PersonService personService) {
        this.taskService = taskService;
        this.personService = personService;
    }

    @GetMapping("")
    public List<TaskEntity> index() {
        return taskService.getTasks();
    }

    @PostMapping("")
    public ResponseEntity<Object> createTask(@RequestBody @Valid TaskEntity taskEntity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors(),HttpStatus.BAD_REQUEST);
        }
        taskService.addTask(taskEntity);
        return new ResponseEntity<>("Task created successfully",HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public TaskEntity getTask(@PathVariable int id) {
        return taskService.getTaskById(id);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/{id}")
    public ResponseEntity<Object> addComment(@PathVariable int id, @RequestParam String comment) {
        if(comment.length() < 2 || comment.length() > 250) {
            throw new PropertyUpdateException("Cannot add a comment with less than 2 characters or more than 250 characters");
        }
        taskService.addComment(id, comment);
        return new ResponseEntity<>("Comment : \n"+ comment + "\n added successfully",HttpStatus.OK);
    }

    @PatchMapping("/{id}/contractor")
    public ResponseEntity<Object> updateContractor(@PathVariable int id, @RequestParam int contractor_id) {
        taskService.updateContractor(id, contractor_id);
        return new ResponseEntity<>("Contractor successfully changed to " + personService.getById(contractor_id),HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PatchMapping("/{id}/status")
    public ResponseEntity<Object> updateStatus(@PathVariable int id, @RequestParam String status) {
        taskService.updateStatus(id, status);
        return new ResponseEntity<>("Status successfully changed to " + status,HttpStatus.OK);
    }

    @PatchMapping("/{id}/priority")
    public ResponseEntity<Object> updatePriority(@PathVariable int id, @RequestParam String priority) {
        taskService.updatePriority(id, priority);
        return new ResponseEntity<>("Priority successgetAllfully changed to " + priority,HttpStatus.OK);
    }

    @GetMapping("/getByContractor")
    public List<TaskEntity> getTasksByContractorId(@RequestParam int contractor_id) {
        return taskService.getTasksByContractorId(contractor_id);
    }

    @GetMapping("/getByAuthor")
    public List<TaskEntity> getTasksByAuthorId(@RequestParam int author_id) {
        return taskService.getTasksByAuthorId(author_id);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable int id) {
        taskService.deleteTaskById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted successfully");
    }

    @ExceptionHandler
    public ResponseEntity<PropertyUpdateErrorResponse> handler(PropertyUpdateException e) {
        PropertyUpdateErrorResponse response = new PropertyUpdateErrorResponse();
        response.setMessage(e.getMessage());
        response.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<TaskErrorResponse> handler(TaskNotFoundException e) {
        TaskErrorResponse response = new TaskErrorResponse();
        response.setMessage(e.getMessage());
        response.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handler(UserNotFoundException e) {
        UserErrorResponse response = new UserErrorResponse();
        response.setMessage(e.getMessage());
        response.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }


}
