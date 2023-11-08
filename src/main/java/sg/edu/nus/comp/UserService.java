package sg.edu.nus.comp;

import java.util.List;

public interface UserService {
	
	public User login(String username, String password);
	public User viewUser(int id);
	public List<User> listUsers();
	public User createOrEdit(User user);
	public int deleteUser(int id);
}
