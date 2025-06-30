package com.test.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.test.dao.BikeDao;
import com.test.dto.BikeDto;

@WebServlet("/bike.do")
public class BikeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String command = request.getParameter("command");
		System.out.println("command: " + command);
		
		if(command.equals("first")) {
			response.sendRedirect("bike01.jsp");
			
		}else if(command.equals("second")) {
			response.sendRedirect("bike02.jsp");
			
		}else if(command.equals("second_db")) {
			new BikeDao().deleteAll(); //인서트를 두 번 실행했을 때 에러가 발생하는 것을 방지하기 위한 메소드, db에서 delete를 따로 실행할 필요X
			
			//한 번 실행하고 다시 실행했을 때는 PK의 존재로 인해 무결성 제약이 걸려 에러가 발생한다.
			//해결하려면 한번 데이터를 지워주고 다시 시작하면 된다.
			//해결법은 위의 deleteAll() 메소드 작성!!
			
			
			String obj = request.getParameter("obj");
			//System.out.println(obj);
			
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(obj);
			
			//DATA의 첫번째 방에 저장된 json데이터 new_addr 값 저장하기
			//String str = element.getAsJsonObject().get("DATA").getAsJsonArray().get(0).getAsJsonObject().get("new_addr").getAsString();
			//System.out.println(str);
			
			//DESCRIPTION이 가지는 data의 ADDR_GU의 값을 저장하기
			//str = element.getAsJsonObject().get("DESCRIPTION").getAsJsonObject().get("ADDR_GU").getAsString();
			//System.out.println(str);
	
			List<BikeDto> list = new ArrayList<>();
			for(int i=0; i<element.getAsJsonObject().get("DATA").getAsJsonArray().size(); i++) {
				JsonObject tmp = element.getAsJsonObject().get("DATA").getAsJsonArray().get(i).getAsJsonObject();
				//System.out.println(tmp.get("new_addr").getAsString());	
				//get으로 가져오는 데이터는 jsonelement 형태로 가져온다.
			
				String addr_gu = tmp.get("addr_gu").getAsString();
				int content_id = tmp.get("content_id").getAsInt();
				String content_nm = tmp.get("content_nm").getAsString();
				String new_addr = tmp.get("new_addr").getAsString();
				int cradle_count = tmp.get("cradle_count").getAsInt();
				double longitude = tmp.get("longitude").getAsDouble();
				double latitude = tmp.get("latitude").getAsDouble();
				
				BikeDto dto = new BikeDto();
				dto.setAddr_gu(addr_gu);
				dto.setContent_id(content_id);
				dto.setContent_nm(content_nm);
				dto.setNew_addr(new_addr);
				dto.setCradle_count(cradle_count);
				dto.setLongitude(longitude);
				dto.setLatitude(latitude);
				
				list.add(dto);
				
			}
			
			int res = new BikeDao().insert(list);
			
			if(res == list.size()) {
				System.out.println("insert 종료\n");
			}else {
				System.out.println("insert 실패\n");
			}
			
			//비동기통신으로 요청이 온 것이기때문에 ajax로 응답을 해야한다.
			//응답되는 데이터가 응답페이지(ajax가 없을 시)가 아닌 ajax로 들어온다.
			PrintWriter out = response.getWriter();
			out.println(res);
			//res 대신 true or false를 적게 되면 성공, 실패를 좀 더 쉽게 표현할 수 있다.
		
		}else if(command.equals("first_db")) {
			String[] bikeList = request.getParameterValues("bike");
			System.out.println("bikeList의 크기: " + bikeList.length);
			//parameter는 너무 많은 값을 다 넘길 수 없다. 최대 999까지
			//그래서 999이상일 경우 command=second_db를 실행하는 것이 낫다.(String값으로 변환해서 진행)
			
			List<BikeDto> list = new ArrayList<>();
			for(int i=0; i<bikeList.length; i++) {
				//특정 문자를 기준으로 나누기
				//강남구/2301/ 현대고등학교 건너편/서울특별시 강남구 압구정로 134/10/127.02179/37.524071
				String[] tmp = bikeList[i].split("/");
				
				//쪼갠것을 적절한 위치에 나누어 담는 과정
				BikeDto dto = new BikeDto();
				dto.setAddr_gu(tmp[0]);
				dto.setContent_id(Integer.parseInt(tmp[1]));
				dto.setContent_nm(tmp[2]);
				dto.setNew_addr(tmp[3]);
				dto.setCradle_count(Integer.parseInt(tmp[4]));
				dto.setLongitude(Double.parseDouble(tmp[5]));
				dto.setLatitude(Double.parseDouble(tmp[6]));
				
				list.add(dto);
			}
			
			new BikeDao().deleteAll();
			
			int res = new BikeDao().insert(list);
			
			if(res>0) {
				System.out.println("insert 성공\n");
				response.sendRedirect("index.html");
			}else {
				System.out.println("insert 싪패\n");
				response.sendRedirect("bike01.jsp");
			}			
		}
	}
	
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
