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
import FinalProject2.model.EmploymentInfo;
import FinalProject2.model.Position;
import FinalProject2.model.UserAccount;
import FinalProject2.repository.EmploymentInfoRepository;
import FinalProject2.repository.PositionRepository;

@Service
@Transactional
public class PositionService {
	
	//20行ごとにページングするように設定
	private static final int PAGE_SIZE=10;

	@Autowired
	PositionRepository repository;
	
	@Autowired
	EmploymentInfoRepository info_repository;
	
	@Autowired
	HttpSession session;
	
	public Page<Position> findAll(int page) {
		return repository.findAll(PageRequest.of(page<=0?0:page, PAGE_SIZE));
	}
	
	//従業員が現在所属している役職のみを抽出
		public List<Position> findAll_activePosition(){
			List<Position> activePositionList = new ArrayList<Position>();
			List<Position> allPositions = repository.findAll();
			for(Position position: allPositions) {
				List<EmploymentInfo> employmentInfoList = position.getEmploymentInfoList();
				if(employmentInfoList.size() != 0) {
					activePositionList.add(position);
				}
			}
			return activePositionList;
		}

		public List<Position> findAll() {
			return repository.findAll();
		}

		public void update(Position position) {
			
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			UserAccount  makeUserAccount = (UserAccount) session.getAttribute("user");
			String makeUserID  = makeUserAccount.getUsername();
			
			Optional<Position> position_check = findById(position.getPosition_id());
			if(position_check.isPresent()) {
				Position motomoto_position = position_check.get();
				position.setMake_date(motomoto_position.getMake_date());
				position.setMake_user(motomoto_position.getMake_user());
				position.setUpdate_date(timestamp);
				position.setUpdate_user(makeUserID);
			}
			
			repository.save(position);
			
		}

		private Optional<Position> findById(int position_id) {
			return repository.findById(position_id);
		}

		public int getNewId() {
			
			int max_ID = 0;
			List<Position> positionList = repository.findAll();
			for(Position position: positionList){
				int ID =  position.getPosition_id();
				if(max_ID < ID) {
					max_ID = ID;
				}
			}
			int newID = max_ID + 1;
			
			return newID;
		}

		public void save(Position new_position) {
			
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			UserAccount  makeUserAccount = (UserAccount) session.getAttribute("user");
			String makeUserID  = makeUserAccount.getUsername();
			
			new_position.setMake_date(timestamp);
			new_position.setMake_user(makeUserID);
			new_position.setUpdate_date(timestamp);
			new_position.setUpdate_user(makeUserID);
			
	        repository.save(new_position);
			
		}

		public void delete(int position_id) {
			// TODO 自動生成されたメソッド・スタブ
			repository.deleteById(position_id);
		}
		
}
