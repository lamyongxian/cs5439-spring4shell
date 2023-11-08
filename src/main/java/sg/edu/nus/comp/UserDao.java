package sg.edu.nus.comp;

import java.util.List;

public interface UserDao {
    public int add(User user);
    public int edit(User user, int id);
    public int delete(int id);
    public User find(int id);
    public User find(String name, String password);
    public List<User> findAll();
}
