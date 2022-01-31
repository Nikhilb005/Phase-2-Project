package com.admin;

import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.portal.*;
import com.portal.Class;

public class AdminControllerServlet  extends HttpServlet {
	
		private static final long serialVersionUID = 1L;

		private DBRetrieve dbRetrieve;

		@Resource(name = "administrative-portal")
		private DataSource datasource;

		@Override
		public void init() throws ServletException {
			super.init();
			
		try {
				dbRetrieve = new DBRetrieve(datasource);
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}

 		public AdminControllerServlet() {
			super();
		}

		protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			try {
				String command = request.getParameter("command");
				if (command == null) {
					command = "CLASSES";
				}			
				else if (!getCookies(request, response) && (!command.equals("LOGIN"))) {
					response.sendRedirect("/Administrative-Portal/login.jsp");
				}
				else {
					switch (command) {
					case "STUDENTS":
						studentsList(request, response);
						break;
					case "TEACHERS":
						teachersList(request, response);
						break;
					case "SUBJECTS":
						subjectList(request, response);
						break;
					case "CLASSES":
						classestList(request, response);
						break;
					case "ST_LIST":
						classStudentsList(request, response);
						break;
					case "LOGIN":
						login(request, response);
						break;
					default:
						classestList(request, response);
					}
				}
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}

		private void studentsList(HttpServletRequest request, HttpServletResponse response) throws Exception {
			List<Student> students = dbRetrieve.getStudents();
			request.setAttribute("STUDENT_LIST", students);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/StudentList.jsp");
			dispatcher.forward(request, response);
		}

		private void teachersList(HttpServletRequest request, HttpServletResponse response) throws Exception {
			List<Teacher> teachers = dbRetrieve.getTeachers();
			request.setAttribute("TEACHERS_LIST", teachers);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/teacherList.jsp");
			dispatcher.forward(request, response);
		}

		private void subjectList(HttpServletRequest request, HttpServletResponse response) throws Exception {
			List<Subject> subjects = dbRetrieve.getSubjects();
			request.setAttribute("SUBJECTS_LIST", subjects);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/subjectList.jsp");
			dispatcher.forward(request, response);
		}

		private void classestList(HttpServletRequest request, HttpServletResponse response) throws Exception {
			List<Class> classes = dbRetrieve.getClasses();
			request.setAttribute("CLASSES_LIST", classes);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/classList.jsp");
			dispatcher.forward(request, response);
		}

		private void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
			String username = request.getParameter("username");
			String password = request.getParameter("password");

			if (username.toLowerCase().equals("admin") && password.toLowerCase().equals("admin")) {
				Cookie cookie = new Cookie(username, password);
				cookie.setMaxAge(86400); 
				response.addCookie(cookie);
				classestList(request, response);
			}
			else {
				RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
				dispatcher.forward(request, response);
			}
		}

		private void classStudentsList(HttpServletRequest request, HttpServletResponse response) throws Exception {

			int classId = Integer.parseInt(request.getParameter("classId"));
			String section = request.getParameter("section");
			String subject = request.getParameter("subject");

			List<Student> students = dbRetrieve.loadClassStudents(classId);
			request.setAttribute("STUDENTS_LIST", students);
			request.setAttribute("SECTION", section);
			request.setAttribute("SUBJECT", subject);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/classStudent.jsp");
			dispatcher.forward(request, response);
		}

		private boolean getCookies(HttpServletRequest request, HttpServletResponse response) throws Exception {
			boolean check = false;
			Cookie[] cookies = request.getCookies();
			for (Cookie cookie : cookies) {				 
				if (cookie.getName().equals("admin") && cookie.getValue().equals("admin")) {
					check = true;
					break;
				} 
			}
			return check;
		}
	}