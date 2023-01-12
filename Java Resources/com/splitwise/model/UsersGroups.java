package com.splitwise.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "user_group")
public class UsersGroups {
	


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(cascade = CascadeType.ALL,targetEntity = User.class)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(cascade = CascadeType.ALL,targetEntity = Group.class,fetch = FetchType.EAGER)
	@JoinColumn(name = "group_id")
	private Group group;
	
	@OneToMany(cascade = CascadeType.ALL,targetEntity = UserExpense.class,fetch = FetchType.EAGER)
	@JoinColumn(name = "userExpenseId" )
	private Set<UserExpense> userExpense = new HashSet<>();
	
	@Transient
	private double balance;
	
	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}


	public Set<UserExpense> getUserExpense() {
		return userExpense;
	}

	public void setUserExpense(Set<UserExpense> userExpense) {
		this.userExpense = userExpense;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(group, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsersGroups other = (UsersGroups) obj;
		return Objects.equals(group, other.group) && Objects.equals(user, other.user);
	}

}
