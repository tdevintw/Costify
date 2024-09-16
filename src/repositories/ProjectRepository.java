package repositories;

import config.Database;
import domain.Estimate;
import domain.Labor;
import domain.Material;
import domain.Project;
import domain.enums.Status;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProjectRepository {

    public List<Labor> getLaborsOfProject(int id) {
        String query = "SELECT * FROM labors WHERE project_id = ?";
        try (Connection connection = Database.getInstance().getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Labor> labors = new ArrayList<>();
            while (resultSet.next()) {
                int laborId = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String componentType = resultSet.getString("component_type");
                double tva = resultSet.getDouble("tva");
                double qualityCoefficient = resultSet.getDouble("quality_coefficent");
                double costPerHour = resultSet.getDouble("cost_per_hour");
                double hoursOfWork = resultSet.getDouble("hours_of_work");
                labors.add(new Labor(laborId, name, componentType, tva, qualityCoefficient, null, costPerHour, hoursOfWork));
            }
            return labors;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Material> getMaterialsOfProject(int id) {
        String query = "SELECT * FROM materials WHERE project_id = ?";
        try (Connection connection = Database.getInstance().getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Material> materials = new ArrayList<>();
            while (resultSet.next()) {
                int materialId = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String componentType = resultSet.getString("component_type");
                double tva = resultSet.getDouble("tva");
                double qualityCoefficient = resultSet.getDouble("quality_coefficient");
                double costPerUnit = resultSet.getDouble("cost_per_unit");
                double quantity = resultSet.getDouble("quantity");
                double costOfTransport = resultSet.getDouble("cost_of_transport");
                materials.add(new Material(materialId, name, componentType, tva, qualityCoefficient, null, costPerUnit, quantity, costOfTransport));
            }
            return materials;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Estimate> getEstimatesOfProject(int id) {
        String query = "SELECT * FROM estimates WHERE project_id = ?";
        try (Connection connection = Database.getInstance().getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Estimate> estimates = new ArrayList<>();
            while (resultSet.next()) {
                int estimateId = resultSet.getInt("id");
                double costTotal = resultSet.getDouble("cost_total");
                LocalDate creationDate = resultSet.getDate("creation_date").toLocalDate();
                LocalDate validatedAt = resultSet.getDate("validated_at").toLocalDate();
                boolean isAccepted = resultSet.getBoolean("is_accepted");
                estimates.add(new Estimate(id, costTotal, creationDate, validatedAt, isAccepted, null));
            }
            return estimates;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean addProject(int userId , String name , double profitMargin , double costTotal , Status status) {
        String query = "INSERT INTO projects (user_id , name , profit_margin , cost_total , status) VALUES(?,?,?,?,?)";
        try(Connection connection = Database.getInstance().getConnection()  ;PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1,userId);
            preparedStatement.setString(2,name);
            preparedStatement.setDouble(3,profitMargin);
            preparedStatement.setDouble(4,costTotal);
            preparedStatement.setObject(5, status , Types.OTHER);
            int rowsAdded = preparedStatement.executeUpdate();
            if(rowsAdded>0){
                System.out.println("project added");
                return true;
            }else{
                System.out.println("can't add project");
                return  false;
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
