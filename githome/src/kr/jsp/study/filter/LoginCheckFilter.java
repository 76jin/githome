package kr.jsp.study.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/LoginCheckFilter")
public class LoginCheckFilter implements Filter {

    public LoginCheckFilter() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

	    HttpServletRequest httpRequest = (HttpServletRequest)request;
	    HttpSession session = httpRequest.getSession(false);
	    boolean login = false;
	    
	    if (session != null) {
	        if (session.getAttribute("loginUser") != null) {
	            login = true;
	        }
	    }
	    
	    if (login) {   // 로그인 상태인 경우
	        // pass the request along the filter chain
	        chain.doFilter(request, response);
	    } else {   // 로그인 하지 않은 경우, 로그인 폼으로 이동한다
	        HttpServletResponse httpResponse = (HttpServletResponse)response;
	        httpResponse.sendRedirect(httpRequest.getContextPath() + "/login/loginForm.jsp");
//	        RequestDispatcher dispatcher = request.getRequestDispatcher("/login/loginForm.jsp");
//	        dispatcher.forward(request, response);
	    }
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
