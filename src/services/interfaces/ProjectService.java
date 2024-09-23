package services.interfaces;

import domain.*;

import java.util.List;

public interface ProjectService {

    Project addProject(User client, String projectName , double profitMargin , double costTotal );

    List<Project> getProjectsOfUser(User user);

    Project acceptProject(Project project);

    Project refuseProject(Project project);


}
