package ru.rsreu.Yamschikov;

public class GoToPageHandler {
	private GoToPageMethodEnum method;
	private String page;

	public GoToPageHandler(GoToPageMethodEnum method, String page) {
		super();
		this.method = method;
		this.page = page;
	}

	public GoToPageMethodEnum getMethod() {
		return method;
	}

	public void setMethod(GoToPageMethodEnum method) {
		this.method = method;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

}
