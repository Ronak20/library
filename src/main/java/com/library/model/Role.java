package com.library.model;

public enum Role {
	ADMIN("Administrator"),STUDENT("Student");
	
	private String name;

	private Role(String name) {
		this.name = name;
	}

	public static Role parse(String roleName) {
		for (Role role : Role.values()) {
			//System.out.println(role.toString());
			if (role.toString().equals(roleName)) {
				//System.out.println("role : "+role.toString());
				return role;
			}
		}
		
		return null;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
