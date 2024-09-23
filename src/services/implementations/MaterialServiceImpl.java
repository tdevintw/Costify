package services.implementations;

import domain.Material;
import repositories.MaterialRepository;
import services.interfaces.MaterialService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MaterialServiceImpl implements MaterialService {

    private  MaterialRepository materialRepository = new MaterialRepository();
    @Override
    public List<Material> addMaterials(List<Material> materials , int projectId) {
        for(Material material : materials){
            materialRepository.addMaterial(material,projectId);
        }
        return materials;
    }

    @Override
    public double costTotalOfAMaterialPackage(Material material){
        return (material.getQuantity()*material.getCostPerUnit()*material.getQualityCoefficient())+material.costOfTransport();
    }
}
