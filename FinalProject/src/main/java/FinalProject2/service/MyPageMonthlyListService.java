package FinalProject2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import FinalProject2.model.MyPageMonthlyList;
import FinalProject2.model.UserAccount;

@Service
public class MyPageMonthlyListService {

	@Autowired
	TaskService taskService;
	
	@Autowired
	TaskDetailService taskDService;
	
	@Autowired
	TaskManagementService taskMService;
	
	@Autowired
	EmployeeService employeeService;
	
	public MyPageMonthlyList getById(UserAccount user) {
		MyPageMonthlyList mpml = new MyPageMonthlyList();
		mpml.setGetPoint(taskMService.getPoint(user.getUsername()));
		mpml.setWillGetPoint(taskMService.willGetPoint(user.getUsername()));
		mpml.setNorma(employeeService.findOne(user.getUsername()).get().getEmploymentInfo().getNorma());
		int[] ranks = taskMService.getRanks(mpml.getGetPoint(), user.getUsername());
		mpml.setRankD(ranks[0]);
		mpml.setRankDTotal(ranks[1]);
		mpml.setRankA(ranks[2]);
		mpml.setRankATotal(ranks[3]);
		return mpml;
	}

}
