package services.interfaces;

import domain.Material;

import java.util.List;

public interface MaterialService {
    List<Material> addMaterials();

    double costTotalOfAMaterialPackage(Material material);
}
