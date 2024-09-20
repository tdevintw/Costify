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

    public List<Estimate> getEstimatesOfUser(User user){

    }

    public List<Estimate> getEstimatesOfProject(Project project){

    }
}
