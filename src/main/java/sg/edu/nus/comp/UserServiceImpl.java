package sg.edu.nus.comp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao dao;
	
	public User login(String username, String password) {
		User user = null;
		
		try {
			user = dao.find(username, password);
		} catch (EmptyResultDataAccessException e) {
			System.out.println("UserService login error: " + e.getMessage());
		}
		
		if(user != null && user.getId() > 0) {
			return user;
		} else {
			return null;
		}
	}

	public List<User> listUsers() {
		List<User> users = dao.findAll();
		return users;
	}

	public User viewUser(int id) {
		User user = dao.find(id);
		return user;
	}
	
	public User createOrEdit(User user) {
		
		User currentUser = dao.find(user != null ? user.getId() : 0);
		User newUser = null;
		
		if (currentUser != null && currentUser.getId() > 0) { //existing
			dao.edit(user, currentUser.getId());
			newUser = dao.find(currentUser.getId());
		} else { //new
			int intNewID = dao.add(user);
			if(intNewID > 0) {
				newUser = dao.find(intNewID);
			}
		}
		
		return newUser;
	}

	public int deleteUser(int id) {
		return dao.delete(id);
	}
	
}
