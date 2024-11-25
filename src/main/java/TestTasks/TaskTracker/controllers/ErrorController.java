package TestTasks.TaskTracker.controllers;

import TestTasks.TaskTracker.util.Responses.UserErrorResponse;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@ControllerAdvice
@RestController
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @ExceptionHandler
    public Error handleException(final JWTVerificationException exception) {
        return new Error(exception.getMessage());
    }

//    @RequestMapping("/error")
//    public ResponseEntity<Object> error(final HttpServletRequest request) {
//        request.setAttribute("javax.servlet.error.status_code", HttpStatus.UNAUTHORIZED);
//        request.setAttribute("javax.servlet.error.message", "Unauthorized");
//        return new ResponseEntity<>(new UserErrorResponse("JWT verification failed: Token is invalid or expired", LocalDateTime.now()), HttpStatus.UNAUTHORIZED);
//    }

}
