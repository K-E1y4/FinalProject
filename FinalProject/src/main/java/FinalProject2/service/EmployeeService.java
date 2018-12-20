package FinalProject2.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import FinalProject2.model.Department;
import FinalProject2.model.Employee;
import FinalProject2.model.EmployeeSearch;
import FinalProject2.model.EmploymentInfo;
import FinalProject2.model.EmploymentInfoForm;
import FinalProject2.model.Position;
import FinalProject2.model.UserAccount;
import FinalProject2.repository.DepartmentRepository;
import FinalProject2.repository.EmployeeRepository;
import FinalProject2.repository.EmploymentInfoRepository;
import FinalProject2.repository.PositionRepository;

@Service
@Transactional
public class EmployeeService {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	EmployeeRepository repository;
	
	@Autowired
	EmploymentInfoRepository Info_repository;

	@Autowired
	DepartmentRepository departmentRepository;

	@Autowired
	PositionRepository positionRepository;;
	
	//20行ごとにページングするように設定
	private static final int PAGE_SIZE=20;
	
	//アカウント作成時の共通の初期パスワード
	private static final String INITIAL_PASSWORD = "123456";
	
	//アカウント作成時の初期アカウントタイプ
	private static final char INITIAL_ADMIN_TYPE = '0';
	
	private static final LocalDate DEFAULT_QUIT_DATE = LocalDate.of(2222, 02, 22);
	
	//pagination実装のためにListからPageに戻り値の型を変更。キャストも追加
	public Page<Employee> findAll(int page) {
		return repository.findAll(PageRequest.of(page<=0?0:page, PAGE_SIZE));
	}
	
	//検索条件有ページネーション実装の為にEmployeeのListを取得
	public List<Employee> findAll() {
		return repository.findAll();
	}
	
	//検索条件を元に検索
	public Page<Employee> findBySearch(int page, EmployeeSearch employeeSearch) {
		
			String employee_name = employeeSearch.getEmployee_name();
			
			List<Employee> employeesList = findAll();
			
			String employeeId_start = employeeSearch.getEmployeeId_start();
			if(employeeId_start.equals("")) {
				employeeId_start = employeesList.get(0).getEmployee_id();
			}
			
			String employeeId_to = employeeSearch.getEmployeeId_to();
			if(employeeId_to.equals("")) {
				employeeId_to = employeesList.get(employeesList.size() - 1).getEmployee_id();
			}
			
			char sex1 = '0'; char sex2 = '0';
			switch(employeeSearch.getSex()) {
			case "未選択": //未選択の場合
				sex1 = '0';
				sex2 = '1';
				break;
			case "0": //男性の場合
				sex1 = '0';
				sex2 = '0';
				break;
			case "1": //女性の場合
				sex1 = '1';
				sex2 = '1';
				break;
			}
	
			LocalDate now = LocalDate.now(); LocalDate birthday_start = null; LocalDate birthday_to = null;
			switch(employeeSearch.getAge()) {
			case "未選択": //未選択の場合
				birthday_start = now.minusYears(100);
				birthday_to = now.minusYears(1);
				break;
			case "10代":
				birthday_start = now.minusYears(19);
				birthday_to = now.minusYears(10);
				break;
			case "20代":
				birthday_start = now.minusYears(29);
				birthday_to = now.minusYears(20);
				break;
			case "30代":
				birthday_start = now.minusYears(39);
				birthday_to = now.minusYears(30);
				break;
			case "40代":
				birthday_start = now.minusYears(49);
				birthday_to = now.minusYears(40);
				break;
			case "50代":
				birthday_start = now.minusYears(59);
				birthday_to = now.minusYears(50);
				break;
			}
			
			String department_name = employeeSearch.getDepartment_name();
			String position_name = employeeSearch.getPosition_name();
			//indexでvalueの空文字はエラーになるため
			if(department_name.equals("未選択")) department_name = "";
			if(position_name.equals("未選択")) position_name = "";
			
			if(department_name.equals("") && position_name.equals("")) {
					return repository.findBySearch(employeeId_start, employeeId_to, employee_name, 
							birthday_start, birthday_to, sex1, sex2, PageRequest.of(page<=0?0:page, PAGE_SIZE));
			} else {
					return repository.findBySearch(employeeId_start, employeeId_to, employee_name, 
							birthday_start, birthday_to, department_name, position_name, sex1, sex2, 
							PageRequest.of(page<=0?0:page, PAGE_SIZE));
		    }
		
	}
	
	public Optional<Employee> findOne(String employee_id) {
        return repository.findById(employee_id);
    }
	
	@Transactional
    public Employee save(Employee employee) {
		
    	LocalDate quit_date_check = employee.getQuit_date();
    	
    	//退社日が入力されたら（退社したら）、雇用情報（EmploymentInfo）を削除
    	if(quit_date_check != DEFAULT_QUIT_DATE) {
    		
    		Info_repository.deleteById(employee.getEmployee_id());
    		
    	}
        return repository.save(employee);
    }

    @Transactional
    public void delete(String employee_id) {
        	repository.deleteById(employee_id);
    }
    
    public String makeNewEmployeeId() {
    	
			List<Employee> employees = findAll();
	
			int i = 0;
			for(Employee employee: employees) {
					if(Integer.parseInt(employee.getEmployee_id()) > i) {
							i = Integer.parseInt(employee.getEmployee_id());
					}
			}
			String NewEmployeeId = String.format("%4s", String.valueOf(i + 1)).replace(" ", "0");
			
	    	return NewEmployeeId;
    }

	public int getActiveEmployeesNumber() {
			int activeEmployeesNumber =repository.findActiveEmployeeNumber();
			return activeEmployeesNumber;
	}

	public List<String> getPrefecturesList() {
		
		List<String> prefecturesList = Arrays.asList(
				"北海道", "青森県", "岩手県", "宮城県", "秋田県", "山形県", "福島県", "茨城県", "栃木県", 
				"群馬県", "埼玉県", "千葉県", "東京都", "新潟県", "富山県", "石川県", "福井県", "山梨県",
				"長野県", "静岡県", "愛知県", "三重県", "滋賀県", "京都府", "大阪府", "兵庫県", "奈良県",
				"和歌山県", "鳥取県", "島根県", "岡山県", "山口県", "徳島県",  "香川県", "愛媛県", "高知県",
				"福岡県", "佐賀県", "長崎県", "熊本県", "大分県", "宮崎県", "鹿児島県", "沖縄県"
				);

		return prefecturesList;
		
	}

	public void saveFirst(Employee new_employee) {
		
		LocalDate localdate_now = LocalDate.now();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		UserAccount  makeUserAccount = (UserAccount) session.getAttribute("user");
		String makeUserID  = makeUserAccount.getUsername();
		
//		フォーム側では数値のみ入力できるように実装。
		StringBuilder postal_remake = new StringBuilder();
		postal_remake.append(new_employee.getPostal());
		postal_remake.insert(3, "-");
		
		new_employee.setPostal(postal_remake.toString());
		new_employee.setQuit_date(DEFAULT_QUIT_DATE);
		new_employee.setMake_date(timestamp);
		new_employee.setMake_user(makeUserID);
		new_employee.setPassword(passwordEncoder.encode(INITIAL_PASSWORD));
		new_employee.setPassword_update(localdate_now);
		new_employee.setUpdate_date(timestamp);
		new_employee.setUpdate_user(makeUserID);
		new_employee.setAdmin_type(INITIAL_ADMIN_TYPE);
		
		repository.save(new_employee);
		
	}
	
	public void saveFirst(EmploymentInfoForm new_employmentInfoForm) {
		EmploymentInfo new_employment_info = new EmploymentInfo();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		UserAccount  makeUserAccount = (UserAccount) session.getAttribute("user");
		String makeUserID  = makeUserAccount.getUsername();
		new_employment_info.setEmployee_id(new_employmentInfoForm.getEmployee_id());
		Employee employee = repository.getOne(new_employmentInfoForm.getEmployee_id());
		new_employment_info.setEmployee(employee);
		Department department = departmentRepository.getOne(new_employmentInfoForm.getDepartment_id());
		new_employment_info.setDepartment(department);
		Position position = positionRepository.getOne(new_employmentInfoForm.getPosition_id());
		new_employment_info.setPosition(position);
		new_employment_info.setBasic_salary(new_employmentInfoForm.getBasic_salary());
		new_employment_info.setNorma(new_employmentInfoForm.getNorma());
		new_employment_info.setMake_date(timestamp);
		new_employment_info.setMake_user(makeUserID);
		new_employment_info.setUpdate_date(timestamp);
		new_employment_info.setUpdate_user(makeUserID);
		
		Info_repository.save(new_employment_info);
		
	}
	
	public boolean checkPass(String id, String pass) {
        String dbPass = repository.findById(id).get().getPassword();
        return passwordEncoder.matches(pass, dbPass);
	}

	@Transactional
	public void changePass(String username, String newPass) {
	        repository.changePass(username, passwordEncoder.encode(newPass));
	}

	public Optional<EmploymentInfo> findInfo(String employee_id) {
		return Info_repository.findById(employee_id);
	}

	@Transactional
	public void update(Employee employee) {
		
		if(employee.getQuit_date() == null) {
			employee.setQuit_date(DEFAULT_QUIT_DATE);
		}
    	
    	//退社日が入力されたら（退社したら）、雇用情報（EmploymentInfo）を削除
    	if(employee.getQuit_date() != DEFAULT_QUIT_DATE) {
    		Info_repository.deleteById(employee.getEmployee_id());
    	}
		
    	//employeeをsaveする為に必要な情報の取得
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		UserAccount  makeUserAccount = (UserAccount) session.getAttribute("user");
		String updateUserID  = makeUserAccount.getUsername();
		
		Optional<Employee> employee_check =findOne(employee.getEmployee_id());
		if (employee_check.isPresent()){
		    Employee motomoto_employee = employee_check.get();
		    //employeeをsaveするために必要な情報のセット
		    employee.setPassword(motomoto_employee.getPassword());
		    employee.setPassword_update(motomoto_employee.getPassword_update());
		    employee.setAdmin_type(motomoto_employee.getAdmin_type());
		    employee.setEmploymentInfo(motomoto_employee.getEmploymentInfo());
		    employee.setMake_user(motomoto_employee.getMake_user());
		    employee.setMake_date(motomoto_employee.getMake_date());
		    employee.setUpdate_date(timestamp);
			employee.setUpdate_user(updateUserID);
		   
		} else {
		   // alternative processing....
		}
		
		repository.save(employee);
		
	}

	public void updateInfo(EmploymentInfo employmentInfo) {
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		UserAccount  makeUserAccount = (UserAccount) session.getAttribute("user");
		String updateUserID  = makeUserAccount.getUsername();
		
		Optional<EmploymentInfo> employmentInfo_check = findInfo(employmentInfo.getEmployee_id());
		if(employmentInfo_check.isPresent()){
			EmploymentInfo motomoto_employmentInfo = employmentInfo_check.get();
			employmentInfo.setMake_user(motomoto_employmentInfo.getMake_user());
			employmentInfo.setMake_date(motomoto_employmentInfo.getMake_date());
			employmentInfo.setUpdate_user(updateUserID);
			employmentInfo.setUpdate_date(timestamp);
			employmentInfo.setDepartment(motomoto_employmentInfo.getDepartment());
			employmentInfo.setPosition(motomoto_employmentInfo.getPosition());
		}
		
		Info_repository.save(employmentInfo);
		
	}

	public List<String> getYearList(String employeeId) {
		LocalDate hireYear = repository.getHireYear(employeeId);
		int year = 0;
		if(hireYear.getMonth().getValue() <= 3 ) {
			year = hireYear.getYear()-1;
		} else {
			year = hireYear.getYear();
		}
		int now = LocalDate.now().getYear();
		List<String> yearList = new ArrayList<>();
		for(int i = now; i >= year; i--) {
			yearList.add(Integer.toString(i));
		}
		return yearList;
	}

}
