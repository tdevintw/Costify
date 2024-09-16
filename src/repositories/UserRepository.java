package repositories;

import config.Database;
import domain.Project;
import domain.User;
import domain.enums.Role;
import domain.enums.Status;

import javax.sound.sampled.Port;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private ProjectRepository projectRepository = new ProjectRepository();

    public User getUser(String name) throws SQLException {
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
                List<Project> projects = getProjectsOfUser(id);
                user = new User(id, name, password,  address,  phone,  isProfessional,  role , projects);
                User finalUser = user;
                projects.forEach(project -> project.setUser(finalUser));
            }
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Project> getProjectsOfUser(int id) {
        String query = "SELECT * FROM projects WHERE user_id = ?";
        try (Connection connection = Database.getInstance().getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Project> projects = new ArrayList<>();
            while (resultSet.next()) {
                int projectId = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double profitMargin = resultSet.getDouble("profit_margin");
                double costTotal = resultSet.getDouble("cost_total");
                Status status = Status.valueOf(resultSet.getString("status"));
                Project project = new Project(projectId, name , profitMargin , costTotal , status , projectRepository.getLaborsOfProject(projectId) , projectRepository.getMaterialsOfProject(projectId) , null , projectRepository.getEstimatesOfProject(projectId));
                project.getLabors().forEach(labor -> labor.setProject(project));
                project.getMaterials().forEach(material -> material.setProject(project));
                project.getEstimates().forEach(estimate -> estimate.setProject(project));
                projects.add(project);
            }
            return projects;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
