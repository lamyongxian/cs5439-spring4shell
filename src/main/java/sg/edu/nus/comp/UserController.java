package sg.edu.nus.comp;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.access.*;

import sg.edu.nus.comp.User.UserRole;

@Controller
public class UserController {
	
	@Autowired
	UserService userService;
	
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView showUsers(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        ModelAndView mv = new ModelAndView("users");
        
        List<User> liUsers = null; 
        
        /***************** ACCESS CHECK ****************/
        User loginUser = (User) session.getAttribute("loginUser");
        
        if (loginUser != null) {
	        if(loginUser.getRole() == UserRole.admin) { 
	        	liUsers = userService.listUsers();
	        } else {
	        	throw new AccessDeniedException("403 returned");
	        }
        } else {
        	throw new AccessDeniedException("403 returned");
        }
        /***********************************************/

        mv.addObject("users", liUsers);
        return mv;
    }
    
    @RequestMapping(value = "/new_user", method = RequestMethod.GET)
	public ModelAndView newUser(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
		ModelAndView mv = null;
		try {
			
			/***************** ACCESS CHECK ****************/
			User loginUser = (User) session.getAttribute("loginUser");
			
			if (loginUser != null) {
				if(loginUser.getRole() == UserRole.admin) { //login user is admin
					System.out.println("newUser: Admin access.");
					//Do nothing, skip further checks
				} else {
					throw new AccessDeniedException("403 returned");
				}
			} else {
				throw new AccessDeniedException("403 returned");
			}
			/***********************************************/
			
			User newUser = new User();	
			//post.setRole(UserRoles.none);
			mv = new ModelAndView("edit_user");
			mv.addObject("user", newUser);
			
			mv.addObject("roles", User.UserRole.values());

			mv.addObject("strMsg", "Click 'Save' to create new user.");
			mv.addObject("strDisplayMsg", "show");
			
		} catch (NumberFormatException ex) {
			System.out.println("UserController edit error: " + ex.getMessage());
		}
		return mv;
	}
	
	@RequestMapping(value = "/edit_user", method = RequestMethod.GET)
	public ModelAndView editUser(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
		ModelAndView mv = null;
		try {

			int intPost = Integer.parseInt(request.getParameter("id"));

			/***************** ACCESS CHECK ****************/
			User loginUser = (User) session.getAttribute("loginUser");
			
			if (loginUser != null) {
				if(loginUser.getRole() == UserRole.admin) { //login user is admin
					System.out.println("editUser: Admin access.");
					//Do nothing, skip further checks
				} else {
					throw new AccessDeniedException("403 returned");
				}
			}else {
				throw new AccessDeniedException("403 returned");
			}
			/***********************************************/
				
			User editUser = userService.viewUser(intPost);
			mv = new ModelAndView("edit_user");
			mv.addObject("user", editUser);
			
			mv.addObject("roles", User.UserRole.values());
			
			mv.addObject("strDisplayMsg", "none");
			System.out.println("Edit User: " + editUser.toString());
			
		} catch (NumberFormatException ex) {
			System.out.println("UserController edit error: " + ex.getMessage());
		}
		return mv;
	}
	
	@RequestMapping(value = "/save_user", method = RequestMethod.POST)
	public ModelAndView saveUser(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@ModelAttribute("user") @Valid User savedUser, BindingResult result) {
				
		ModelAndView mv = null;
		
		/***************** ACCESS CHECK ****************/
		User loginUser = (User) session.getAttribute("loginUser");
		
		if (loginUser != null) {
			if(loginUser.getRole() == UserRole.admin) { //login user is admin
				System.out.println("saveUser: Admin access.");
				//Do nothing, skip further checks
			} else if(loginUser.getId() == savedUser.getId()) { //login user editing self
				System.out.println("saveUser: Edit self.");
				savedUser.setRole(null); //If self editing, prevent changing of role no matter what
			} else {
				throw new AccessDeniedException("403 returned");
			}
		} else {
			throw new AccessDeniedException("403 returned");
		}
		/***********************************************/
		
		mv = new ModelAndView("edit_user");
		mv.addObject("roles", User.UserRole.values());
		
		if (result.hasErrors()) {
			mv.addObject("strMsg", "Please Check the fields for error.");
			mv.addObject("strDisplayMsg", "show");
		} else {
			
			mv.addObject("strMsg", "Saved!");
			mv.addObject("strDisplayMsg", "show");
			
			System.out.println("User savedUser: " + savedUser.toString());
			
			if (savedUser.getRole() == null) { //No change to role if null
				//Do nothing, skip to createOrEdit
			} else if (loginUser.getRole() == UserRole.admin) { //If role is changing, check actor is admin
				//Do nothing, skip to createOrEdit
			} else {
				throw new AccessDeniedException("403 returned");
			} 
			//TODO: Encrypt valid status for dropdownlist 'value' instead (Enhance Security)
			
			savedUser = userService.createOrEdit(savedUser);
			
			mv.addObject("user", savedUser);
		}
		
		return mv;
		
	}
	
	@RequestMapping(value = "/delete_user", method = RequestMethod.GET)
	public ModelAndView deleteUser(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
				
		ModelAndView mv = null;

		int intUser = Integer.parseInt(request.getParameter("id"));

		/***************** ACCESS CHECK ****************/
		User loginUser = (User) session.getAttribute("loginUser");
		
		if (loginUser != null) {
			if(loginUser.getRole() == UserRole.admin) { //login user is admin
				System.out.println("deleteUser: Admin access.");
				//Do nothing, skip further checks
			} else {
				throw new AccessDeniedException("403 returned");
			}
		} else {
			throw new AccessDeniedException("403 returned");
		}
		/***********************************************/
		
		int intAffected = userService.deleteUser(intUser);
		
		if (intAffected > 0) { //If a row deleted successfully
			mv = new ModelAndView("redirect:/users");
		} else {
			mv = new ModelAndView("edit_user");
			mv.addObject("strMsg", "Error deleting!");
			mv.addObject("strDisplayMsg", "show");
		}
		
		return mv;
		
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

		session.setAttribute("loginUser", null);

		ModelAndView mv = new ModelAndView("redirect:/login");
		return mv;
	}

}
