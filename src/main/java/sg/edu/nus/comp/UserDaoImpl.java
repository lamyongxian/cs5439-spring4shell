package sg.edu.nus.comp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("userDao")
public class UserDaoImpl implements UserDao {
	
	@Autowired
	private JdbcTemplate jdbc;

	public int add(final User user) {
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		jdbc.update(new PreparedStatementCreator() {

			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement s = con.prepareStatement("INSERT INTO user (name, email, password, role, active) VALUES (?,?,SHA2(IFNULL(?,''),256),?,?);",
						Statement.RETURN_GENERATED_KEYS);
				s.setString(1, user.getName());
				s.setString(2, user.getEmail());
				s.setString(3, user.getPassword());
				s.setString(4, user.getRole().name());
				s.setBoolean(5, user.isActive());
				return s;
			}
			
		}, keyHolder);
		int pkUser = keyHolder.getKey().intValue();
		
		return pkUser;
	}

	public int edit(User user, int id) {
		String strPassword = user.getPassword();
		return jdbc.update("UPDATE user SET name = ?, email = ?, password = IF(? IS NULL or TRIM(?) = '', password, SHA2(?,256)), active = IFNULL(?, active) , role = IFNULL(?, role) WHERE id = ?;",
					user.getName(), user.getEmail(), strPassword, strPassword, strPassword, user.isActive(), user.getRole().name(), id);
	}

	public int delete(int id) {
		return jdbc.update("DELETE FROM user WHERE id = ?;", id);
	}

	public User find(int id) {
		List<User> users = jdbc.query("SELECT id,name,email,role,active FROM user WHERE id = ? LIMIT 1;", new Object[] {id}, new BeanPropertyRowMapper(User.class)); //Workaround EmptyResultDataAccessException
		
		if (users != null && !users.isEmpty()) {
			return users.get(0);
		}
		return null;
	}

	public User find(String name, String password) throws EmptyResultDataAccessException {
		User user = (User) jdbc.queryForObject("SELECT id,name,email,role,active FROM user WHERE name= ? AND password= SHA2(?,256);",
											   new Object[] {name,password}, new BeanPropertyRowMapper(User.class));
		return user;
	}

	public List<User> findAll() {
		List<User> users = jdbc.query("SELECT id,name,email,role,active FROM user;", new BeanPropertyRowMapper(User.class));
		return users;
	}

}
