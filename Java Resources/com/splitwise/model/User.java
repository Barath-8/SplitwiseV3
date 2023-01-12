package com.splitwise.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;

	@Column(name = "Email", nullable = false, unique = true)
	private String email;

	@Column(name = "phone", nullable = false, unique = true)
	private String phone;
	private String password;

	@OneToMany(cascade = CascadeType.MERGE, mappedBy = "user", fetch = FetchType.EAGER)
	private Set<UsersGroups> group_list = new HashSet<>();
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="userId")
	private Set<Expense> expense_list = new HashSet<>();
	
	public Set<UsersGroups> getGroup_list() {
		return group_list;
	}

	public void setGroup_list(Set<UsersGroups> group_list) {
		this.group_list = group_list;
	}

	public Set<Expense> getExpense_list() {
		return expense_list;
	}

	public void setExpense_list(Set<Expense> expense_list) {
		this.expense_list = expense_list;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, phone);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(email, other.email);
	}


}
