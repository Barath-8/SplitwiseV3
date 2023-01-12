package com.splitwise.model;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class GroupExpense {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(cascade = CascadeType.ALL)
	private User sender;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private User reciever;

	@ManyToOne(cascade = CascadeType.ALL)
	private Group group;

	@Column(precision = 12 , scale=2)
	private double amount;

	public int getId() {
		return id;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getReciever() {
		return reciever;
	}

	public void setReciever(User reciever) {
		this.reciever = reciever;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	@Override
	public String toString() {
		return "GroupExpense [id=" + id + ", sender=" + sender.getName() + ", reciever=" + reciever.getName() + ", group=" + group.getName()
				+ ", amount=" + amount + "]";
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(group, reciever, sender);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GroupExpense other = (GroupExpense) obj;
		return Objects.equals(group, other.group) && Objects.equals(reciever, other.reciever)
				&& Objects.equals(sender, other.sender);
	}
}
