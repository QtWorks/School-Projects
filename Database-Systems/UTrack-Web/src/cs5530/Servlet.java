package cs5530;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.*;
import service.*;
import exceptions.*;
import cs5530.*;

@WebServlet("/servlet")
public class Servlet extends HttpServlet {
	
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(request.getParameter("editPOI") != null){
			UserRecord user = (UserRecord) session.getAttribute("user");
			if(user.isAdmin()){
				response.sendRedirect("editPOI.jsp");
			}
		}
	}

}
