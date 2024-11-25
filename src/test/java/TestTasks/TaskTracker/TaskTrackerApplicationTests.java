package TestTasks.TaskTracker;

import TestTasks.TaskTracker.models.PersonEntity;
import TestTasks.TaskTracker.models.Priority;
import TestTasks.TaskTracker.models.Status;
import TestTasks.TaskTracker.models.TaskEntity;
import TestTasks.TaskTracker.repositories.PeopleRepository;
import TestTasks.TaskTracker.repositories.TaskCommentsRepository;
import TestTasks.TaskTracker.repositories.TaskRepository;
import TestTasks.TaskTracker.services.TaskService;
import TestTasks.TaskTracker.util.exceptions.TaskNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class TaskTrackerApplicationTests {

	@MockBean
	PeopleRepository peopleRepository;

	@MockBean
	TaskRepository taskRepository;

	@MockBean
	TaskCommentsRepository taskCommentsRepository;

	@MockBean
	TaskService taskService;

	@Autowired
	MockMvc mockMvc;

	@Test
	void testReturn() throws Exception{
		TaskEntity task = new TaskEntity();
		task.setId(1);
		task.setAuthorId(1);
		task.setTitle("Test Task");
		task.setDescription("Test Description");
		task.setPriority(Priority.valueOf("HIGH"));
		task.setStatus(Status.IN_PROGRESS);

		given(taskService.getTaskById(1L)).willReturn(task);
		given(peopleRepository.findByEmail("test_mail@mail.ru")).willReturn(Optional.of(new PersonEntity(1,
				"test_mail@mail.ru",
				"$2a$10$5WEFP8wNfwnEQnizZHLUfuiSDV2Q3GmMEom5.3yeAY226ZPKEvN1u",
				"ROLE_ADMIN")));

		this.mockMvc.perform(get("/Tasks/1").header("Authorization",
				"Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJVc2VyIGRldGFpbHMiLCJlbWFpbCI6InRlc3RfbWFpbEBtYWlsLnJ1IiwiaWF0IjoxNzMyNDkyNTk1LCJpc3MiOiJLYW1hbCIsImV4cCI6MTczMjQ5NjE5NX0.CzYdrR2urD8RcUJvYCyCvaBJbo7BxKlqww_QLfM4XVM"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(header().string("Content-Type", "application/json"))
				.andExpect(content().json("{title : 'Test Task' }"));
	}

	@Test
	void priorityUpdate() throws Exception{
		TaskEntity task = new TaskEntity();
		task.setId(1);
		task.setAuthorId(1);
		task.setTitle("Test Task");
		task.setDescription("Test Description");
		task.setPriority(Priority.valueOf("HIGH"));
		task.setStatus(Status.IN_PROGRESS);

		given(taskService.getTaskById(1L)).willReturn(task);
		given(taskService.getTaskById(2L)).willThrow(new TaskNotFoundException());
		given(taskRepository.findById(2L)).willThrow(new TaskNotFoundException());
		given(peopleRepository.findByEmail("test_mail@mail.ru")).willReturn(Optional.of(new PersonEntity(1,
                "test_mail@mail.ru",
                "$2a$10$5WEFP8wNfwnEQnizZHLUfuiSDV2Q3GmMEom5.3yeAY226ZPKEvN1u",
                "ROLE_ADMIN"))
		);

		this.mockMvc.perform(patch("/Tasks/2/priority?priority")
				.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJVc2VyIGRldGFpbHMiLCJlbWFpbCI6InRlc3RfbWFpbEBtYWlsLnJ1IiwiaWF0IjoxNzMyNDkyNTk1LCJpc3MiOiJLYW1hbCIsImV4cCI6MTczMjQ5NjE5NX0.CzYdrR2urD8RcUJvYCyCvaBJbo7BxKlqww_QLfM4XVM")).andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	void createTask() throws Exception{
		TaskEntity task = new TaskEntity();
		task.setId(1);
		task.setAuthorId(1);
		task.setTitle("Test Task");
		task.setDescription("Test Description");
		task.setPriority(Priority.valueOf("HIGH"));
		task.setStatus(Status.IN_PROGRESS);

		given(peopleRepository.findByEmail("test_mail@mail.ru")).willReturn(Optional.of(new PersonEntity(1,
				"test_mail@mail.ru",
				"$2a$10$5WEFP8wNfwnEQnizZHLUfuiSDV2Q3GmMEom5.3yeAY226ZPKEvN1u",
				"ROLE_ADMIN"))
		);

		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(task);

		this.mockMvc.perform(post("/Tasks")
						.header("Authorization",
								"Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJVc2VyIGRldGFpbHMiLCJlbWFpbCI6InRlc3RfbWFpbEBtYWlsLnJ1IiwiaWF0IjoxNzMyNDkyNTk1LCJpc3MiOiJLYW1hbCIsImV4cCI6MTczMjQ5NjE5NX0.CzYdrR2urD8RcUJvYCyCvaBJbo7BxKlqww_QLfM4XVM")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json)
				)
				.andDo(print())
				.andExpect(status().isCreated());

		TaskEntity task2 = new TaskEntity();
		task.setId(2);
		task.setAuthorId(3);
		task.setTitle("");
		task.setDescription("T");
		task.setPriority(Priority.valueOf("HIGH"));
		task.setStatus(Status.IN_PROGRESS);


		String json2 = objectMapper.writeValueAsString(task2);

		this.mockMvc.perform(post("/Tasks")
						.header("Authorization",
								"Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJVc2VyIGRldGFpbHMiLCJlbWFpbCI6InRlc3RfbWFpbEBtYWlsLnJ1IiwiaWF0IjoxNzMyNDkyNTk1LCJpc3MiOiJLYW1hbCIsImV4cCI6MTczMjQ5NjE5NX0.CzYdrR2urD8RcUJvYCyCvaBJbo7BxKlqww_QLfM4XVM")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json2)
				)
				.andDo(print())
				.andExpect(status().isBadRequest());
	}
}
