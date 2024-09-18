package services.interfaces;

import domain.Project;

import java.time.LocalDate;

public interface EstimateService {
    boolean addEstimate(Project project , LocalDate createdDate , LocalDate validatedDate);
}
