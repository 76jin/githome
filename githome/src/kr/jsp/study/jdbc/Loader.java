package kr.jsp.study.jdbc;

import java.util.StringTokenizer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class Loader extends HttpServlet {
    
    @Override
    public void init(ServletConfig config) throws ServletException {
//        super.init(config);
        try {
            String drivers = config.getInitParameter("jdbcdriver");
            System.out.println("Load drivers:" + drivers);
            StringTokenizer st = new StringTokenizer(drivers, ",");
            while (st.hasMoreTokens()) {
                String jdbcDriver = st.nextToken();
                Class.forName(jdbcDriver);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
    
}
