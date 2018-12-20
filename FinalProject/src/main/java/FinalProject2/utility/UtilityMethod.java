package FinalProject2.utility;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import FinalProject2.model.UserAccount;
import FinalProject2.service.BonusPointUseService;
import FinalProject2.service.CostDivisionService;
import FinalProject2.service.TaskMonthlyResultService;

import java.sql.Date;;

public class UtilityMethod {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	TaskMonthlyResultService taskMRS;
	
	@Autowired
	BonusPointUseService bPointUS;
	
	public Timestamp getTimestamp() {
		Timestamp ts = new Timestamp((new java.util.Date()).getTime());
		return ts;
	}
	
	public int getBonusPoint(UserAccount user) {
		int getpoint = taskMRS.getSumBonusPoint(user.getUsername());
		int usepoint = bPointUS.getSumUsePoint(user.getUsername());
		return getpoint - usepoint;
	}
}
