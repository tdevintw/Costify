package services.interfaces;

import domain.User;

public interface  UserService {

    User getUser(String name);

    User update(User user);

    boolean deleteAccount(User user);
}
