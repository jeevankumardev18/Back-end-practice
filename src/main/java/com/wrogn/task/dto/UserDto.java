package com.wrogn.task.dto;

public class UserDto 
{
	
	 public UserDto(Long id, String email, String password) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
	}
	 
	 public UserDto()
	 {
		 
	 }

	 private Long id;
	 
	 private String email;
	 
	 private String password;

	 public Long getId() {
		 return id;
	 }

	 public void setId(Long id) {
		 this.id = id;
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
		 this.password = password;
	 }

	 @Override
	 public String toString() {
		return "UserDto [id=" + id + ", email=" + email + ", password=" + password + "]";
	 }
	 
}
