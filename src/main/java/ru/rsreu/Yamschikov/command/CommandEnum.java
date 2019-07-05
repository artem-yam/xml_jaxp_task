package ru.rsreu.Yamschikov.command;

public enum CommandEnum {
	LOGOUT {
		{
			this.command = new LogoutCommand();
		}
	},
	ADMINPAGE {
		{
			this.command = new AdminPageCommand();
		}
	},
	USERCREATIONPAGE {
		{
			this.command = new UserCreationPageCommand();
		}
	},
	USERCREATIONCOMPLETED {
		{
			this.command = new UserCreationCompletedCommand();
		}
	},
	USEREDITPAGE {
		{
			this.command = new UserEditPageCommand();
		}
	},
	USEREDITCOMPLETED {
		{
			this.command = new UserEditCompletedCommand();
		}
	},
	USERDELETE {
		{
			this.command = new UserDeleteCommand();
		}
	},
	USERSEARCH {
		{
			this.command = new UserSearchCommand();
		}
	},
	USERACTIVITYPAGE {
		{
			this.command = new UserActivityPageCommand();
		}
	},
	MAINPAGE {
		{
			this.command = new MainPageCommand();
		}
	},
	ACTIVITYREVIEWPAGE {
		{
			this.command = new ActivityReviewPageCommand();
		}
	},
	ACTIVITYCREATIONPAGE {
		{
			this.command = new ActivityCreationPageCommand();
		}
	},
	ACTIVITYCREATIONSTEP2 {
		{
			this.command = new ActivityCreationStep2Command();
		}
	},
	ACTIVITYCREATIONCOMPLETED {
		{
			this.command = new ActivityCreationCompletedCommand();
		}
	},
	ACTIVITYEDITPAGE {
		{
			this.command = new ActivityEditPageCommand();
		}
	},
	ACTIVITYEDITCOMPLETED {
		{
			this.command = new ActivityEditCompletedCommand();
		}
	},
	ACTIVITYDELETE {
		{
			this.command = new ActivityDeleteCommand();
		}
	},
	ACTIVITYSEARCH {
		{
			this.command = new ActivitySearchCommand();
		}
	},
	USERACTIVITYSEARCH {
		{
			this.command = new UserActivitySearchCommand();
		}
	},
	REPORTPAGE {
		{
			this.command = new ReportPageCommand();
		}
	},
	SMALLREPORT {
		{
			this.command = new SmallReportCommand();
		}
	};

	ActionCommand command;

	public ActionCommand getCurrentCommand() {
		return command;
	}
}