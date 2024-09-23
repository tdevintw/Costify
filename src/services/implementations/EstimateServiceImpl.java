package services.implementations;

import domain.Estimate;
import domain.Project;
import domain.User;
import domain.enums.Status;
import repositories.EstimateRepository;
import repositories.ProjectRepository;
import services.interfaces.EstimateService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EstimateServiceImpl implements EstimateService {
    private EstimateRepository estimateRepository = new EstimateRepository();
    private ProjectRepository projectRepository = new ProjectRepository();

    @Override
    public Estimate addEstimate(int projectId , double costTotal ,LocalDate  createdAt ,  LocalDate validatedUntil){
        return estimateRepository.addEstimate(projectId , costTotal , createdAt , validatedUntil);
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

    @Override
    public List<Estimate> validEsimates(List<Estimate> estimates){
        LocalDate now = LocalDate.now();
        return estimates.stream().filter(estimate -> !estimate.isAccepted()).filter(estimate -> (estimate.getValidatedAt().isBefore(now) || estimate.getValidatedAt().isEqual(now)) && estimate.getProject().getStatus()== Status.InProgress).collect(Collectors.toList());
    }

    @Override
    public Estimate acceptEstimate(Estimate estimate){
        return estimateRepository.acceptEstimate(estimate);
    }
}
