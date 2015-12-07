package jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String username;
	private String id;
	private List<Role> roles;

	public enum Role {
		DRIVER("Driver"), PASSENGER("Passenger");

		private String value;

		private Role(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}
	}

	public UserDTO(String username, String id) {
		super();
		this.username = username;
		this.id = id;
		this.roles = new ArrayList<>();
	}

	public UserDTO(String username, String userId, boolean isDriver, boolean isPassenger) {
		this(username, userId);
		if (isDriver) {
			roles.add(Role.DRIVER);
		}
		if (isPassenger) {
			roles.add(Role.PASSENGER);
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public void addRole(Role role) {
		this.roles.add(role);
	}

	public boolean isDriver() {
		return this.roles.contains(Role.DRIVER);
	}

	public boolean isPassenger() {
		return this.roles.contains(Role.PASSENGER);
	}
}
