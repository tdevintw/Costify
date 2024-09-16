package services.implementations;

import domain.Material;
import services.interfaces.MaterialService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MaterialServiceImpl implements MaterialService {

    @Override
    public List<Material> addMaterials() {
        Scanner input = new Scanner(System.in);
        Scanner inputDouble = new Scanner(System.in);
        String choiceMaterial;
        List<Material> materials = new ArrayList<>();
        System.out.println("----Adding Materials----\n");
        do{
            System.out.println("Material Name : ");
            String name = input.nextLine();
            System.out.println("Enter the quantity of this material (in m²)");
            double quantity = inputDouble.nextDouble();
            System.out.println("Enter the unit cost of this material (€/m²)");
            double costPerUnit = inputDouble.nextDouble();
            System.out.println("Enter the transport cost of this material (€)");
            double costOfTransport = inputDouble.nextDouble();
            System.out.println("Enter the quality coefficient of the material (1.0 = standard, > 1.0 = high quality)");
            double qualityCoefficient = inputDouble.nextDouble();
            System.out.println("Enter TVA percentage of this product (%)");
            double TVA = inputDouble.nextDouble() /100;
            materials.add(new Material(name , "Material" ,TVA , qualityCoefficient , null , costPerUnit , quantity , costOfTransport ));
            System.out.println("Do you want to add another Material");
            choiceMaterial = input.nextLine();
        }while (choiceMaterial.equals("y"));
        return materials;
    }
}
