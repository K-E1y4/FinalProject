package FinalProject2.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import FinalProject2.model.DayoffDivision;
import FinalProject2.model.Department;
import FinalProject2.model.Employee;
import FinalProject2.model.EmploymentInfo;
import FinalProject2.model.UserAccount;
import FinalProject2.repository.DepartmentRepository;

@Service
@Transactional
public class DepartmentService {
	
	//20行ごとにページングするように設定
	private static final int PAGE_SIZE=10;
		
	@Autowired
	DepartmentRepository repository;
	
	@Autowired
	HttpSession session;
	
	public Department getOne(String id) {
		return repository.getOne(id);
	}
	public Page<Department> findAll(int page) {
		return repository.findAll(PageRequest.of(page<=0?0:page, PAGE_SIZE));
	}
	
	//従業員が現在所属している部署のみを抽出
	public List<Department> findAll_activeDepartment(){
		List<Department> activeDepartmentList = new ArrayList<Department>();
		List<Department> allDepartments = repository.findAll();
		for(Department department: allDepartments) {
			List<EmploymentInfo> employmentInfoList = department.getEmploymentInfoList();
			if(employmentInfoList.size() != 0) {
				activeDepartmentList.add(department);
			}
		}
		return activeDepartmentList;
	}

	public List<Department> findAll() {
		return repository.findAll();
	}

	public Optional<Department> findById(String department_id) {
		
		return repository.findById(department_id);
		
	}

	public void update(Department department) {
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		UserAccount  makeUserAccount = (UserAccount) session.getAttribute("user");
		String makeUserID  = makeUserAccount.getUsername();
		
		Optional<Department> department_check = findById(department.getDepartment_id());
		if(department_check.isPresent()) {
			Department motomoto_department = department_check.get();
			department.setMake_date(motomoto_department.getMake_date());
			department.setMake_user(motomoto_department.getMake_user());
			department.setUpdate_date(timestamp);
			department.setUpdate_user(makeUserID);
			department.setEmploymentInfoList(motomoto_department.getEmploymentInfoList());
		}
		
		repository.save(department);

	}

	public String getNewId() {
		
		int max_ID = 0;
		List<Department> departmentList = repository.findAll();
		//部署マスタの最後の要素は　「99 その他」になるため、これを除いて新規IDを作成する
		departmentList.remove(departmentList.size() - 1);
		for(Department department: departmentList){
			int ID =  Integer.parseInt(department.getDepartment_id());
			if(max_ID < ID) {
				max_ID = ID;
			}
		}
		String newID = String.valueOf(max_ID + 1);
		
		return newID;
	}

	public void save(Department new_department) {
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		UserAccount  makeUserAccount = (UserAccount) session.getAttribute("user");
		String makeUserID  = makeUserAccount.getUsername();
		
		new_department.setMake_date(timestamp);
		new_department.setMake_user(makeUserID);
		new_department.setUpdate_date(timestamp);
		new_department.setUpdate_user(makeUserID);
		
		repository.save(new_department);
		
	}

	public void delete(String department_id) {
		repository.deleteById(department_id);
	}

}
