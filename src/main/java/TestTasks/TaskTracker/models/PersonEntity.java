package TestTasks.TaskTracker.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="Person")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonEntity {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="email")
    @Email
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="role")
    private String role;

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", password='" + password + '\'' +
                '}';
    }
}

