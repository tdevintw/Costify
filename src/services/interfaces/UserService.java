package services.interfaces;

import domain.User;

import java.util.List;

public interface  UserService {

    User getUser(String name);

    User update(User user);

    boolean deleteAccount(User user);

    List<User> getAll();
}
