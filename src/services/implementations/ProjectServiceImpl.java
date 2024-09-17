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

import java.util.List;

public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository projectRepository = new ProjectRepository();
    private MaterialRepository materialRepository = new MaterialRepository();
    private LaborRepository laborRepository = new LaborRepository();


    @Override
    public Project addProject(User client, String projectName, List<Material> materials, List<Labor> labors, double TVA, double profitMargin) {
        double laborsTotalCost = labors.stream().mapToDouble(labor -> labor.getCostPerHour() * labor.getHoursOfWork() * labor.getQualityCoefficient())
                .sum();
        double materialsTotalCost = materials.stream().mapToDouble(material -> (material.getQuantity() * material.getCostPerUnit() * material.getQualityCoefficient()) + material.costOfTransport())
                .sum();
        double costWithoutProfit = (materialsTotalCost + laborsTotalCost) * TVA;
        double costWithProfit = costWithoutProfit + profitMargin;
        Project project = projectRepository.addProject(client.getId(), projectName, profitMargin, costWithProfit, Status.InProgress);
        project.setUser(client);
        project.setLabors(labors);
        project.setMaterials(materials);
        project.getLabors().forEach(labor -> labor.setProject(project));
        project.getMaterials().forEach(material -> material.setProject(project));
        project.getLabors().forEach(labor -> labor.setTVA(TVA));
        project.getMaterials().forEach(material -> material.setTVA(TVA));
        project.getLabors().forEach(labor -> laborRepository.addLabor(labor));
        project.getMaterials().forEach(material -> materialRepository.addMaterial(material));
        return project;

    }

    @Override
    public void showProject(Project project) {
        System.out.println("Project Name :" + project.getName());
        System.out.println("Client Name : " + project.getUser().getName());
        System.out.println("Client address : " + project.getUser().getAddress() + "\n");
        System.out.println("----Cost Details---- \n");
        System.out.println("1-Materials : ");
        project.getMaterials().forEach(material -> System.out.println("-" + material.getName() + ": " + ((material.getQuantity() * material.getCostPerUnit() * material.getQualityCoefficient()) + material.costOfTransport()) + "(quantity : " + material.getQuantity() + ", cost per unit : " + material.getCostPerUnit() + " , quality : " + material + " , cost of transport : " + material.costOfTransport()));
        System.out.print("Cost total of materials without tva is : ");
        double materialsTotalCostWithoutTVA = project.getMaterials().stream().mapToDouble(material -> (((material.getQuantity() * material.getCostPerUnit() * material.getQualityCoefficient()) + material.costOfTransport())))
                .sum();
        System.out.println(materialsTotalCostWithoutTVA);
        System.out.print("Cost total with tva (%" + project.getMaterials().getFirst().getTVA() * 100 + "):  ");
        double materialsTotalCostWithTVA = materialsTotalCostWithoutTVA + (materialsTotalCostWithoutTVA * project.getMaterials().getFirst().getTVA());
        System.out.println(materialsTotalCostWithTVA);

        System.out.println("\n\n");

        System.out.println("2-Labors : ");
        project.getLabors().forEach(labor -> System.out.println("-" + labor.getName() + " : " + labor.getCostPerHour() * labor.getHoursOfWork() * labor.getQualityCoefficient() + "(cost per hour : " + labor.getCostPerHour() + "$/h , Worked hours : " + labor.getHoursOfWork() + " , productivity: " + labor.getQualityCoefficient() + ")"));
        System.out.print("Cost total of labors without tva is : ");
        double laborsTotalCostWithoutTVA = project.getLabors().stream().mapToDouble(labor -> labor.getCostPerHour() * labor.getHoursOfWork() * labor.getQualityCoefficient())
                .sum();
        System.out.println(laborsTotalCostWithoutTVA);
        System.out.print("Cost total with tva (%" + project.getLabors().getFirst().getTVA() * 100 + "):  ");
        double laborsTotalCostWithTVA = laborsTotalCostWithoutTVA + (laborsTotalCostWithoutTVA * project.getLabors().getFirst().getTVA());
        System.out.println(laborsTotalCostWithTVA);

        System.out.println("\n\n");
        System.out.print("cost total without profit margin : ");
        System.out.println(project.getCostTotal() - (project.getCostTotal() * project.getProfitMargin()));
        System.out.println("\n cost total of profit margin : " + project.getCostTotal() * project.getProfitMargin());
        System.out.println("\n cost total of project is : " + project.getCostTotal());

    }
}
