package example01.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import example01.dao.MemoDAO;
import example01.dto.MemoDTO;

@WebServlet("/memo_servlet/*")
public class MemoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProc(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProc(request, response);
	}

	protected void doProc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String path = request.getContextPath();
		String url = request.getRequestURL().toString();
		String page = "/main/main.jsp";
		String pageNumber_;
	      pageNumber_ = request.getParameter("pageNumber");      
	      if (pageNumber_ == null || pageNumber_.trim().equals("")) {
	    	  pageNumber_ = "1";
	      }
	      int pageNumber = Integer.parseInt(pageNumber_);
		if (url.indexOf("memo.do") != -1) {
			 MemoDAO dao = new MemoDAO();
			 int pageSize = 10; //페이지에 나오는 리스트 수
	         int blockSize = 10; //아래 페이지 장수 
	         
	         int totalRecord = dao.getTotalRecord();
	         int jj = totalRecord - pageSize * (pageNumber -1);
	         
	         int startRecord = pageSize * (pageNumber - 1 ) + 1;
	         int lastRecord = pageSize * pageNumber;
	         
	         int totalPage = 0;
	         int startPage = 1;
	         int lastPage = 1;
	         
	         if(totalRecord > 0) {
	            
	            totalPage = totalRecord / pageSize + (totalRecord % pageSize == 0 ? 0 : 1);
	            startPage = (pageNumber / blockSize - (pageNumber % blockSize != 0 ? 0 : 1)) * blockSize + 1;
	                     
	            lastPage = startPage + blockSize -1;
	            if (lastPage > totalPage) {
	              lastPage = totalPage;
	            }
	         }
			 ArrayList<MemoDTO> list = dao.getSelectAll(startRecord, lastRecord);
			 
	         request.setAttribute("list", list);
	         request.setAttribute("menu_gubun", "memo_memo");
	         
	         request.setAttribute("pageNumber", pageNumber);
	         request.setAttribute("pageSize", pageSize);
	         request.setAttribute("blockSize", blockSize);
	         request.setAttribute("totalRecord", totalRecord);
	         request.setAttribute("jj", jj);
	          
	         request.setAttribute("startRecord", startRecord);
	         request.setAttribute("lastRecord", lastRecord);
	          
	         request.setAttribute("totalPage", totalPage);
	         request.setAttribute("startPage", startPage);
	         request.setAttribute("lastPage", lastPage);
	         RequestDispatcher rd = request.getRequestDispatcher(page);
	         rd.forward(request, response);      
	         
	      }  else if(url.indexOf("memoProc.do") != -1) {         
	         String name = request.getParameter("name");
	         String content = request.getParameter("content");
	         
	         name = name.replace("<", "&lt;");
	         name = name.replace(">", "&lt;");
	         name = name.replace("&", "&amp;");
	         name = name.replace("\"", "&quot;");
	         name = name.replace("'", "&qpos;");
	         
	         content = content.replace("<", "&lt;");
	         content = content.replace(">", "&lt;");
	         content = content.replace("&", "&amp;");
	         content = content.replace("\"", "&quot;");
	         content = content.replace("'", "&qpos;");
	         
	         System.out.println("aaaa");
	         
	         MemoDTO dto = new MemoDTO();
	         MemoDAO dao = new MemoDAO();
	         dto.setName(name);
	         dto.setContent(content);

	         int result = dao.getMemoInsert(dto);
	         
	         String temp; 
	         if(result > 0) {
	            temp = path+"/memo_servlet/memo.do";
	            System.out.println("등록되었습니다");   
	         } else {
	            temp = path+"/memo_servlet/memo.do";
	            System.out.println("실패_결과코드 : " + result);
	         }
	         response.sendRedirect(temp);
	      } 
	}
}
