package services.interfaces;

import domain.Estimate;
import domain.Project;
import domain.User;

import java.time.LocalDate;
import java.util.List;

public interface EstimateService {
    Estimate addEstimate(int projectId , double costTotal , LocalDate createdAt , LocalDate validatedUntil);

    List<Estimate> getEstimatesOfUser(User user);

    List<Estimate> validEsimates(List<Estimate> estimates);

    Estimate acceptEstimate(Estimate estimate);
}
