package ru.rsreu.Yamschikov;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import ru.rsreu.Yamschikov.datalayer.UserDAO;
import ru.rsreu.Yamschikov.datalayer.data.user.User;

@WebListener
public class SessionListener implements HttpSessionListener {
	public void attributeRemoved(HttpSessionBindingEvent ev) {
	}

	@Override
	public void sessionCreated(HttpSessionEvent ev) {
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent ev) {

		System.out.println("Сессия уничтожена :" + ev);

		UserDAO userDAO = (UserDAO) ev.getSession().getServletContext().getAttribute("userDAO");
		User user = (User) ev.getSession().getAttribute("currentUser");
		if (user != null) {
			userDAO.setOffline(user.getLogin());
		}
	}
}