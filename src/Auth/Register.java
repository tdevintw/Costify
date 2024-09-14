package Auth;

import config.Database;
import domain.User;
import domain.enums.Role;

import java.sql.*;

public class Register {


    public User createUser(String name, String password, String address , String  phone , boolean isProfessional) throws SQLException {
        User newUser = null;
        if (isInputValid(name, password , phone)) {
            String query = "INSERT INTO users (name, password , address , phone , isProfessional , role) VALUES (?,?,?,?,?,?)";
            try (Connection connection = Database.getInstance().getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, address);
                preparedStatement.setString(4, phone);
                preparedStatement.setBoolean(5, isProfessional);
                preparedStatement.setObject(6, Role.valueOf("Client") , Types.OTHER);

                int rowsAdded = preparedStatement.executeUpdate();
                if (rowsAdded > 0) {
                    try (ResultSet generatedId = preparedStatement.getGeneratedKeys()) {
                        if (generatedId.next()) {
                            int id = generatedId.getInt(1);
                            newUser = new User(id,  name,  password,  address,  phone,  isProfessional, Role.valueOf("Client") , null);
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

    public static boolean isPhoneValid(String phone) {
        return phone.length() == 17;
    }

    public static boolean isInputValid(String name, String password, String phone) {
        if (!isNameValid(name)) {
            System.out.println("Name should be at least 3 characters");
            return false;
        } else if (!isPasswordValid(password)) {
            System.out.println("Password must be at least 6 characters");
            return false;
        } else if (!isPhoneValid(phone)) {
            System.out.println("phone must be 17 in length , ex : +212 xxx-xx-xx-xx");
            return false;
        } else {
            return true;
        }

    }

}
