package com.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(name="administrative-portal")
	private DataSource dataSource;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");	
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {			
		myConn = dataSource.getConnection();
		String sql = "select * from students";
		myStmt = (Statement) myConn.createStatement();	
		myRs = (myStmt).executeQuery(sql);
		
		while(myRs.next()) {
			String fname = myRs.getString("fname");
			out.println(fname);		
		}
	}
		catch(Exception e) {
			e.printStackTrace();
		}
	
	}

}
