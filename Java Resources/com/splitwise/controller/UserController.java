package com.splitwise.controller;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.splitwise.dao.GroupDAO;
import com.splitwise.dao.UserDAO;
import com.splitwise.model.Group;
import com.splitwise.model.User;
import com.splitwise.model.UsersGroups;

@Controller
public class UserController {
	
	@Autowired
	UserDAO userDao;
	
	@Autowired
	GroupDAO groupDao;
	
	User current;
	Group group;
	Set<UsersGroups> groupLink;
	UsersGroups viewing;
	
	@GetMapping("/")
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("isSignUp",new Boolean(false));
		mv.setViewName("index");
		return mv;
	}
	
	
	@PostMapping("/login")
	public ModelAndView login(@RequestParam String username, @RequestParam String password) {

		ModelAndView mv = new ModelAndView();


		current = userDao.getUser(username,password);
		
		if (current==null) {
			mv.setViewName("index");
			return mv;
		}
		
		mv.setViewName("login");

		mv.addObject("user", current);
		
		groupLink= userDao.getGroups(current);
		
		mv.addObject("group_list",groupLink);
		mv.addObject("group",new Group());
		
		
		System.out.println(groupLink);
		
		return mv;
	}
	
	@PostMapping("/add-Group")
	public ModelAndView addGroup(Group group) {
		ModelAndView mv = new ModelAndView();
		groupDao.addGroup(group, current);
		mv.setViewName("login");
		mv.addObject("user", current);
		groupLink= userDao.getGroups(current);
		mv.addObject("group_list",current.getGroup_list());
		mv.addObject("group",new Group());
		return mv;
	}
	
	@RequestMapping("/viewGroup")
	public ModelAndView viewGroup(@RequestParam int groupLinkId) {
		
		ModelAndView mv = new ModelAndView();
		
		
		for(UsersGroups link : groupLink) {
			if(link.getId()==groupLinkId) {
				viewing = link;
				group = link.getGroup();
				break;
			}
		}
		
		mv.setViewName("displayGroup");
		
		mv.addObject("group",group);
		mv.addObject("members",group.getUser_list().stream().filter(e-> !e.getUser().equals(current)).collect(Collectors.toList()));
		mv.addObject("user",current);
		mv.addObject("newMember",new User());
		return mv;
	}
	
	@PostMapping("/add-member")
	public ModelAndView addMember(User newMember) {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("displayGroup");
		
		User member = userDao.searchByEmail(newMember);
		
		if(member==null) {
			mv.addObject("isAdded",new Boolean(false));
			mv.addObject("newMember",new User());
			return mv;
		}
		
		groupDao.addMember(group, member);
		mv.addObject("group",group);
		mv.addObject("members",group.getUser_list().stream().filter(e-> !e.getUser().equals(current)).collect(Collectors.toList()));
		mv.addObject("user",current);
		mv.addObject("newMember",new User());
		return mv;
	}
	
	@PostMapping("/add-expense")
	public ModelAndView addExpense(@RequestParam String description,@RequestParam double amount) {
		ModelAndView mv = new ModelAndView();
		
		groupDao.addExpense(description,amount, viewing);
		mv.addObject("group",group);
		mv.addObject("members",group.getUser_list());
		mv.addObject("members",group.getUser_list().stream().filter(e-> !e.getUser().equals(current)).collect(Collectors.toList()));
		
		mv.addObject("user",current);
		mv.addObject("newMember",new User());
		
		mv.setViewName("displayGroup");
		
		return mv;
	}
	
	@RequestMapping("/signUp")
	public String signUp(User user) {
		return "signUp";
	}
	
	@PostMapping("/add-User")
	public ModelAndView addUser(User user) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("isSignUp",new Boolean(true));
		
		if(userDao.signUp(user))
			mv.addObject("isAdded",new Boolean(true));
		else
			mv.addObject("isAdded",new Boolean(false));
		
		mv.setViewName("index");
		
		return mv;
	}
	
	
	
	
}
