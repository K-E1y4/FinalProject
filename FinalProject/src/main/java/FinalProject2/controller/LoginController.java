package FinalProject2.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

	@Autowired
	HttpSession session;
	
	@RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
	public String index(Model model) {
		if(session.getAttribute("user") != null) {
			return "redirect:/mypage";
		}
		model.addAttribute("iserror",false);
	    return "login";
	}
	
	@RequestMapping(value = "/login-error", method = RequestMethod.GET)
	public String loginError(Model model) {
		 model.addAttribute("iserror",true);
		 return "login";
	}
	
}