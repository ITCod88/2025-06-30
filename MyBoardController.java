package com.my.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.my.dao.MyBoardDao;
import com.my.dto.MyBoardDto;

@WebServlet("/myboard")
public class MyBoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String command = request.getParameter("command");
		System.out.println("command: " + command);
		
		MyBoardDao dao = new MyBoardDao();
		
		if(command.equals("list")) {
			List<MyBoardDto> res = dao.selectAll();
			
			request.setAttribute("list", res);
			RequestDispatcher dis = request.getRequestDispatcher("mylist.jsp");
			dis.forward(request, response);
			
		}else if(command.equals("detail")) {
			int myno = Integer.parseInt(request.getParameter("myno"));
			MyBoardDto res = dao.selectOne(myno);
			
			request.setAttribute("dto", res);
			RequestDispatcher dis = request.getRequestDispatcher("mydetail.jsp");
			dis.forward(request, response);
			
		}else if(command.equals("writeform")) {
			response.sendRedirect("mywrite.jsp");
			
		}else if(command.equals("write")) {
			String myname = request.getParameter("myname");
			String mytitle = request.getParameter("mytitle");
			String mycontent = request.getParameter("mycontent");
			
			MyBoardDto dto = new MyBoardDto();
			dto.setMyname(myname);
			dto.setMytitle(mytitle);
			dto.setMycontent(mycontent);
			
			int res = dao.insert(dto);
			
			if(res>0) {
				System.out.println("글 작성 성공");
				response.sendRedirect("index.html");
			}else {
				System.out.println("글 작성 실패");
				response.sendRedirect("index.html");
			}
		
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
