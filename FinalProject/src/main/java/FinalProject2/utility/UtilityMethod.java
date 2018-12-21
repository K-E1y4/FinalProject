package FinalProject2.utility;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
	
	public LocalDate[] getYear(String year){
		LocalDate[] years = new LocalDate[2];
		years[0] = LocalDate.parse(year + "-04-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		years[1] = LocalDate.parse(Integer.toString(Integer.parseInt(year)+1) + "-03-31", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		return years;
	}
	
	public int getBonusPoint(UserAccount user) {
		int getpoint = taskMRS.getSumBonusPoint(user.getUsername());
		int usepoint = bPointUS.getSumUsePoint(user.getUsername());
		return getpoint - usepoint;
	}

	public LocalDate[] getLast12() {
		LocalDate[] years = new LocalDate[2];
		LocalDate now = LocalDate.now();
		years[0] = now.minusMonths(13).plusDays(1);
		years[1] = now;
		return years;
	}
}
