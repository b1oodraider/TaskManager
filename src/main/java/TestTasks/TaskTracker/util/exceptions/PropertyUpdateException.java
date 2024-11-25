package TestTasks.TaskTracker.util.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PropertyUpdateException extends RuntimeException {
    private String message;
}
