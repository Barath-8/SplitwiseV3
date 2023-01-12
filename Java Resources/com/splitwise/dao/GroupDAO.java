package com.splitwise.dao;

import com.splitwise.model.Group;
import com.splitwise.model.User;
import com.splitwise.model.UsersGroups;

public interface GroupDAO {
	void addGroup(Group group,User user);
	void addMember(Group group,User user);
	void addExpense(String description,double amount,UsersGroups groupLink);
}
