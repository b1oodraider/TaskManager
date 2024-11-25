package TestTasks.TaskTracker.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class PersonDetails implements UserDetails {
    private final PersonEntity personEntity;

    public PersonDetails(PersonEntity personEntity) {
        this.personEntity = personEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.personEntity.getRole()));
    }

    @Override
    public String getPassword() {
        return this.personEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return this.personEntity.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public PersonEntity getPerson() {
        return this.personEntity;
    }
}
