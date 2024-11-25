package TestTasks.TaskTracker.util.Responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthenticationErrorResponse {
    private String message;
    private LocalDateTime timestamp;
}
