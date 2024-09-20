package repositories;

import config.Database;
import domain.Project;
import domain.User;
import domain.enums.Role;
import domain.enums.Status;

import javax.sound.sampled.Port;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static Auth.Register.isInputValid;

public class UserRepository {
    private ProjectRepository projectRepository = new ProjectRepository();

    public User getUser(String name)  {
        String query = "SELECT * FROM users WHERE name = ?";
        try (Connection connection = Database.getInstance().getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            User user = null;
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String password = resultSet.getString("password");
                String address = resultSet.getString("address");
                String phone = resultSet.getString("phone");
                boolean isProfessional = resultSet.getBoolean("isProfessional");
                Role role = Role.valueOf(resultSet.getString("role"));
                user = new User(id, name, password, address, phone, isProfessional, role, null);
                User finalUser = user;
            }
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean updateUserRole(String name, String role) {
        String query = "UPDATE users SET role = ? WHERE name = ? ";
        try (Connection connection = Database.getInstance().getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, Role.valueOf(role), Types.OTHER);
            preparedStatement.setString(2, name);
            int updatedRows = preparedStatement.executeUpdate();
            if (updatedRows > 0) {
                System.out.println("user role updated ");
                return true;
            } else {
                System.out.println("user name not found");
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    } //no need for this anymore it will be placed by update

    public User update(User user) {
        User newUser = null;
        if (isInputValid(user.getName(), user.getId(), user.getPassword(), user.getPhone())) {
            String query = "UPDATE  users SET name = ? , password = ? , address = ? , phone = ?, isProfessional = ? , role = ? WHERE id =?";
            try (Connection connection = Database.getInstance().getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setString(3, user.getAddress());
                preparedStatement.setString(4, user.getPhone());
                preparedStatement.setBoolean(5, user.isProfessional());
                preparedStatement.setObject(6, user.getRole(), Types.OTHER);
                preparedStatement.setInt(7, user.getId());

                int rowsAdded = preparedStatement.executeUpdate();
                if (rowsAdded > 0) {
                    return user;
                } else {
                    return null;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return user;
    }

    public boolean deleteAccount(User user)  {
        String q = "DELETE FROM users WHERE id = ?";
        try (Connection connection = Database.getInstance().getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(q)) {
            preparedStatement.setInt(1, user.getId());
            int rowsaffected = preparedStatement.executeUpdate();
            if (rowsaffected > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
