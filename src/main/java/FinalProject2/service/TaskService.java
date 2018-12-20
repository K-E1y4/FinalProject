package FinalProject2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import FinalProject2.repository.TaskRepository;

@Service
public class TaskService {

	@Autowired
	TaskRepository taskRepository;
}
