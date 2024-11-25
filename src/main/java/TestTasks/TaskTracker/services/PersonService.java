package TestTasks.TaskTracker.services;

import TestTasks.TaskTracker.repositories.PeopleRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    private final PeopleRepository personRepository;

    public PersonService(PeopleRepository personRepository) {
        this.personRepository = personRepository;
    }

    public boolean isPerson(String email) {
        return personRepository.findByEmail(email).isPresent();
    }
    public boolean isPerson(int id) {
        return personRepository.findById(id).isPresent();
    }

    public String getById(int id) {
        return personRepository.findById(id).get().getEmail();
    }
}
