package services.implementations;

import domain.User;
import repositories.UserRepository;
import services.interfaces.UserService;

import java.sql.SQLException;

public class UserServiceImpl implements UserService {
private UserRepository userRepository = new UserRepository();
    @Override
    public User getUser(String name) {
        try{
            User user = userRepository.getUser(name);
            if(user != null){
                System.out.println("Client Found !");
                return  user;
            }else{
                System.out.println("User Not Found ?!");
                return null;
            }
        }catch(SQLException e){
            throw  new RuntimeException(e);
        }
    }
}
