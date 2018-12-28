package FinalProject2.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import FinalProject2.model.Department;
import FinalProject2.model.Position;
import FinalProject2.model.TaskDivision;
import FinalProject2.model.UserAccount;
import FinalProject2.repository.DepartmentRepository;
import FinalProject2.repository.TaskDivisionRepository;

@Service
@Transactional
public class TaskDivisionService {
	
	//20行ごとにページングするように設定
	private static final int PAGE_SIZE=10;
		
	@Autowired
	TaskDivisionRepository repository;
	
	@Autowired
	HttpSession session;
	
	public Page<TaskDivision> findAll(int page) {
		return repository.findAll(PageRequest.of(page<=0?0:page, PAGE_SIZE));
	}

	public void update(TaskDivision taskDivision) {
		
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			UserAccount  makeUserAccount = (UserAccount) session.getAttribute("user");
			String makeUserID  = makeUserAccount.getUsername();
			
			TaskDivision motomoto_taskDivision = findById(taskDivision.getTask_division_id());
			taskDivision.setDetail(motomoto_taskDivision.getDetail());
			taskDivision.setMake_date(motomoto_taskDivision.getMake_date());
			taskDivision.setMake_user(motomoto_taskDivision.getMake_user());
			taskDivision.setUpdate_date(timestamp);
			taskDivision.setUpdate_user(makeUserID);
		
			repository.save(taskDivision);
	}

	public TaskDivision findById(int task_division_id) {
		
		Optional<TaskDivision> taskDivision_check = repository.findById(task_division_id);
		TaskDivision taskDivision = null;
		if(taskDivision_check.isPresent()) {
			taskDivision = taskDivision_check.get();
		}
		return taskDivision;
	}

	public Object getNewId() {
		
		int max_ID = 0;
		List<TaskDivision> taskDivisionList = repository.findAll();
		for(TaskDivision taskDivision: taskDivisionList){
			int ID =  taskDivision.getTask_division_id();
			if(max_ID < ID) {
				max_ID = ID;
			}
		}
		int newID = max_ID + 1;
		
		return newID;
	}

	public void save(TaskDivision new_taskDivision) {
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		UserAccount  makeUserAccount = (UserAccount) session.getAttribute("user");
		String makeUserID  = makeUserAccount.getUsername();
		
		new_taskDivision.setDetail("備考");
		new_taskDivision.setMake_date(timestamp);
		new_taskDivision.setMake_user(makeUserID);
		new_taskDivision.setUpdate_date(timestamp);
		new_taskDivision.setUpdate_user(makeUserID);
		
        repository.save(new_taskDivision);
		
	}

	public void delete(int task_division_id) {
		// TODO 自動生成されたメソッド・スタブ
		repository.deleteById(task_division_id);
	}

	public List<TaskDivision> findAll() {
		
		return repository.findAll();
	}

}
