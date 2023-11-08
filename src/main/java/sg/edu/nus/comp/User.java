package sg.edu.nus.comp;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class User implements PostOwner {
	
	public enum UserRole {
		admin,
		editor
	}
	
	private int id;
	private String name;
	@Email(message = "Not a valid email.") @NotEmpty(message = "Please enter your email!")
	private String email; //TODO: Additional Email validation using RegEx Pattern
	@Size(min = 6, max = 15, message = "Your password must between 6 and 15 characters!")
	private String password;
	private UserRole role;
	private boolean active;
	
	public User() {
		super();
	}

	public User(int id, String name, String email, String password, boolean active, UserRole role) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		//Set password to null if empty (Bypass validation when no change)
		this.password = (password != null && password.isEmpty()) ? null: password;
		this.role = role;
		this.active = active;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		//Set password to null if empty (Bypass validation when no change)
		this.password = (password != null && password.isEmpty()) ? null: password;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", role=" + role
				+ ", active=" + active + "]";
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
