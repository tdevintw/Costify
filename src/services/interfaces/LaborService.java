package services.interfaces;

import domain.*;

import java.util.List;

public interface LaborService {
    List<Labor> addLabors(List<Labor> labors , int projectId);

    double costTotalOfALabor(Labor labor);

}

