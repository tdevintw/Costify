package Auth;

import domain.Project;
import domain.User;
import config.Database;
import domain.enums.Role;
import repositories.UserRepository;
import services.implementations.UserServiceImpl;
import services.interfaces.UserService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Login {
private static UserRepository userRepository = new UserRepository();
    public static User isUserExist(String name, String password) {

        String query = "SELECT * FROM users WHERE name = ? AND password = ?";
        try (Connection connection = Database.getInstance().getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,password);
            ResultSet users = preparedStatement.executeQuery();
            User user = null;
            while (users.next()) {
                int id = users.getInt("id");
                String username = users.getString("name");
                String userPassword = users.getString("password");
                String address = users.getString("address");
                String phone = users.getString("phone");
                boolean isProfessional = users.getBoolean("isProfessional");
                String role = users.getString("role");
                // projects for now null | projects will be added when i will add the project repository
                user = new User(id,  username,  userPassword,  address,  phone,  isProfessional, Role.valueOf(role) ,  null);
            }
            if (user == null) {
                System.out.println("Name or Password is incorrect");
                return null;
            }else{
                user.setProjects(userRepository.getProjectsOfUser(user.getId()));
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}