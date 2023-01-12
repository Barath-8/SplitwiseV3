package com.splitwise.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.splitwise.model.Expense;
import com.splitwise.model.Group;
import com.splitwise.model.GroupExpense;
import com.splitwise.model.User;
import com.splitwise.model.UserExpense;
import com.splitwise.model.UsersGroups;


@Repository
public class GroupDaoImpl implements GroupDAO {

	@Autowired
	HibernateTemplate hibernateTemplate;

	@Override
	@Transactional
	public void addGroup(Group group, User user) {

		UsersGroups groupLink = new UsersGroups();

		groupLink.setGroup(group);
		groupLink.setUser(user);
		groupLink.setBalance(0);
		
		group.getUser_list().add(groupLink);
		user.getGroup_list().add(groupLink);
		
		hibernateTemplate.saveOrUpdate(groupLink);
	}

	@Override
	@Transactional
	public void addMember(Group group, User user) {
		
		Set<UsersGroups> groupMember = group.getUser_list();

		for (UsersGroups member : groupMember) {
			User user1 = member.getUser();
			GroupExpense expense = new GroupExpense();
			expense.setSender(user);
			expense.setReciever(user1);
			expense.setGroup(group);
			expense.setAmount(0);

			group.getGroupExpense().add(expense);

			expense = new GroupExpense();
			expense.setSender(user1);
			expense.setReciever(user);
			expense.setGroup(group);
			expense.setAmount(0);

			group.getGroupExpense().add(expense);

		}
		addGroup(group, user);
	}

	@Override
	@Transactional
	public void addExpense(String description, double amount, UsersGroups groupLink) {

		UserExpense userExpense = new UserExpense();

		userExpense.setAmount(amount);
		userExpense.setDescription(description);

		hibernateTemplate.save(userExpense);

		Expense expenseLog = new Expense();

		expenseLog.setAmount(amount);

		hibernateTemplate.save(expenseLog);

		groupLink.getUserExpense().add(userExpense);

		Group group = groupLink.getGroup();
		User user = groupLink.getUser();
		user.getExpense_list().add(expenseLog);

		List<User> member = new ArrayList<>();

		for (UsersGroups mem : group.getUser_list())
			member.add(mem.getUser());

		double split = amount / member.size();

		List<GroupExpense> expense = group.getGroupExpense();

		System.out.println(expense);

		System.out.println(expense);

		for (GroupExpense exp : expense)
			if (exp.getSender().equals(user)) {
				double borrowed = exp.getAmount();
				if (borrowed >= split) {
					exp.setAmount(borrowed - split);

				} else {
					double remain = split - borrowed;
					exp.setAmount(0);
					for (GroupExpense find : expense)
						if (find.getReciever().equals(exp.getSender()) && find.getSender().equals(exp.getReciever())) {
							find.setAmount(find.getAmount() + remain);

							UsersGroups reciever = find.getReciever().getGroup_list().stream()
									.filter(e -> e.getGroup().equals(group)).findFirst().get();
							reciever.setBalance(reciever.getBalance() + remain);
							break;
						}
				}
			}

		normalize(expense, member);

		hibernateTemplate.saveOrUpdate(groupLink);

	}

	private void normalize(List<GroupExpense> expenses, List<User> member) {

		for (User user : member)
			for (GroupExpense expense : expenses) {
				int id = -1;
				double min = Double.MAX_VALUE;
				boolean isBalance = false;

				if (expense.getSender().equals(user) && expense.getAmount() > 0)
					for (int idx = 0; idx < expenses.size(); idx++) {
						GroupExpense exp = expenses.get(idx);

						double dis = expense.getAmount() - exp.getAmount();

						if (exp.getReciever().equals(user) && dis!=expense.getAmount())
							if (dis < min && dis >= 0) {
								isBalance = false;
								min = dis;
								id = idx;
								if (dis == 0)
									break;
							} else if (dis < 0 && -1*dis < min) {
								isBalance = true;
								min = -1*dis;
								id = idx;
							}
					}

				if (id != -1 ) {
					User swap = expenses.get(id).getSender();

					for (GroupExpense exp : expenses)
						if (exp.getSender().equals(swap) && exp.getReciever().equals(expense.getReciever())) {
							if (!isBalance)
								exp.setAmount(exp.getAmount() + expenses.get(id).getAmount());
							else
								exp.setAmount(exp.getAmount()+expense.getAmount());
							break;
						}
					
					System.out.println(expenses.get(id));
					
					if (!isBalance) {
						expense.setAmount(expense.getAmount() - expenses.get(id).getAmount());
						expenses.get(id).setAmount(0);
					} else {
						expenses.get(id).setAmount(expenses.get(id).getAmount()-expense.getAmount());
						expense.setAmount(0);
					}

				}

			}
	}


}
