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

import FinalProject2.model.CostDivision;
import FinalProject2.model.Employee;
import FinalProject2.model.TaskDivision;
import FinalProject2.model.UserAccount;
import FinalProject2.repository.CostDivisionRepository;

@Service
@Transactional
public class CostDivisionService {
	
	//20行ごとにページングするように設定
	private static final int PAGE_SIZE=10;
	
	@Autowired
	HttpSession session;
	
	@Autowired
	CostDivisionRepository repository;
	
	public List<CostDivision> findAll() {
	    return repository.findAll();
	}
	
	public Page<CostDivision> findAll(int page) {
		return repository.findAll(PageRequest.of(page<=0?0:page, PAGE_SIZE));
	}
	
	public Optional<CostDivision> findOne(int cost_division_id) {
        return repository.findById(cost_division_id);
    }
	
    public CostDivision save(CostDivision costDivision) {
    	
    	Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		UserAccount  makeUserAccount = (UserAccount) session.getAttribute("user");
		String makeUserID  = makeUserAccount.getUsername();
		
		costDivision.setMake_date(timestamp);
		costDivision.setMake_user(makeUserID);
		costDivision.setUpdate_date(timestamp);
		costDivision.setUpdate_user(makeUserID);
		
        return repository.save(costDivision);
    }

    @Transactional
    public void delete(int cost_division_id) {
        repository.deleteById(cost_division_id);
    }

	public void update(CostDivision costDivision) {
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		UserAccount  makeUserAccount = (UserAccount) session.getAttribute("user");
		String makeUserID  = makeUserAccount.getUsername();
		
		Optional<CostDivision> costDivision_check = findById(costDivision.getCost_division_id());
		if(costDivision_check.isPresent()) {
			CostDivision motomoto_costDivision = costDivision_check.get();
			costDivision.setMake_date(motomoto_costDivision.getMake_date());
			costDivision.setMake_user(motomoto_costDivision.getMake_user());
			costDivision.setUpdate_date(timestamp);
			costDivision.setUpdate_user(makeUserID);
		}
		
		repository.save(costDivision);
		
	}

	private Optional<CostDivision> findById(int cost_division_id) {
		
		return repository.findById(cost_division_id);
	}

	public int getNewId() {
		
		int max_ID = 0;
		List<CostDivision> costDivisionList = repository.findAll();
		for(CostDivision costDivision: costDivisionList){
			int ID =  costDivision.getCost_division_id();
			if(max_ID < ID) {
				max_ID = ID;
			}
		}
		int newID = max_ID + 1;
		
		return newID;
	}

}
