package services.implementations;

import domain.Labor;
import domain.Material;
import repositories.LaborRepository;
import services.interfaces.LaborService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LaborServiceImpl implements LaborService {
    private LaborRepository laborRepository = new LaborRepository();
    @Override
    public List<Labor> addLabors(List<Labor> labors , int projectId) {
        for(Labor labor : labors){
            laborRepository.addLabors(labor , projectId);
        }
        return labors;
    }

    @Override
    public double costTotalOfALabor(Labor labor) {
        return labor.getQualityCoefficient() * labor.getHoursOfWork() * labor.getCostPerHour();
    }
}
