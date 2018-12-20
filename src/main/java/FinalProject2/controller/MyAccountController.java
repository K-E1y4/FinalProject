package FinalProject2.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import FinalProject2.service.EmployeeService;
import FinalProject2.model.Employee;
import FinalProject2.model.PasswordChangeCheck;
import FinalProject2.model.UserAccount;

@Controller
@RequestMapping("/myaccount")
public class MyAccountController {
	
	@Autowired
	HttpSession session;

	@Autowired
	EmployeeService employeeService;
	
	@GetMapping
	public String myacount(Model model) {
		UserAccount user = (UserAccount) session.getAttribute("user");
		Employee emp = employeeService.findOne(user.getUsername()).get();
		model.addAttribute("employee", emp);
		return "mypage/account";
	}
	
	@PostMapping
	public String passChange(@ModelAttribute PasswordChangeCheck pcc, Model model) {
		UserAccount user = (UserAccount) session.getAttribute("user");
		if (employeeService.checkPass(user.getUsername(), pcc.getPastPass()) && pcc.getNewPass().equals(pcc.getCheckPass())) {
			employeeService.changePass(user.getUsername(), pcc.getNewPass());
		}
		if (!(employeeService.checkPass(user.getUsername(), pcc.getPastPass()))) {
			model.addAttribute("errmsg1", "パスワードが間違っています。");
		}
		if (!(pcc.getNewPass().equals(pcc.getCheckPass()))) {
			model.addAttribute("errmsg2", "パスワードが異なっています。");
		}
		Employee emp = employeeService.findOne(user.getUsername()).get();
		model.addAttribute("employee", emp);
		return "mypage/account";
	}
}
