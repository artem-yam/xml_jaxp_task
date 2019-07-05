package ru.rsreu.Yamschikov.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class NullSessionFilter implements Filter {
    
    /**
     * Method what realizes filtration of request: checks if session was
     * destroyed and not created yet
     */
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        
        System.out.println("-------------------");
        System.out.println("Session Filter info:");
        
        System.out.println("session = " + session);
        
        if ("true".equals(req.getSession().getAttribute("SessionFilterWorked"))
                &&
                req.getSession().getAttribute("SessionFilterWorked") != null) {
            req.getSession().setAttribute("SessionFilterWorked", "false");
        } else {
            req.getSession().removeAttribute("sessionTimeout");
        }
        
        if (session == null &&
                req.getServletContext().getAttribute("factory") != null) {
            
            System.out.println("------------------");
            System.out.println("Null session filter");
            System.out.println("------------------");
            
            req.getSession().setAttribute("SessionFilterWorked", "true");
            req.getSession().setAttribute("sessionTimeout",
                "Время ожидания сессии истекло");
            
            resp.sendRedirect(req.getRequestURI());
            return;
        }
        
        chain.doFilter(request, response);
    }
}