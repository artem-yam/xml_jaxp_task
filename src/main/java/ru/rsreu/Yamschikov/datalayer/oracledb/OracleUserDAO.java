package ru.rsreu.Yamschikov.datalayer.oracledb;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.rsreu.Yamschikov.datalayer.UserDAO;
import ru.rsreu.Yamschikov.datalayer.data.user.User;
import ru.rsreu.Yamschikov.datalayer.data.user.UserFullName;
import ru.rsreu.Yamschikov.datalayer.data.user.UserPersonalData;
import ru.rsreu.Yamschikov.datalayer.data.user.UserTypeEnum;

public class OracleUserDAO implements UserDAO {
	private Connection connection;

	public OracleUserDAO(Connection connection) {
		this.connection = connection;
	}

	private List<User> formUserList(ResultSet rs) {
		List<User> lst = new ArrayList<User>();
		try {
			while (rs.next()) {
				int id = rs.getInt(1);
				String login = rs.getString(2);
				String password = rs.getString(3);
				String lastName = rs.getString(4);
				String firstName = rs.getString(5);
				String middleName = rs.getString(6);

				UserFullName fullName = new UserFullName(lastName, firstName, middleName);

				String typeStr = rs.getString(7);
				UserTypeEnum type = UserTypeEnum.getType(typeStr);

				String phoneNumber = rs.getString(8);
				String extraInfo = rs.getString(9);
				String status = rs.getString(10);
				String department = rs.getString(11);
				String degreeAndPost = rs.getString(12);

				UserPersonalData userData = new UserPersonalData(fullName, phoneNumber, extraInfo, department,
						degreeAndPost);

				User user = new User(id, login, password, userData, type, status);

				if (!lst.contains(user)) {
					lst.add(user);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lst;
	}

	private User formUser(ResultSet rs) {
		User user = null;

		try {
			while (rs.next()) {
				int id = rs.getInt(1);
				String login = rs.getString(2);
				String password = rs.getString(3);
				String lastName = rs.getString(4);
				String firstName = rs.getString(5);
				String middleName = rs.getString(6);

				UserFullName fullName = new UserFullName(lastName, firstName, middleName);

				String typeStr = rs.getString(7);

				UserTypeEnum type = UserTypeEnum.getType(typeStr);
				// UserTypeEnum type =
				// UserTypeEnum.valueOf(typeStr.toLowerCase());

				String phoneNumber = rs.getString(8);
				String extraInfo = rs.getString(9);
				String status = rs.getString(10);
				String department = rs.getString(11);
				String degreeAndPost = rs.getString(12);

				UserPersonalData userData = new UserPersonalData(fullName, phoneNumber, extraInfo, department,
						degreeAndPost);

				user = new User(id, login, password, userData, type, status);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}

	@Override
	public List<User> getAllUsers() {
		List<User> lst = new ArrayList<User>();

		try {
			Statement st = null;
			try {
				st = connection.createStatement();
				ResultSet rs = null;
				try {
					rs = st.executeQuery(("request.users.select.all.orderbystatus"));

					lst = formUserList(rs);

					if (lst.size() > 0) {
						System.out.println(lst);
					} else {
						System.out.println("Not found");
					}
				} finally {
					if (rs != null) {
						rs.close();
					} else {
						System.err.println("ошибка во время чтения из БД");
					}
				}
			} finally {
				if (st != null) {
					st.close();
				} else {
					System.err.println("Statement не создан");
				}
			}
		} catch (SQLException e) {
			System.err.println("DB connection error: " + e);
		}

		return lst;
	}

	@Override
	public User getUserById(int userId) {
		User user = null;
		try {
			PreparedStatement ps = null;
			try {
				ps = connection.prepareStatement(("request.users.select.byid"));
				ps.setInt(1, userId);
				ResultSet rs = null;
				try {
					rs = ps.executeQuery();

					user = formUser(rs);
				} finally {
					if (rs != null) {
						rs.close();
					} else {
						System.err.println("ошибка во время чтения из БД");
					}
				}
			} finally {
				if (ps != null) {
					ps.close();
				} else {
					System.err.println("Statement не создан");
				}
			}
		} catch (SQLException e) {
			System.err.println("DB connection error: " + e);
		}
		return user;
	}

	@Override
	public User getUserByLogin(String userLogin) {
		User user = null;
		try {
			PreparedStatement ps = null;
			try {
				ps = connection.prepareStatement(("request.users.select.bylogin"));
				ps.setString(1, userLogin);
				ResultSet rs = null;
				try {
					rs = ps.executeQuery();

					user = formUser(rs);
				} finally {
					if (rs != null) {
						rs.close();
					} else {
						System.err.println("ошибка во время чтения из БД");
					}
				}
			} finally {
				if (ps != null) {
					ps.close();
				} else {
					System.err.println("Statement не создан");
				}
			}
		} catch (SQLException e) {
			System.err.println("DB connection error: " + e);
		}
		return user;
	}

	@Override
	public void setOnline(String userLogin) {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(("request.users.update.online"));
			ps.setString(1, "online");
			ps.setString(2, userLogin);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println("DB connection error: " + e);
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void setOffline(String userLogin) {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(("request.users.update.offline"));
			ps.setString(1, "offline");
			ps.setString(2, userLogin);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println("DB connection error: " + e);
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void deleteUser(String userLogin) {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(("request.users.delete"));
			ps.setString(1, userLogin);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println("DB connection error: " + e);
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void editUser(String oldLogin, String userLogin, String userPass, UserPersonalData userData,
			String userType) {
		PreparedStatement ps = null;

		/*
		 * System.out.println("Старый логин: " + oldLogin);
		 * System.out.println("Новый логин: " + userLogin);
		 * System.out.println("Новый пароль: " + userPass);
		 * System.out.println("Новые данные: " + userData);
		 * System.out.println("Новый тип: " + userType);
		 */

		try {
			ps = connection.prepareStatement(("request.users.update"));
			ps.setString(1, userLogin);
			ps.setString(2, userPass);

			ps.setString(3, userData.getFullName().getLastName());
			ps.setString(4, userData.getFullName().getFirstName());
			ps.setString(5, userData.getFullName().getMiddleName());

			ps.setString(6, userType);
			ps.setString(7, userData.getPhoneNumber());
			ps.setString(8, userData.getExtraInfo());
			ps.setString(9, userData.getDepartmentName());
			ps.setString(10, userData.getDegreeAndPost());

			ps.setString(11, oldLogin);

			System.out.println("Перед выполнением запроса");

			ps.executeUpdate();

			System.out.println("Пользователь изменен");
		} catch (SQLException e) {
			System.err.println("DB connection error: " + e);
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public int getNextId() {
		Statement st = null;
		ResultSet rs = null;
		int id = 1;
		try {
			st = connection.createStatement();
			rs = st.executeQuery(("request.users.select.maxid"));
			while (rs.next()) {
				id = rs.getInt(1) + 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				System.err.println("ошибка во время чтения из БД");
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				System.err.println("ошибка во время чтения из БД");
			}
		}
		return id;
	}

	@Override
	public void createUser(String userLogin, String userPass, UserPersonalData userData, String userType) {
		PreparedStatement ps = null;
		try {
			int id = getNextId();

			ps = connection.prepareStatement(("request.users.insert"));
			ps.setInt(1, id);
			ps.setString(2, userLogin);
			ps.setString(3, userPass);
			ps.setString(4, userData.getFullName().getLastName());
			ps.setString(5, userData.getFullName().getFirstName());
			ps.setString(6, userData.getFullName().getMiddleName());
			ps.setString(7, userType);
			ps.setString(8, userData.getPhoneNumber());
			ps.setString(9, userData.getExtraInfo());
			ps.setString(10, "offline");
			ps.setString(11, userData.getDepartmentName());
			ps.setString(12, userData.getDegreeAndPost());

			ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println("DB connection error: " + e);
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public List<User> getSearсhedUsers(String searchText) {
		List<User> lst = new ArrayList<User>();

		List<String> searchString = Arrays.asList(searchText.toLowerCase().split(" "));

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			for (int i = 0; i < searchString.size(); i++) {
				ps = connection.prepareStatement(("request.users.select.all.search"));
				ps.setString(1, searchString.get(i));
				ps.setString(2, searchString.get(i));
				ps.setString(3, searchString.get(i));
				ps.setString(4, searchString.get(i));
				ps.setString(5, searchString.get(i));
				ps.setString(6, searchString.get(i));
				ps.setString(7, searchString.get(i));
				ps.setString(8, searchString.get(i));
				ps.setString(9, searchString.get(i));
				ps.setString(10, searchString.get(i));

				try {
					rs = ps.executeQuery();

					lst = formUserList(rs);

					if (lst.size() > 0) {
						System.out.println(lst);
					} else {
						System.out.println("Not found");
					}
				} catch (SQLException e) {
					System.err.println("DB error: " + e);
				}
			}
		} catch (SQLException e) {
			System.err.println("DB connection error: " + e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				System.err.println("ошибка во время чтения из БД");
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				System.err.println("Statement не создан");
			}
		}
		return lst;
	}

}
