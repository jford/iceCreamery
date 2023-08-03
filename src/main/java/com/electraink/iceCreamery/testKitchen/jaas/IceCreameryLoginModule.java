package com.electraink.iceCreamery.testKitchen.jaas;

import com.electraink.iceCreamery.testKitchen.datastore.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

/*
   
   Create a file called jaas.config containing the following...
   
   IBookLogin
   {
       com.iBook.jaas.IBookLoginModule required application.name=iBook debug=true;
   };
   */

public class IceCreameryLoginModule implements LoginModule
{
	private CallbackHandler handler;
	private Subject subject;
	private UserPrincipal userPrincipal;
	private RolePrincipal rolePrincipal;
	private String login;
	private List<String> userGroups;
	
	@Override
	public void initialize(Subject subject,
			               CallbackHandler callbackHandler,
			               Map<String, ?> sharedState,
			               Map<String, ?> options)
	{
		handler = callbackHandler;
		this.subject = subject;
	}
	
	@Override
	public boolean login() throws LoginException
	{
		Callback[] callbacks = new Callback[2];
		callbacks[0] = new NameCallback("login");
		callbacks[1] = new PasswordCallback("password", true);
		
		try
		{
			handler.handle(callbacks);
			String name = ((NameCallback) callbacks[0]).getName();
			String password = String.valueOf(((PasswordCallback) callbacks[1]).getPassword());
			
			AdminParser adminParser = new AdminParser();
			Admin admin = adminParser.parse(DataMgr.adminDB); 
			Vector<String[]> adminUsers = admin.getUsers();
			Iterator<String[]> iUsers = adminUsers.iterator();
			while(iUsers.hasNext())
			{
				String[] user = (String[])iUsers.next();
				if(name != null && user[0].equals(name) && 
				   password != null && user[1].equals(password))
				{
					login = name;
					userGroups = new ArrayList<String>();
					userGroups.add("admin");
					return true;
				}
			}
			
			
			throw new LoginException("Authentication failed");
		}
		catch (IOException e)
		{
			throw new LoginException(e.getMessage());
		}
		catch(UnsupportedCallbackException e)
		{
			throw new LoginException(e.getMessage());
		}
	}
	
	@Override
	public boolean commit() throws LoginException
	{
		userPrincipal = new UserPrincipal(login);
		subject.getPrincipals().add(userPrincipal);
		
		if(userGroups != null && userGroups.size() > 0)
		{
			for(String groupName : userGroups)
			{
				rolePrincipal = new RolePrincipal(groupName);
				subject.getPrincipals().add(rolePrincipal);
			}
		}
		return true;
	}
	
	@Override
	public boolean abort() throws LoginException
	{
		return false;
	}
	
	@Override
	public boolean logout() throws LoginException
	{
		subject.getPrincipals().remove(userPrincipal);
		subject.getPrincipals().remove(rolePrincipal);
		return true;
		
	}
}