package com.wrogn.task.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
public class UserEntity 
{
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String email;

	    private String password;

		@OneToMany(mappedBy = "user")
		private List<OrderEntity> orders=new ArrayList<>();

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

		public UserEntity(Long id, String email, String password) {
			super();
			this.id = id;
			this.email = email;
			this.password = password;
		}
	    
	    public UserEntity()
	    {
	    	
	    }

}
