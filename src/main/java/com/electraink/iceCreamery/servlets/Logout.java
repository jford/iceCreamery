package com.electraink.iceCreamery.servlets;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Logout extends HttpServlet {

  private static final long serialVersionUID = 1L;
  
  protected void doGet(HttpServletRequest request,
    HttpServletResponse response) 
      throws ServletException, IOException {
    
    // Invalidate current HTTP session.
    // Will call JAAS LoginModule logout() method
    request.getSession().invalidate();
    
    // Redirect the user to the secure web page.
    // Since the user is now logged out the
    // authentication form will be shown
    response.sendRedirect(request.getContextPath() 
      + "/forms/recipeSelect.jsp");
    
  }

}
