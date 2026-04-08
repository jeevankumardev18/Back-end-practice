package com.wrogn.task.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

		@Enumerated(EnumType.STRING)
		@Column(nullable = false)
		private Role role;


		@OneToMany(
				mappedBy = "user",
				cascade = CascadeType.ALL,
				fetch = FetchType.LAZY)
		@JsonIgnore
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

		public UserEntity(Long id, String email, String password,Role role) {
			super();
			this.id = id;
			this.email = email;
			this.password = password;
			this.role=role;
		}
	    
	    public UserEntity()
	    {
	    	
	    }

    public List<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderEntity> orders) {
        this.orders = orders;
    }

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
