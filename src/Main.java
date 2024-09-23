import Auth.*;
import domain.*;
import domain.enums.Role;
import domain.enums.Status;
import services.implementations.*;
import services.interfaces.*;

import java.time.format.DateTimeFormatter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    private static User currentUser;
    private static UserService userService = new UserServiceImpl();
    private static MaterialService materialService = new MaterialServiceImpl();
    private static LaborService laborService = new LaborServiceImpl();
    private static ProjectService projectService = new ProjectServiceImpl();
    private static EstimateService estimateService = new EstimateServiceImpl();
    private static Scanner input = new Scanner(System.in);


    //  I-Adding  A logo section to enhance the design of the console application.
    public static void logo() {
        System.out.println("""
                            _.-------._
                         _-'_.------._ `-_
                       _- _-          `-_/
                      -  -  Costify
                  ___/  /______________
                 /___  .______________/
                  ___| |_____________
                 /___  .____________/          \s
                     \\  \\
                      -_ -_             /|
                        -_ -._        _- |
                          -._ `------'_./
                               `-------'
                \s""");
    }

    // II-the entry point of our application check of user authentication it's like a session checker.
    public static void main(String[] args) throws SQLException {
        while (currentUser == null) {
            notAuthenticatedMenu();
        }
        checkRoleToRedirectToMenu();
    }

    //III-All the 6 methods below are for authentication purposes they are used to link the user with the database.
    public static void notAuthenticatedMenu() throws SQLException {
        logo();
        System.out.println("""
                1-Login
                2-Register
                """);
        int option = input.nextInt();
        input.nextLine();
        switch (option) {
            case 1:
                loginLMenu();
                break;
            case 2:
                registerMenu();
                break;
            default:
                System.out.println("Enter a valid option");
                break;
        }
    }

    ///III-I/method to link unauthenticated users with their personal data , which make them able to track and mange freely their accounts.
    public static void loginLMenu() throws SQLException {
        logo();
        while (currentUser == null) {
            System.out.println("Enter Your ***Name*** :");
            String name = input.next();
            input.nextLine();
            System.out.println("Enter Your ***Password*** :");
            String password = input.next();
            User user = Login.isUserExist(name, password);
            if (user == null) {
                System.out.println("Do you want to try again or exit | 1-Exit other-Try Again");
                int choice = input.nextInt();
                input.nextLine();
                if (choice == 2) {
                    notAuthenticatedMenu();
                }
            } else {
                currentUser = user;
            }
        }
        checkRoleToRedirectToMenu();
    }

    ///III-II/instead of using duplicate fragments of code on both register and creating user (in Admin section) , I call this method to optimize code .
    public static User createUser() throws SQLException {

        System.out.println("***Name*** :  ");
        String name = input.next();
        input.nextLine();
        System.out.println("***Password*** :  ");
        String password = input.next();
        input.nextLine();
        System.out.print("***Address*** :  ");
        String address = input.next();
        input.nextLine();
        System.out.print("***phone*** (+212 xxx-xx-xx-xx) :  ");
        String phone = input.next();
        input.nextLine();
        boolean isProfessional;
        System.out.print("An  ***Individual*** or a ***Company*** 1-individual other-company :  ");
        int option = input.nextInt();
        input.nextLine();
        isProfessional = option != 1;
        return Register.createUser(name, password, address, phone, isProfessional);
    }

    /*III-III/ create accounts for guests that don't have any account Yet
            Names are unique so when creating a user the service must handle the logic .
     */
    public static void registerMenu() throws SQLException {
        User user;
        do {
            user = createUser();
            if (user == null) {
                System.out.println("Do you want to 1-Exit or other-Try Again");
            }
            int choice = input.nextInt();
            input.nextLine();
            if (choice == 1) {
                notAuthenticatedMenu();
            }
        }
        while (user == null);
        currentUser = user;
        checkRoleToRedirectToMenu();
    }

    ///III-IV/redirect User to a specific interface based on their role
    public static void checkRoleToRedirectToMenu() throws SQLException {
        if (currentUser.getRole().equals(Role.Admin)) {
            adminMenu();
        } else {
            clientMenu();
        }
    }

    ///III-V/ Setting currentUser to null to separate user and data
    public static void logout() throws SQLException {
        currentUser = null;
        notAuthenticatedMenu();
    }

    //IV -Separating the menu of client and admin since each on of them perform specific roles .
    public static void adminMenu() throws SQLException {
        int option;
        do {
            logo();
            System.out.println("Welcome Back , " + currentUser.getName());
            System.out.println("""
                    1-Manage Personal Information
                    2-Manage Projects
                    3-Assign Project To A Client
                    4-Manage Users
                    5-Logout
                    """);
            option = input.nextInt();
            input.nextLine();
            switch (option) {
                case 1:
                    managePersonalInformation();
                    break;
                case 2:
                    manageProjects();
                    break;
                case 3:
                    assignProjectToAClient();
                    break;
                case 4:
                    manageUsers();
                    break;
                case 5:
                    logout();
                    break;
            }
        } while (option != 5 && option != 4 && option != 3 && option != 2 && option != 1);

    }

    public static void clientMenu() throws SQLException {
        int option;
        do {
            logo();
            System.out.println("Welcome Back , " + currentUser.getName());
            System.out.println("""
                    1-Manage Personal Information
                    2-My Projects
                    3-My Estimates
                    4-Logout
                    """);
            option = input.nextInt();
            input.nextLine();
            switch (option) {
                case 1:
                    managePersonalInformation();
                    break;
                case 2:
                    myProjects();
                    break;
                case 3:
                    myEstimates();
                    break;
                case 4:
                    logout();
                    break;
            }
        } while (option != 4 && option != 3 && option != 2 && option != 1);

    }

    //IV- Client and admin will both have the same prototype of Managing their personal Information (Updating , deleting account)

    public static void managePersonalInformation() throws SQLException {
        int option;
        do {
            logo();
            System.out.println("""
                    1-See Personal Information
                    2-Update My Profile
                    3-Delete Account
                    """);
            option = input.nextInt();
            input.nextLine();

            switch (option) {
                case 1:
                    System.out.println("***Name*** : " + currentUser.getName());
                    System.out.println("***Address*** : " + currentUser.getAddress());
                    System.out.println("***Phone*** : " + currentUser.getPhone());
                    String Type = currentUser.isProfessional() ? "A : Company" : "An : Individual";
                    System.out.println("You are   ***" + Type + "***");
                    break;
                case 2:
                    updateMyProfile();
                    break;
                case 3:
                    deleteAccount();
                    break;

            }
        } while (option != 3 && option != 2 && option != 1);
        if (currentUser.getRole().equals(Role.Admin)) {
            adminMenu();
        } else {
            clientMenu();
        }
    }

    ///IV-II/ Update Profile Info | both admin and client will have the same functionalities

    public static void updateMyProfile() {
        System.out.println("""
                ***What do you want to update***
                1-Name 
                2-Password
                3-Address
                4-Phone
                5-Professional Type
                """);
        int option = input.nextInt();
        input.nextLine();

        switch (option) {
            case 1:
                System.out.println("Enter Your new Name");
                String newName = input.nextLine();
                input.nextLine();
                currentUser.setName(newName);
                break;
            case 2:
                System.out.println("Enter Your new Password");
                String newPassword = input.nextLine();
                input.nextLine();
                currentUser.setPassword(newPassword);

                break;
            case 3:
                System.out.println("Enter Your new Address");
                String newAddress = input.nextLine();
                input.nextLine();
                currentUser.setAddress(newAddress);
                break;
            case 4:
                System.out.println("Enter Your new Phone");
                String newPhone = input.nextLine();
                input.nextLine();
                currentUser.setPhone(newPhone);
                break;
            case 5:
                System.out.println("Enter Your new Professional Type \n 1-Individual \n 2-Company");
                int choice = input.nextInt();
                input.nextLine();
                boolean isProfessional = choice != 1;
                currentUser.setProfessional(isProfessional);
                break;
        }
        userService.update(currentUser);
    }

    ///IV-III/ Delete Account
    public static void deleteAccount() throws SQLException {
        System.out.println("Are you sure you want to delete your account");
        String option = input.nextLine();
        input.nextLine();
        if (option.equals("y")) {
            userService.deleteAccount(currentUser);
        }
        if (currentUser.getRole().equals(Role.Admin)) {
            adminMenu();
        } else {
            clientMenu();
        }
    }

    //V-Admin Section


    public static void manageProjects() {

    }

    public static void addProjectToClient(User client) throws SQLException {
        System.out.println("Information of client selected : \nName" + client.getName() + "\nAddress : " + client.getAddress() + "\nPhone : " + client.getPhone());
        System.out.println("Enter Project Name");
        String projectName = input.nextLine();
        input.nextLine();
        List<Material> materials = addMaterials();
        List<Labor> labors = addLabors();
        double TVA = 0;
        double discount = 0;
        double profitMargin = 0;
        System.out.println("\n*****Calculating of cost total*****\n");
        System.out.println("Do you want to apply TVA to this Project(y/n)");
        String choice = input.nextLine();
        input.nextLine();
        if (choice.equals("y")) {
            System.out.println("Enter TVA percentage (%)");
            double percentage = input.nextDouble();
            input.nextLine();
            TVA = percentage / 100;
        }
        System.out.println("this user is " + ((client.isProfessional()) ? " a company" : " An individual ") + " Do you want to apply a discount(y/n)");
        choice = input.nextLine();
        input.nextLine();
        if (choice.equals("y")) {
            System.out.println("Enter Discount percentage (%)");
            double percentage = input.nextDouble();
            input.nextLine();
            discount = percentage / 100;
        }
        System.out.println("Do you want to apply Profit margin to this Project(y/n)");
        choice = input.nextLine();
        input.nextLine();
        if (choice.equals("y")) {
            System.out.println("Enter TVA percentage (%)");
            double percentage = input.nextDouble();
            input.nextLine();
            profitMargin = percentage / 100;
        }
        resultOfAProject(projectName, client, materials, labors, TVA, discount, profitMargin);
    }

    public static List<Material> addMaterials() {
        System.out.println("\n*****Adding of Materials*****\n");
        boolean keepAddingMaterial = false;
        List<Material> materials = new ArrayList<>();
        do {
            System.out.println("Enter the name of the material");
            String materialName = input.nextLine();
            input.nextLine();
            System.out.println("Enter the quantity of this material");
            double quantity = input.nextDouble();
            input.nextLine();
            System.out.println("Enter cost per unit of this material");
            double costPerUnit = input.nextDouble();
            input.nextLine();
            System.out.println("Enter the cost of transport for this package of material");
            double costOfTransport = input.nextDouble();
            input.nextLine();
            System.out.println("Enter the coefficient quality of this product(1.0 standard ,>1.0 good quality )");
            double quality = input.nextDouble();
            input.nextLine();
            materials.add(new Material(materialName, "Material", 0, quality, null, costPerUnit, quantity, costOfTransport)); //must set project and tva afterward
            System.out.println("Material was added \nDo you want to add another Material(y/n)");
            String option = input.nextLine();
            input.nextLine();
            keepAddingMaterial = option.equals("y");
        } while (keepAddingMaterial);

        return materials;
    }

    public static List<Labor> addLabors() {
        System.out.println("\n*****Adding of Labors*****\n");
        boolean keepAddingLabor = false;
        List<Labor> labors = new ArrayList<>();
        do {
            System.out.println("Enter the speciality of the Labor");
            String laborName = input.nextLine();
            input.nextLine();
            System.out.println("Enter cost per Hour of this Labor");
            double costPerHour = input.nextDouble();
            input.nextLine();
            System.out.println("Enter Total Hours Worked by this labor");
            double totalOfHours = input.nextDouble();
            input.nextLine();
            System.out.println("Enter the coefficient of professionalism of this Labor(1.0 standard ,>1.0 good quality )");
            double quality = input.nextDouble();
            input.nextLine();
            labors.add(new Labor(laborName, "Labor", 0, quality, null, costPerHour, totalOfHours)); //must set project and tva afterward
            System.out.println("Labor was added \nDo you want to add another Labor(y/n)");
            String option = input.nextLine();
            input.nextLine();
            keepAddingLabor = option.equals("y");
        } while (keepAddingLabor);

        return labors;
    }

    public static void resultOfAProject(String projectName, User client, List<Material> materials, List<Labor> labors, double TVA, double discount, double profitMargin) throws SQLException {
        System.out.println("\n***Result of calculations***\n");
        System.out.println("Name of the project :" + projectName);
        System.out.println("Client address :" + client.getAddress());
        System.out.println("Details of costs");
        System.out.println("1-Materials:");
        double totalForMaterials = 0;
        for (Material material : materials) {
            double costOfMaterialPackage = materialService.costTotalOfAMaterialPackage(material);
            System.out.println("-" + material.getName() + " : " + costOfMaterialPackage + "$(quantity : " + material.getQuantity() + ", cost per unit : " + material.getCostPerUnit() + "$ ,quality : " + material.getQualityCoefficient() + ",cost of transport : " + material.costOfTransport() + "$)");
            totalForMaterials += costOfMaterialPackage;
        }
        System.out.println("Total cost of Materials without TVA is :" + totalForMaterials + "$");
        System.out.println("Total cost of Materials with TVA("+TVA*100+"%)is :" + (totalForMaterials + (totalForMaterials * TVA)) + "$");

        System.out.println("\n\n2-Labors:");
        double totalForLabors = 0;
        for (Labor labor : labors) {
            double costOfLabor = laborService.costTotalOfALabor(labor);
            System.out.println("-" + labor.getName() + ":" + costOfLabor + "$(cost per hour : " + labor.getCostPerHour() + ",hours worked: " + labor.getHoursOfWork() + " ,productivity : " + labor.getQualityCoefficient());
            totalForLabors += costOfLabor;
        }
        System.out.println("Total cost of labors without TVA is :" + totalForLabors + "$");
        System.out.println("Total cost of labors with TVA("+TVA*100+"%)is :" + totalForLabors + (totalForLabors * TVA) + "$");
        double totalCostWithTVA = (totalForLabors + totalForMaterials) + ((totalForLabors + totalForMaterials) * TVA);
        System.out.println("\n\n3-Cost total before profit margin is : " + totalCostWithTVA + "$");
        double profitMarginCost = totalCostWithTVA * profitMargin;
        System.out.println("4-Profit margin(" + profitMargin * 100 + "%) : " + profitMarginCost + "$");
        double discountCost = (profitMarginCost + totalCostWithTVA) * discount;
        System.out.println("5-Discount(" + discount * 100 + "%) : " + discountCost + "$");
        double costTotal = totalCostWithTVA + profitMarginCost - discountCost;
        System.out.println("Cost total of project after all is : " + costTotal);
        System.out.println("\nDo you want to save the estimate of the project(y/n)");
        String option = input.nextLine();
        input.nextLine();
        if (option.equals("y")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            System.out.println("Enter creation date of estimate ");
            String createdAt = input.nextLine();
            input.nextLine();
            System.out.println("Validated until");
            String validatedUntilString = input.nextLine();
            input.nextLine();
            LocalDate creationDate = LocalDate.parse(createdAt, formatter);
            LocalDate validatedUntil = LocalDate.parse(validatedUntilString, formatter);
            callingServicesToInsertData(client, projectName, profitMarginCost, costTotal, labors, materials, creationDate, validatedUntil, TVA);
            System.out.println("Project and Estimate was saved");
        } else {
            System.out.println("All you previous data will be deleted...");
            adminMenu();
        }
    }

    public static void callingServicesToInsertData(User client, String projectName, double profitMargin, double costTotal, List<Labor> labors, List<Material> materials, LocalDate createdAt, LocalDate validatedUntil, double TVA) throws SQLException {
        Project project = projectService.addProject(client, projectName, profitMargin, costTotal);
        materialService.addMaterials(materials, project.getId(), TVA);
        laborService.addLabors(labors, project.getId(), TVA);
        Estimate estimate = estimateService.addEstimate(project.getId(), costTotal, createdAt, validatedUntil);
        if (estimate != null) {
            System.out.println("estimate was added");
        }
        adminMenu();
    }

    public static void assignProjectToAClient() throws SQLException {
        System.out.println("Enter Client Name");
        String name = input.nextLine();
        input.nextLine();
        User user = userService.getUser(name);
        if (user == null) {
            System.out.println("Can't Found the user");
            System.out.println("Do you want to add this client");
            String option = input.nextLine();
            input.nextLine();
            if (option.equals("y")) {
                user = addUser(name);
            }
        } else if (user.getRole().equals(Role.Admin)) {
            System.out.println("Can't assign a a project to an admin");
        }
        addProjectToClient(user);

        adminMenu();
    }

    public static User addUser(String oldName) throws SQLException {
        System.out.println("Do you want to keep " + oldName + " as the name of the client (y/n)");
        String option = input.nextLine();
        input.nextLine();
        String name;
        if (option.equals("y")) {
            name = oldName;
        } else {
            System.out.println("Enter the Name");
            name = input.nextLine();
            input.nextLine();
        }
        System.out.println("Enter password");
        String password = input.nextLine();
        input.nextLine();
        System.out.println("Enter Address");
        String address = input.nextLine();
        input.nextLine();
        System.out.println("Enter Phone(+212 xxx-xx-xx-xx)");
        String phone = input.nextLine();
        input.nextLine();
        System.out.println("is this client professional (y/n)");
        String isProfessional1 = input.nextLine();
        input.nextLine();
        boolean isProfessional = isProfessional1.equals("y");
        User newClient = Register.createUser(name, password, address, phone, isProfessional);
        if (newClient == null) {
            System.out.println("Can't add user");
            adminMenu();
        }
        return newClient;
    }

    public static void manageUsers() throws SQLException {
        List<User> users = userService.getAll();
        System.out.println("""
                +-------+------------------+------------------+----------------------+------------------+------------------+
                |  ID   |      Name        |      Address     |         Phone        |  Is Professional |       Role       |
                +-------+------------------+------------------+----------------------+------------------+------------------+""");

        for (User user : users) { // Assuming you have a method to get user details
            System.out.printf("| %-5s | %-16s | %-16s | %-20s | %-16s | %-16s |%n",
                    user.getId(),
                    user.getName(),
                    user.getAddress(),
                    user.getPhone(),
                    user.isProfessional() ? "Yes" : "No",
                    user.getRole()
            );
        }
        System.out.println("+-------+------------------+------------------+----------------------+------------------+------------------+");

        System.out.println("Do you want to change the role of a user(y/n)");
        String choice = input.nextLine();
        input.nextLine();
        if (choice.equals("y")) {
            System.out.println("Enter the id of the user");
            int id = input.nextInt();
            input.nextLine();
            if (id == currentUser.getId()) {
                System.out.println("You can't change your role");
            } else {
                Optional<User> user = users.stream().filter(user1 -> user1.getId() == id).findFirst();
                if (user.isEmpty()) {
                    System.out.println("User not found ");
                } else {
                    changeRole(user.get());
                }
            }
        }
        adminMenu();
    }

    public static void changeRole(User user) throws SQLException {
        System.out.println("Do you really want to change the role of " + user.getName() + " to " + (user.getRole().equals(Role.Admin) ? "Client" : "Admin") + " (y/n)");
        String option = input.nextLine();
        input.nextLine();
        if (option.equals("y")) {
            if (user.getRole().equals(Role.Admin)) {
                user.setRole(Role.Client);
            } else {
                user.setRole(Role.Admin);
            }
            userService.update(user);
        }
        adminMenu();
    }


    //VI-Client Section
    //if the user already fetched estimates from database, it means that projects were already assigned to the user, that's why i check the presence of projects using getProjects().
    public static void myProjects() {
        List<Project> projects;
        if (currentUser.getProjects() == null) {
            projects = projectService.getProjectsOfUser(currentUser);
            currentUser.setProjects(projects);
        } else {
            projects = currentUser.getProjects();
        }

        System.out.println("""
                +----------------------------------+---------------+------------------------+----------------+
                |       Project Name               |  Cost Total   |      Profit Margin     |     Status     |
                +----------------------------------+---------------+------------------------+----------------+""");

        for (Project project : projects) {
            System.out.printf("| %-32s | %-13s | %-22s | %-14s |%n",
                    project.getName(),
                    project.getCostTotal() + "$",
                    project.getProfitMargin() + "$",
                    project.getStatus());
        }

        System.out.println("+----------------------------------+---------------+------------------------+----------------+");

    }

    //if the user choose to view estimates before projects , that means both the projects and the estimates will be fetched from database and assigned to user , i also use distinct to select only distinct projects since a project can have many estimates which may result assigning duplicated projects to the user.
    public static void myEstimates() throws SQLException {
        List<Estimate> estimates = estimateService.getEstimatesOfUser(currentUser);
        currentUser.setProjects(estimates.stream().map(Estimate::getProject).distinct().collect(Collectors.toList()));
        System.out.println("""
                +----------------------------------+---------------+------------------------+-------------------------+-------------+
                |       Project Name               |  Cost Total   |      Created At        |       Validated Until   |   Accepted  |
                +----------------------------------+---------------+------------------------+-------------------------+-------------+""");

        for (Estimate estimate : estimates) {
            System.out.printf("| %-32s | %-13s | %-22s | %-23s | %-11s |%n",
                    estimate.getProject().getName(),
                    estimate.getCostTotal() + "$",
                    estimate.getCreationDate(),
                    estimate.getValidatedAt(),
                    estimate.isAccepted() ? "Yes" : "No");
        }

        System.out.println("+----------------------------------+---------------+------------------------+-------------------------+-------------+");
        System.out.println("Do you want to manage your estimates(y/n)");
        String choice = input.nextLine();
        input.nextLine();
        if (choice.equals("y")) {
            manageEstimate(estimates);
        } else {
            clientMenu();
        }
    }

    //This method is the one that link the client and the admin since this method affect the status of the project which may lead to be accepted or refused | the client can only accept the estimates within the range of created at and validated until .
    public static void manageEstimate(List<Estimate> estimates) throws SQLException {

        System.out.println("Hi " + currentUser.getName() + " Here you can accept or refuse the estimates of your projects \n You can  accept an estimate  just within the range | Once you accept an estimate the project will be launched");
        System.out.println("""

                +------------------+----------------------------------+---------------+------------------------+-------------------------+
                |   Estimate ID    |       Project Name               |  Cost Total   |      Created At        |       Validated Until   |
                +------------------+----------------------------------+---------------+------------------------+-------------------------+""");

        for (Estimate estimate : estimateService.validEsimates(estimates)) {
            System.out.printf("| %-16s | %-32s | %-13s | %-22s | %-23s  |%n",
                    estimate.getId(),
                    estimate.getProject().getName(),
                    estimate.getCostTotal() + "$",
                    estimate.getCreationDate(),
                    estimate.getValidatedAt()
            );
        }

        System.out.println(
                "+------------------+----------------------------------+---------------+------------------------+-------------------------+");

        System.out.println("Enter the id of the estimate you want to accept or refuse");
        int choice = input.nextInt();
        input.nextLine();
        Optional<Estimate> choosedEstimate = estimates.stream().filter(estimate -> estimate.getId() == choice).findFirst();
        if (choosedEstimate.isEmpty()) {
            System.out.println("Can't found estimate");
            clientMenu();
        }
        System.out.println("Do you want to accept or refuse this Estimate(1-accept 2- refuse)");
        int option = input.nextInt();
        input.nextLine();
        switch (option) {
            case 1:
                acceptAnEstimate(choosedEstimate);
                break;
            case 2:
                refuseAnEstimate(choosedEstimate);
                break;
            default:
                System.out.println("Enter a valid option");
        }
        clientMenu();
    }

    public static void acceptAnEstimate(Optional<Estimate> choosedEstimate) throws SQLException {
        System.out.println("Do you really want to accept this estimate(y/n)");
        String option = input.nextLine();
        input.nextLine();
        if (option.equals("n")) {
            clientMenu();
        } else {
            if (estimateService.acceptEstimate(choosedEstimate.get()) != null && projectService.acceptProject(choosedEstimate.get().getProject()) != null) {
                //need to update the project status of the current user and also the acceptance of the estimate so if i want the getProject adn getEstimate it will be updated
                currentUser.getProjects().stream().filter(project -> project.getId() == choosedEstimate.get().getProject().getId()).forEach(project ->
                {
                    project.setStatus(Status.Completed);
                    project.getEstimates().stream()
                            .filter(estimate -> estimate.getId() == choosedEstimate.get().getId())
                            .forEach(estimate -> estimate.setAccepted(true));
                });
            }
        }
    }

    public static void refuseAnEstimate(Optional<Estimate> choosedEstimate) throws SQLException {
        System.out.println("Do you really want to refuse this estimate(y/n)");
        String choice = input.nextLine();
        input.nextLine();
        if (choice.equals("n")) {
            clientMenu();
        } else {
            if (projectService.refuseProject(choosedEstimate.get().getProject()) != null) {
                //need to update the project status of the current user so if i want the getProject it will be updated
                currentUser.getProjects().stream().filter(project -> project.getId() == choosedEstimate.get().getProject().getId()).forEach(project -> project.setStatus(Status.Canceled));
            }
        }
    }

}