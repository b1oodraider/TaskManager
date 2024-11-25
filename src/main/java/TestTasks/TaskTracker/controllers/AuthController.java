package TestTasks.TaskTracker.controllers;

import TestTasks.TaskTracker.DTO.AuthDTO;
import TestTasks.TaskTracker.security.CustomAuthenticationProvider;
import TestTasks.TaskTracker.util.JWTUtil;
import TestTasks.TaskTracker.util.Responses.AuthenticationErrorResponse;
import TestTasks.TaskTracker.util.Responses.UserErrorResponse;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final CustomAuthenticationProvider customAuthenticationProvider;


    @PostMapping("/login")
    public Map<String, String> performLogin(@RequestBody AuthDTO authDTO) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authDTO.getEmail(), authDTO.getPassword(), null);
        authenticationManager.authenticate(customAuthenticationProvider.authenticate(authInputToken));
        String token = jwtUtil.generateToken(authDTO.getEmail());
        return Map.of("jwt-token", token);
    }

    @ExceptionHandler
    public ResponseEntity<AuthenticationErrorResponse> handleException(BadCredentialsException e) {
        AuthenticationErrorResponse response = new AuthenticationErrorResponse();
        response.setMessage("Wrong email or password");
        response.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

}
