package TestTasks.TaskTracker.services;

import TestTasks.TaskTracker.models.PersonDetails;
import TestTasks.TaskTracker.models.PersonEntity;
import TestTasks.TaskTracker.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {
    private final PeopleRepository personRepository;

    @Autowired
    public PersonDetailsService(PeopleRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public PersonDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<PersonEntity> person = personRepository.findByEmail(email);
        if(person.isEmpty()) {
            throw new UsernameNotFoundException("Cannot find person with email: " + email);
        }
        return new PersonDetails(person.get());
    }
}
