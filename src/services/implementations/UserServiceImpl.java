package services.implementations;

import domain.User;
import repositories.UserRepository;
import services.interfaces.UserService;

import java.sql.SQLException;

public class UserServiceImpl implements UserService {

    private UserRepository userRepository = new UserRepository();

    @Override
    public User getUser(String name) {

            User user = userRepository.getUser(name);
            if (user != null) {
                System.out.println("Client Found !");
                return user;
            } else {
                System.out.println("User Not Found ?!");
                return null;
            }

    }

    @Override
    public User update(User user) {
        return userRepository.update(user);
    }

    @Override
    public boolean deleteAccount(User user) {
        return userRepository.deleteAccount(user);
    }
}
