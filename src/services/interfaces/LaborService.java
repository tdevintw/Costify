package services.interfaces;

import domain.*;

import java.util.List;

public interface LaborService {
    List<Labor> addLabors();


    public double costTotalOfALabor(Labor labor);

}

