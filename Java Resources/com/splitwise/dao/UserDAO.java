package com.splitwise.dao;

import java.util.Set;

import com.splitwise.model.User;
import com.splitwise.model.UsersGroups;

public interface UserDAO {
	
	boolean signUp(User user);
	User getUser(String username , String password);
	public User searchByEmail(User user);
	Set<UsersGroups> getGroups(User current);
	
}
