package FinalProject2.utility;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
		if (year.equals("")) {
			year = Integer.toString(LocalDate.now().getYear());
		}
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
	
	public List<String> getMonthList(String year) {
		LocalDate now = LocalDate.now();
		String nowYear = Integer.toString(now.getYear()); 
		List<String> monthList = new ArrayList<>();
		if(year.equals("") || year.equals(nowYear)) {
			if(now.getMonthValue() < 4) {
				for(int i=now.getMonthValue(); i >= 1; i--) {
					monthList.add(Integer.toString(i));
				}
				for(int i=12; i >= 4; i--) {
					monthList.add(Integer.toString(i));
				}
			} else {
				for(int i=now.getMonthValue(); i >=4 ; i--) {
					monthList.add(Integer.toString(i));
				}
			}
		} else {
			for(int i=3; i >= 1; i--) {
				monthList.add(Integer.toString(i));
			}
			for(int i=12; i >= 4; i--) {
				monthList.add(Integer.toString(i));
			}
		}
		return monthList;
	}

	public LocalDate[] getYear(String year, String month) {
		LocalDate[] years = new LocalDate[2];
		if (month.equals("")) {
			if (year.equals("")) {
				year = Integer.toString(LocalDate.now().getYear());
			}
			years[0] = LocalDate.parse(year + "-4-1", DateTimeFormatter.ofPattern("yyyy-M-d"));
			years[1] = LocalDate.parse(Integer.toString(Integer.parseInt(year)+1) + "-3-31", DateTimeFormatter.ofPattern("yyyy-M-d"));
			
		} else {
			years[0] = LocalDate.parse(year + "-" + month + "-1", DateTimeFormatter.ofPattern("yyyy-M-d"));
			years[1] = years[0].plusMonths(1).minusDays(1);
		}
		return years;
	}

	public LocalDate[] getYear() {
		LocalDate[] years = new LocalDate[2];
		String year = Integer.toString(LocalDate.now().getYear());
		years[0] = LocalDate.parse(year + "-04-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		years[1] = LocalDate.parse(Integer.toString(Integer.parseInt(year)+1) + "-03-31", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		return years;
	}

	public List<String> getMonthList() {
		LocalDate now = LocalDate.now();
		List<String> monthList = new ArrayList<>();
		if(now.getMonthValue() < 4) {
			for(int i=now.getMonthValue(); i >= 1; i--) {
				monthList.add(Integer.toString(i));
			}
			for(int i=12; i >= 4; i--) {
				monthList.add(Integer.toString(i));
			}
		} else {
			for(int i=now.getMonthValue(); i >=4 ; i--) {
				monthList.add(Integer.toString(i));
			}
		}
		return monthList;
	}
}
