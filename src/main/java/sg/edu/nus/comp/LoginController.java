package sg.edu.nus.comp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import sg.edu.nus.comp.log4shell.Log4Shell;

@Controller
public class LoginController {
	
	@Autowired
	UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView show(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("login", "login", new Login());
        mv.addObject("strDisplayMsg", "msg-none");
        return mv;
    }
	
	@RequestMapping(value = "/login2", method = RequestMethod.POST)
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response,
					  @ModelAttribute("login") Login login, BindingResult result) {
		
		ModelAndView mv = null;
		
		User user = userService.login(login.getUsername(), login.getPassword());

		if (user != null && user.isActive()) {
			System.out.println("Login OK.");
			user.setPassword(null);
			request.getSession().setAttribute("loginUser", user);
			mv = new ModelAndView("redirect:/posts");
		} else {
			System.out.println("Login FAILED.");

			/*
			 * Deliberately introducing Log4j Vulnerability
			 * Do not try this at home.
			 */
			String username = login.getUsername();
			Logger logger = LogManager.getLogger(Log4Shell.class);
			logger.error(username);
			
			mv = new ModelAndView("login");
			mv.addObject("strMsg", "Wrong Login!");
			mv.addObject("strDisplayMsg", "msg-show");
		}
		
		return mv;
	}

	/**
	 * Migitation Code for Spring4Shell
	 */
	/*
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// This code protects Spring Core from a "Remote Code Execution" attack (dubbed "Spring4Shell").
		// By applying this mitigation, you prevent the "Class Loader Manipulation" attack vector from firing.
		// For more details, see this post: https://www.lunasec.io/docs/blog/spring-rce-vulnerabilities/
		String[] blackList = { "class.*", "Class.*", "*.class.*", ".*Class.*" };
		binder.setDisallowedFields(blackList);
	}
	*/
}
