package kr.jsp.study.Controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.jsp.study.dao.UserDao;
import kr.jsp.study.util.CookieBox;
import kr.jsp.study.vo.User;

//@WebServlet("/JSPController")
public class JSPController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public JSPController() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    process(request, response);
	}
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        // 파라미터 한글 인코딩 처리
        request.setCharacterEncoding("UTF-8");
        
        UserDao dao;
        User user;
        String id;
        String password;
        String name;
        String email;
        int result = 0;
        
        String command = request.getRequestURI();
	    if (command.indexOf(request.getContextPath()) == 0) {
	        command = command.substring(request.getContextPath().length());
	    }
	    
	    System.out.println("command:" + command);
	    String viewUrl = null;
	    
	    switch (command) {
	    case "/login/loginForm.do":
	        viewUrl = "/login/loginForm.jsp";
	        System.out.println("called " + command + " forward to " + request.getContextPath() + "/" + viewUrl);
	        break;
	    case "/login/login.do":
	        System.out.println("called " + command);
	        // 1. get parameters
	        id = request.getParameter("id");
	        password = request.getParameter("password");
	        System.out.println("id:" + id + ", pw: " + password);
	        
	        // 2. match in db
	        dao = new UserDao();
	        user = dao.getLoginUser(id, password);
            System.out.println("result user:" + user);
            
            // 3. view url
            if (user != null) {
                // 세션에 로그인 정보 저장하기.
                HttpSession session = request.getSession();
                session.setAttribute("loginUser", user);
                
                // 쿠키에 로그인 정보 저장하기.
                response.addCookie(CookieBox.createCookie("loginUserId", user.getId(), "/", 60*60));
                response.addCookie(CookieBox.createCookie("loginUserName", user.getName(), "/", 60*60));
                
                viewUrl = "/login/success.jsp";
            } else {
                viewUrl = "/login/fail.jsp";
            }
	        break;
	    case "/login/logout.do":
	        // 1. 세션과 쿠키 제거
	        HttpSession session = request.getSession();
	        session.invalidate();
	        
	        response.addCookie(CookieBox.createCookie("loginUserId", "", "/", 0));
            response.addCookie(CookieBox.createCookie("loginUserName", "", "/", 0));
	        
	        // 2. view url
	        viewUrl = "/login/logout.jsp";
	        break;
	    case "/user/new.do":
	        viewUrl = "/user/new.jsp";
	        System.out.println("called " + command + " forward to " + request.getContextPath() + "/" + viewUrl);
	        break;
	    case "/user/add.do":
	        System.out.println("called " + command);
	        // get parameters
	        id = request.getParameter("id");
	        password = request.getParameter("password");
	        name = request.getParameter("name");
	        email = request.getParameter("email");
	        
	        // make new user;
	        User newUser = new User(id, password, name, email);
	        System.out.println(newUser.toString());
	        
	        // add to DB
	        dao = new UserDao();
	        result = dao.insert(newUser);
	        System.out.println("result inserted:" + result);
	        
	        // return view
	        if (result == 1) {
	            viewUrl = "/user/add_success.jsp";
	        } else {
	            viewUrl = "/user/add_fail.jsp";
	        }
	        break;
	    case "/list.do":
	        viewUrl = "/list.jsp";
	        break;
	    default:
	        System.out.println("invalid command:" + command);
	    }
	    
        RequestDispatcher rd = request.getRequestDispatcher(viewUrl);
        rd.forward(request, response);
    }


}
