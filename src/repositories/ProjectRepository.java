package repositories;

import config.Database;
import domain.*;
import domain.enums.Status;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProjectRepository {

    public Project addProject(User client, String projectName , double profitMargin , double costTotal) {
        String query = "INSERT INTO projects (user_id , name , profit_margin , cost_total , status) VALUES(?,?,?,?,?)";
        try(Connection connection = Database.getInstance().getConnection()  ;PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS) ){
            preparedStatement.setInt(1,client.getId());
            preparedStatement.setString(2,projectName);
            preparedStatement.setDouble(3,profitMargin);
            preparedStatement.setDouble(4,costTotal);
            preparedStatement.setObject(5, Status.InProgress , Types.OTHER);
            int rowsAdded = preparedStatement.executeUpdate();
            if(rowsAdded>0){
                System.out.println("project added");
                try(ResultSet resultSet = preparedStatement.getGeneratedKeys()){
                if(resultSet.next()){
                    int id = resultSet.getInt(1);
                    return new Project(id , projectName , profitMargin , costTotal , Status.InProgress , null , null , null , null);
                }else{
                    System.out.println("retrieving id failed");
                    return null;
                }
                }
            }else{
                System.out.println("can't add project");
                return  null;
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public List<Project> getProjectsOfUser(User user) {
        String query = "SELECT * FROM projects WHERE user_id = ?";
        try (Connection connection = Database.getInstance().getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Project> projects = new ArrayList<>();
            while (resultSet.next()) {
                int projectId = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double profitMargin = resultSet.getDouble("profit_margin");
                double costTotal = resultSet.getDouble("cost_total");
                Status status = Status.valueOf(resultSet.getString("status"));
                Project project = new Project(projectId, name, profitMargin, costTotal, status,null, null, user, null);
                projects.add(project);
            }
            return projects;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Project  acceptProject(Project project){
        String q = "UPDATE projects SET status = ? WHERE id = ? ";
        try(Connection connection = Database.getInstance().getConnection() ; PreparedStatement preparedStatement = connection.prepareStatement(q)) {
            preparedStatement.setObject(1,Status.Completed, Types.OTHER);
            preparedStatement.setInt(2,project.getId());
            int rowsAffected = preparedStatement.executeUpdate();
            if(rowsAffected>0){
                project.setStatus(Status.Completed);
                return project;
            }else{
                return null;
            }
        }catch (SQLException e){
            throw  new RuntimeException(e);
        }
    }

    public Project  refuseProject(Project project){
        String q = "UPDATE projects SET status = ? WHERE id = ? ";
        try(Connection connection = Database.getInstance().getConnection() ; PreparedStatement preparedStatement = connection.prepareStatement(q)) {
            preparedStatement.setObject(1,Status.Canceled, Types.OTHER);
            preparedStatement.setInt(2,project.getId());
            int rowsAffected = preparedStatement.executeUpdate();
            if(rowsAffected>0){
                project.setStatus(Status.Canceled);
                return project;
            }else{
                return null;
            }
        }catch (SQLException e){
            throw  new RuntimeException(e);
        }
    }

}
