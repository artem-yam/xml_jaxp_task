package ru.rsreu.Yamschikov.command;

import javax.servlet.http.HttpServletRequest;

import ru.rsreu.Yamschikov.GoToPageHandler;

public interface ActionCommand {
	GoToPageHandler execute(HttpServletRequest request);
}