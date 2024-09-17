package services.implementations;

import domain.Labor;
import domain.Material;
import services.interfaces.LaborService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LaborServiceImpl implements LaborService {
    public List<Labor> addLabors() {
        Scanner input = new Scanner(System.in);
        Scanner inputDouble = new Scanner(System.in);
        String choiceMaterial;
        List<Labor> labors = new ArrayList<>();
        System.out.println("----Adding Labors----\n");
        do{
            System.out.println("Enter the type of labor (e.g., Basic Worker, Specialist) : ");
            String name = input.nextLine();
            System.out.println("Enter the hourly rate of this labor");
            double costPerHour = inputDouble.nextDouble();
            System.out.println("Enter the number of hours worked");
            double hoursOfWork = inputDouble.nextDouble();
            System.out.println("Enter the productivity factor (1.0 = standard, > 1.0 = high productivity)");
            double qualityCoefficient = inputDouble.nextDouble();

//            System.out.println("Enter TVA percentage of this Labor (%)");
//            double TVA = inputDouble.nextDouble() /100;
           boolean isAdded = labors.add(new Labor( name,  "Labor",  0,  qualityCoefficient,  null,  costPerHour,  hoursOfWork));
           if(isAdded){
               System.out.println("Labor added successfully");
           }else{
               System.out.println("Can't add Labor !");
           }
            System.out.println("Do you want to add another Labor");
            choiceMaterial = input.nextLine();
        }while (choiceMaterial.equals("y"));
        return labors;
    }

}
