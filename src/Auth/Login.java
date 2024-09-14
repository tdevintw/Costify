package Auth;

import domain.User;
//import Repository.ConsumptionRepository;
import config.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Login {

    public static User isUserExist(String name, String password) {

        String query = "SELECT * FROM users";
        try (Connection connection = Database.getInstance().getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet users = preparedStatement.executeQuery();
            List<User> allUsers = new ArrayList<>();
            User user;
            while (users.next()) {
                int id = users.getInt("id");
                String username = users.getString("name");
                String userPassword = users.getString("password");
                int userAge = users.getInt("age");
                user = new User(id , username, userPassword, userAge);
                allUsers.add(user);
            }

            Optional<User> findUser = allUsers.stream()
                    .filter(user1 -> user1.getName().equals(name) && user1.getPassword().equals(password))
                    .findFirst();
            if (findUser.isEmpty()) {
                System.out.println("Name or Password is incorrect");
                return null;
            }
            ConsumptionRepository consumptionRepository = new ConsumptionRepository();
            findUser.get().setConsumptions(consumptionRepository.getUserConsumptions(findUser.get().getId()));
            return findUser.get();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}