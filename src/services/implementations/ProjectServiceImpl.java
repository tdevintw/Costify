package services.implementations;

import domain.Labor;
import domain.Material;
import domain.Project;
import domain.User;
import domain.enums.Status;
import repositories.LaborRepository;
import repositories.MaterialRepository;
import repositories.ProjectRepository;
import services.interfaces.ProjectService;
import services.interfaces.UserService;

import java.util.List;

public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository projectRepository = new ProjectRepository();
    private UserService userService = new UserServiceImpl();

    @Override
    public Project addProject(User client, String projectName, double profitMargin, double costTotal) {
        return projectRepository.addProject(client, projectName, profitMargin, costTotal);
    }

    @Override
    public List<Project> getProjectsOfUser(User user) {
        return projectRepository.getProjectsOfUser(user);
    }

    @Override
    public Project acceptProject(Project project) {
        return projectRepository.acceptProject(project);
    }

    @Override
    public Project refuseProject(Project project) {
        return projectRepository.refuseProject(project);
    }

    @Override
    public List<Project> getAll() {
        return projectRepository.getAll();
    }

    @Override
    public Project editProject(Project project) {
        return projectRepository.editProject(project);
    }

    @Override
    public boolean deleteProject(Project project) {
        return projectRepository.deleteProject(project);
    }


}
