package services.implementations;

import domain.Estimate;
import domain.Project;
import domain.User;
import repositories.EstimateRepository;
import repositories.ProjectRepository;
import services.interfaces.EstimateService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EstimateServiceImpl implements EstimateService {
    private EstimateRepository estimateRepository = new EstimateRepository();
    private ProjectRepository projectRepository = new ProjectRepository();

    @Override
    public boolean addEstimate(Project  project, LocalDate createdDateOfEstimate , LocalDate validatedDateOfEstimate){
        return estimateRepository.addEstimate(project.getId() , project.getCostTotal() , createdDateOfEstimate,validatedDateOfEstimate,false);
    }

    @Override
    public List<Estimate> getEstimatesOfUser(User user){
        List<Estimate> estimates = new ArrayList<>();
        if(user.getProjects() ==null){
            List<Project> projects = projectRepository.getProjectsOfUser(user);
            user.setProjects(projects);
        }
            for(Project project : user.getProjects()){
                estimates.addAll(estimateRepository.getEstimatesOfProject(project));
            }

        return estimates;
    }
}
