package TestTasks.TaskTracker.util.validators;

import TestTasks.TaskTracker.models.PersonEntity;
import TestTasks.TaskTracker.services.PersonService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {
    private final PersonService personService;

    public PersonValidator(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return PersonEntity.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PersonEntity personEntity = (PersonEntity) target;
        if(personService.isPerson(personEntity.getEmail()))
            errors.rejectValue("username", "", "Username already exists");
    }
}
