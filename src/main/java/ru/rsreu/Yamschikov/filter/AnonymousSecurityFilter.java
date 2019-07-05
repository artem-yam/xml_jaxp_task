package ru.rsreu.Yamschikov.filter;

import ru.rsreu.Yamschikov.datalayer.data.user.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AnonymousSecurityFilter implements Filter {
    
    /**
     * Method what realizes filtration of request: checks if user tries to
     * interact with servlet with no log in
     */
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String action = request.getParameter("command");
        HttpSession session = req.getSession(false);
        
        System.out.println("------------------------");
        System.out.println("Anonymous Filter info:");
        
        System.out.println("session : " + session);
        System.out.println("user action : " + action);
        
        if ("true"
                .equals(req.getSession().getAttribute("AnonymousFilterWorked"))
                && req.getSession().getAttribute("AnonymousFilterWorked") !=
                       null) {
            req.getSession().setAttribute("AnonymousFilterWorked", "false");
        } else {
            req.getSession().removeAttribute("AnonymousFilterMessage");
        }
        
        if (session != null) {
            User user = (User) session.getAttribute("currentUser");
            
            System.out.println("Current user: " + user);
            
            if (user == null
                    && !("login".equals(action) || "logout".equals(action) ||
                             action == null || action.isEmpty())) {
                System.out.println("---------------------------");
                System.out.println("Anonymous Security filter");
                
                req.getSession().setAttribute("AnonymousFilterMessage",
                    "Попытка взаимодействия без входа в систему");
                req.getSession().setAttribute("AnonymousFilterWorked", "true");
                
                resp.sendRedirect(req.getRequestURI());
                
                System.out.println("---------------------------");
                
                return;
            }
        }
        
        chain.doFilter(request, response);
    }
}