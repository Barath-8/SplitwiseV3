package com.splitwise.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
     
     
@Entity
@Table(name = "groups")
public class Group {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String name;
	private String type;
	
	@OneToMany(cascade = CascadeType.MERGE,mappedBy="group")
	private Set<UsersGroups> user_list = new HashSet<>();
	
	@OneToMany(mappedBy = "group",cascade = CascadeType.ALL)
	private List<GroupExpense> groupExpense = new ArrayList<>();
	
	public int getId() {
		return id;
	}
	public List<GroupExpense> getGroupExpense() {
		return groupExpense;
	}
	public void setGroupExpense(List<GroupExpense> groupExpense) {
		this.groupExpense = groupExpense;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public Set<UsersGroups> getUser_list() {
		return user_list;
	}
	
	public void setUser_list(Set<UsersGroups> user_list) {
		this.user_list = user_list;
	}
	

	@Override
	public int hashCode() {
		return Objects.hash(id, name, type);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Group other = (Group) obj;
		return id == other.id && Objects.equals(name, other.name) && Objects.equals(type, other.type);
	}

	
}
