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

    public Estimate addEstimate(int projectId ,double costTotal , LocalDate createdAt , LocalDate validatedUntil){
        String query = "INSERT INTO estimates (project_id , cost_total , creation_date , validated_at , is_accepted) VALUES(?,?,?,?,?)";
        try(Connection connection = Database.getInstance().getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setInt(1,projectId);
            preparedStatement.setDouble(2,costTotal);
            preparedStatement.setDate(3,Date.valueOf(createdAt));
            preparedStatement.setDate(4, Date.valueOf(validatedUntil));
            preparedStatement.setBoolean(5, false);
            int rowsAdded = preparedStatement.executeUpdate();
            if(rowsAdded>0){
                System.out.println("estimate added");
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if(resultSet.next()){
                    int estimateId = resultSet.getInt("id");
                    return new Estimate(estimateId , costTotal , createdAt , validatedUntil , false , null);
                }
            }else{
                System.out.println("can't add estimate");
                return  null;
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return null;
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
