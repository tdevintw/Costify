package services.implementations;

import domain.Estimate;
import domain.Project;
import domain.User;
import repositories.EstimateRepository;
import services.interfaces.EstimateService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EstimateServiceImpl implements EstimateService {
    private EstimateRepository estimateRepository = new EstimateRepository();
    @Override
    public boolean addEstimate(Project  project, LocalDate createdDateOfEstimate , LocalDate validatedDateOfEstimate){
        return estimateRepository.addEstimate(project.getId() , project.getCostTotal() , createdDateOfEstimate,validatedDateOfEstimate,false);
    }

    @Override
    public List<Estimate> getEstimatesOfUser(User user){
        List<Estimate> estimates = new ArrayList<>();
        if(user.getProjects()!= null){
            for(Project project : user.getProjects()){
                estimates.addAll(estimateRepository.getEstimatesOfProject(project));
            }
        }else{
            estimates.addAll(estimateRepository.getEstimatesOfUser(user));
        }
        return estimates;
    }
}
