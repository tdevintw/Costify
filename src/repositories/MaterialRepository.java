package repositories;

import config.Database;
import domain.Material;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class MaterialRepository {

    public boolean addMaterial(Material material , int projectId){
        String query = "INSERT INTO materials ( name , component_type , tva , quality_coefficient , cost_per_unit , quantity , cost_of_transport , project_id) VALUES(?,?,?,?,?,?,?,?)";
        try(Connection connection = Database.getInstance().getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS) ){
            preparedStatement.setString(1,material.getName());
            preparedStatement.setString(2,material.getComponentType());
            preparedStatement.setDouble(3,material.getTVA());
            preparedStatement.setDouble(4,material.getQualityCoefficient());
            preparedStatement.setDouble(5,material.getCostPerUnit());
            preparedStatement.setDouble(6,material.getQuantity());
            preparedStatement.setDouble(7,material.costOfTransport());
            preparedStatement.setInt(8,projectId);

            int rowsAdded = preparedStatement.executeUpdate();
            if(rowsAdded>0){
                System.out.println("Material added");
                return true;
            }else{
                System.out.println("can't add Material");
                return  false;
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
