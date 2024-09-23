package repositories;

import config.Database;
import domain.Labor;
import domain.Project;

import java.sql.*;

public class LaborRepository {

    public boolean addLabors(Labor labor , int projectId, double TVA){
        String query = "INSERT INTO labors (project_id , name , component_type , tva , quality_coefficient , cost_per_hour , hours_of_work) VALUES(?,?,?,?,?,?,?)";
        try(Connection connection = Database.getInstance().getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS) ){
            preparedStatement.setInt(1,projectId);
            preparedStatement.setString(2,labor.getName());
            preparedStatement.setString(3,labor.getComponentType());
            preparedStatement.setDouble(4,TVA*100);
            preparedStatement.setDouble(5,labor.getQualityCoefficient());
            preparedStatement.setDouble(6,labor.getCostPerHour());
            preparedStatement.setDouble(7,labor.getHoursOfWork());
            int rowsAdded = preparedStatement.executeUpdate();
            if(rowsAdded>0){
                System.out.println("Labor added");
                return true;
            }else{
                System.out.println("can't add Labor");
                return  false;
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
