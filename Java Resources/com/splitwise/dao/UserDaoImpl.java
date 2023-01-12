package com.splitwise.dao;

import java.util.List;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.splitwise.model.GroupExpense;
import com.splitwise.model.User;
import com.splitwise.model.UsersGroups;

@Repository
public class UserDaoImpl implements UserDAO{
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Transactional
	@Override
	public boolean signUp(User user){
		
		try {
			hibernateTemplate.save(user);
			return true;
		}catch(DuplicateKeyException dup) {
			return false;
		}
	}
	
	@Override
	@Transactional
	public User getUser(String username, String password) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
		criteria.add(Restrictions.eq("email", username));
		criteria.add(Restrictions.eq("password", password));
		
		List<?> list = hibernateTemplate.findByCriteria(criteria);
		
		if (list.isEmpty())
			return null;
		
		User current=(User) list.get(0);
		
		Set<UsersGroups> groupLink = current.getGroup_list();
		
		
		for(UsersGroups group : groupLink) {
			hibernateTemplate.initialize(group.getGroup().getUser_list());
			hibernateTemplate.initialize(group.getGroup().getGroupExpense());
		}
		
		hibernateTemplate.initialize(current.getExpense_list());
		
		return current;
	}
	
	@Transactional
	@Override
	public User searchByEmail(User user) {
		
		
		DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
		criteria.add(Restrictions.eq("email", user.getEmail()));
		
		List<?> list = hibernateTemplate.findByCriteria(criteria);
		
		if (list.isEmpty())
			return null;
		
		User member=(User) list.get(0);
		
		return member;
	}
	
	@Override
	@Transactional
	public Set<UsersGroups> getGroups(User current) {
		
		Set<UsersGroups> groupLink = current.getGroup_list();
		
		for(UsersGroups grpLink : groupLink ) {
			double amount=0;
			
			for(GroupExpense exp : grpLink.getGroup().getGroupExpense()) {
				if(exp.getSender().equals(current))
					amount-=exp.getAmount();
				else if(exp.getReciever().equals(current))
					amount+=exp.getAmount();
			}
			grpLink.setBalance(amount);
			
		}
		
		return groupLink;
		
	}
	
	

}
