package com.electraink.iceCreamery.testKitchen.datastore;

import java.util.*;

public class Admin
{
	Vector<String[]> users = new Vector<String[]>();
	
	public Admin()
	{
	}
	
	public void addUser(String[] user)
	{
		// user[0] = id, user[1] = pwd
		users.add(user);
	}
	
	public Vector<String[]> getUsers()
	{
		return users;
	}

}
