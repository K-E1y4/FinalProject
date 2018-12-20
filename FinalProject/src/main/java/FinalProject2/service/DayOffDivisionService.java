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
import FinalProject2.model.DayoffDivision;
import FinalProject2.model.UserAccount;
import FinalProject2.repository.DayOffDivisionRepository;

@Service
@Transactional
public class DayOffDivisionService {
	
	//20行ごとにページングするように設定
	private static final int PAGE_SIZE=10;
		
	@Autowired
	DayOffDivisionRepository repository;
	
	@Autowired
	HttpSession session;
	
	public Page<DayoffDivision> findAll(int page) {
		return repository.findAll(PageRequest.of(page<=0?0:page, PAGE_SIZE));
	}
	
	public Optional<DayoffDivision> findOne(int dayoff_division_id) {
        return repository.findById(dayoff_division_id);
    }
	
    public DayoffDivision save(DayoffDivision dayoffDivision) {
    	
    	Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		UserAccount  makeUserAccount = (UserAccount) session.getAttribute("user");
		String makeUserID  = makeUserAccount.getUsername();
		
		dayoffDivision.setMake_date(timestamp);
		dayoffDivision.setMake_user(makeUserID);
		dayoffDivision.setUpdate_date(timestamp);
		dayoffDivision.setUpdate_user(makeUserID);
		
        return repository.save(dayoffDivision);
    }

    @Transactional
    public void delete(int dayoff_division_id) {
        repository.deleteById(dayoff_division_id);
    }

	public void update(DayoffDivision dayoffDivision) {
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		UserAccount  makeUserAccount = (UserAccount) session.getAttribute("user");
		String makeUserID  = makeUserAccount.getUsername();
		
		Optional<DayoffDivision> dayoffDivision_check = findById(dayoffDivision.getDayoff_division_id());
		if(dayoffDivision_check.isPresent()) {
			DayoffDivision motomoto_dayoffDivision = dayoffDivision_check.get();
			dayoffDivision.setMake_date(motomoto_dayoffDivision.getMake_date());
			dayoffDivision.setMake_user(motomoto_dayoffDivision.getMake_user());
			dayoffDivision.setUpdate_date(timestamp);
			dayoffDivision.setUpdate_user(makeUserID);
		}
		
		repository.save(dayoffDivision);
		
	}

	private Optional<DayoffDivision> findById(int dayoff_division_id) {
		
		return repository.findById(dayoff_division_id);
	}

	public Object getNewId() {
		
		int max_ID = 0;
		List<DayoffDivision> dayoffDivisionList = repository.findAll();
		for(DayoffDivision dayoffDivision: dayoffDivisionList){
			int ID =  dayoffDivision.getDayoff_division_id();
			if(max_ID < ID) {
				max_ID = ID;
			}
		}
		int newID = max_ID + 1;
		
		return newID;
	}

}
