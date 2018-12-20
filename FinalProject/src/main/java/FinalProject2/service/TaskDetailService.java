package FinalProject2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import FinalProject2.repository.TaskDetailRepository;

@Service
public class TaskDetailService {

	@Autowired
	TaskDetailRepository taskDRepository;
}
