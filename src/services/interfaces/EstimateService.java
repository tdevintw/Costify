package services.interfaces;

import domain.Estimate;
import domain.Project;
import domain.User;

import java.time.LocalDate;
import java.util.List;

public interface EstimateService {
    boolean addEstimate(Project project , LocalDate createdDate , LocalDate validatedDate);

    List<Estimate> getEstimatesOfUser(User user);

    List<Estimate> validEsimates(List<Estimate> estimates);

    Estimate acceptEstimate(Estimate estimate);
}
