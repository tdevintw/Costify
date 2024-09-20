package services.interfaces;

import domain.*;

import java.util.List;

public interface ProjectService {
    Project addProject(User client , String projectName , List<Material> materials , List<Labor> labors , double TVA , double profitMargin );
    void showProject(Project project);

    List<Project> getProjectsOfUser(User user);
}
