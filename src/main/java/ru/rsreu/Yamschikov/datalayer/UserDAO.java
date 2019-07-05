package ru.rsreu.Yamschikov.datalayer;

import java.util.List;

import ru.rsreu.Yamschikov.datalayer.data.user.User;
import ru.rsreu.Yamschikov.datalayer.data.user.UserPersonalData;

public interface UserDAO {

	User getUserById(int userId);

	User getUserByLogin(String userLogin);

	List<User> getAllUsers();

	void deleteUser(String userLogin);

	void editUser(String oldLogin, String userLogin, String userPass, UserPersonalData userData, String userType);

	void createUser(String userLogin, String userPass, UserPersonalData userData, String userType);

	List<User> getSearсhedUsers(String searchText);

	void setOffline(String userLogin);

	void setOnline(String userLogin);

	int getNextId();
}
