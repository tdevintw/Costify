package repositories;

import config.Database;
import domain.Estimate;
import domain.Project;
import domain.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EstimateRepository {

    public boolean addEstimate(int projectId , double costTotal , LocalDate creationDate , LocalDate validatedAt , boolean isAccepted){
        String query = "INSERT INTO estimates (project_id , cost_total , creation_date , validated_at , is_accepted) VALUES(?,?,?,?,?)";
        try(Connection connection = Database.getInstance().getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1,projectId);
            preparedStatement.setDouble(2,costTotal);
            preparedStatement.setDate(3,Date.valueOf(creationDate));
            preparedStatement.setDate(4, Date.valueOf(validatedAt));
            preparedStatement.setBoolean(5, isAccepted);
            int rowsAdded = preparedStatement.executeUpdate();
            if(rowsAdded>0){
                System.out.println("estimate added");
                return true;
            }else{
                System.out.println("can't add estimate");
                return  false;
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }



    public List<Estimate> getEstimatesOfProject(Project project){
        String q = "SELECT * FROM estimates WHERE project_id = ?";
        List<Estimate> estimates =  new ArrayList<>();
        try(Connection connection = Database.getInstance().getConnection() ; PreparedStatement preparedStatement = connection.prepareStatement(q)){
            preparedStatement.setInt(1,project.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                double costTotal = resultSet.getDouble("cost_total");
                LocalDate creationDate = resultSet.getDate("creation_date").toLocalDate();
                LocalDate validatedUntil = resultSet.getDate("validated_at").toLocalDate();
                boolean isAccepted = resultSet.getBoolean("is_accepted");
                estimates.add(new Estimate(id , costTotal , creationDate , validatedUntil , isAccepted , project));
            }
        }catch (SQLException e){
            throw  new RuntimeException(e);
        }
        project.setEstimates(estimates);
        return estimates;
    }

    public Estimate acceptEstimate(Estimate estimate){
        String q= "UPDATE estimates SET is_accepted = ? WHERE id = ?";
        try(Connection connection = Database.getInstance().getConnection() ; PreparedStatement preparedStatement = connection.prepareStatement(q)){
            preparedStatement.setBoolean(1,true);
            preparedStatement.setInt(2,estimate.getId());
            int rowsUpdated = preparedStatement.executeUpdate();
            if(rowsUpdated>0){
                estimate.setAccepted(true);
                return estimate;
            }else{
                return null;
            }
        }catch (SQLException e){
            throw  new RuntimeException(e);
        }
    }
}
