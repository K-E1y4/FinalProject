package FinalProject2.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import FinalProject2.model.BonusPointUse;
import FinalProject2.model.BonusPointUseForm;
import FinalProject2.repository.BonusPointUseRepository;
import FinalProject2.utility.UtilityMethod;

@Service
@Transactional
public class BonusPointUseService {

	@Autowired
	BonusPointUseRepository bPointUR;
	
	public int getSumUsePoint(String username) {
		try {
			return bPointUR.getSumUsePoint(username);
		}catch (Exception e) {
		}
		return 0;
	}

	@Transactional
	public void save(String emp_id, BonusPointUseForm bPUF) {
		UtilityMethod util = new UtilityMethod();
		BonusPointUse bPU = new BonusPointUse();
		bPU.setBonus_point_use_id(getMaxId());
		bPU.setCost_name(bPUF.getCost_name());
		bPU.setCost_division_id(bPUF.getCost_division_id());
		bPU.setBonus_point_use(bPUF.getBonus_point_use());
		bPU.setEmployee_id(emp_id);
		bPU.setDetail(bPUF.getDetail());
		bPU.setUse_date(LocalDate.now());
		bPU.setMake_date(util.getTimestamp());
		bPU.setMake_user(emp_id);
		bPU.setUpdate_date(util.getTimestamp());
		bPU.setUpdate_user(emp_id);
		bPointUR.save(bPU);
	}

	private String getMaxId() {
		int maxNum = bPointUR.getMaxId() + 1;
		String maxId = String.format("%010d", maxNum);
		return maxId;
	}

	public List<BonusPointUse> findByEmployeeId(String employeeId) {
		return bPointUR.findByEmployeeId(employeeId);
	}

	public Page<BonusPointUse> findByEmployeeId(int pageNum, int pageSize, String employeeId) {
		return bPointUR.findByEmployeeId(employeeId, PageRequest.of(pageNum<=0?0:pageNum, pageSize));
	}
}
