package services.implementations;

import domain.Estimate;
import domain.Project;
import domain.User;
import repositories.EstimateRepository;
import services.interfaces.EstimateService;

import java.time.LocalDate;
import java.util.List;

public class EstimateServiceImpl implements EstimateService {
    private EstimateRepository estimateRepository = new EstimateRepository();
    public boolean addEstimate(Project  project, LocalDate createdDateOfEstimate , LocalDate validatedDateOfEstimate){
        return estimateRepository.addEstimate(project.getId() , project.getCostTotal() , createdDateOfEstimate,validatedDateOfEstimate,false);
    }

    public List<Estimate> getEstimates(User user){
        List<Estimate> estimates = user.getProjects().stream().map(project -> project.getEstimates()).flatMap(list->list.stream()).toList();
        return estimates;
    }
}
