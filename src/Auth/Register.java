package Auth;

import config.Database;
import domain.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Register {


    public User createUser(String name, String password, int age) throws SQLException {
        User newUser = null;
        if (isInputValid(name, password, age)) {
            String query = "INSERT INTO users (name, password , age) VALUES (?,?,?)";
            try (Connection connection = Database.getInstance().getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, password);
                preparedStatement.setInt(3, age);
                int rowsAdded = preparedStatement.executeUpdate();
                if (rowsAdded > 0) {
                    try (ResultSet generatedId = preparedStatement.getGeneratedKeys()) {
                        if (generatedId.next()) {
                            int userId = generatedId.getInt(1);
                            newUser = new User(userId, name, password, age);
                        } else {
                            System.out.println("failed to retrieve id from database");
                        }
                    }
                    System.out.println("User was added successfully");
                    return newUser;
                } else {
                    System.out.println("User wasn't added");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return newUser;
    }

    public static boolean isNameValid(String name) {

        return name.length() >= 3;
    }

    public static boolean isPasswordValid(String password) {
        return password.length() > 5;
    }

    public static boolean isAgeValid(int age) {
        return age > 0;
    }

    public static boolean isInputValid(String name, String password, int age) {
        if (!isNameValid(name)) {
            System.out.println("Name should be at least 3 characters");
            return false;
        } else if (!isPasswordValid(password)) {
            System.out.println("Password must be at least 6 characters");
            return false;
        } else if (!isAgeValid(age)) {
            System.out.println("Age must be greater then 0");
            return false;
        } else {
            return true;
        }

    }

}
