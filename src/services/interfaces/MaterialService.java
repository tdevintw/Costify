package services.interfaces;

import domain.Material;

import java.util.List;

public interface MaterialService {
    List<Material> addMaterials(List<Material> materials , int projectId, double TVA);

    double costTotalOfAMaterialPackage(Material material);
}
