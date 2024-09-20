import Auth.*;
import domain.*;
import domain.enums.Role;
import domain.enums.Status;
import services.implementations.*;
import services.interfaces.*;

import java.time.format.DateTimeFormatter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static User currentUser;
    private static UserService userService = new UserServiceImpl();
    private static MaterialService materialService = new MaterialServiceImpl();
    private static LaborService laborService = new LaborServiceImpl();
    private static ProjectService projectService = new ProjectServiceImpl();
    private static EstimateService estimateService = new EstimateServiceImpl();
    private static Scanner input = new Scanner(System.in);
    private static Scanner inputInt = new Scanner(System.in);
    private static Scanner inputDouble = new Scanner(System.in);

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
            System.out.println("Enter Your ***Password*** :");
            String password = input.next();
            User user = Login.isUserExist(name, password);
            if (user == null) {
                System.out.println("Do you want to try again or exit | 1-Exit other-Try Again");
                int choice = inputInt.nextInt();
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
        System.out.println("***Password*** :  ");
        String password = input.next();
        System.out.print("***Address*** :  ");
        String address = input.next();
        System.out.print("***phone*** (+212 xxx-xx-xx-xx) :  ");
        String phone = input.next();
        boolean isProfessional;
        System.out.print("An  ***Individual*** or a ***Company*** 1-individual other-company :  ");
        int option = inputInt.nextInt();
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
            int choice = inputInt.nextInt();
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
                    4-Logout
                    """);
            option = inputInt.nextInt();
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
        int option = inputInt.nextInt();
        switch (option) {
            case 1:
                System.out.println("Enter Your new Name");
                String newName = input.next();
                currentUser.setName(newName);
                break;
            case 2:
                System.out.println("Enter Your new Password");
                String newPassword = input.next();
                currentUser.setPassword(newPassword);

                break;
            case 3:
                System.out.println("Enter Your new Address");
                String newAddress = input.next();
                currentUser.setAddress(newAddress);
                break;
            case 4:
                System.out.println("Enter Your new Phone");
                String newPhone = input.next();
                currentUser.setPhone(newPhone);
                break;
            case 5:
                System.out.println("Enter Your new Professional Type \n 1-Individual \n 2-Company");
                int choice = inputInt.nextInt();
                boolean isProfessional = choice != 1;
                currentUser.setProfessional(isProfessional);
                break;
        }
        userService.update(currentUser);
    }

    ///IV-III/ Delete Account
    public static void deleteAccount() throws SQLException {
        System.out.println("Are you sure you want to delete your account");
        String option = input.next();
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

    public static void assignProjectToAClient() {

    }

    //VI-Client Section

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

    public static void myEstimates() {
        List<Estimate> estimates = estimateService.getEstimatesOfUser(currentUser);

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
    }


//    public static void clientEstimate(User client) {
//        System.out.println("---------My Estimate : ----");
//        client.getProjects().stream().map(project -> project.getEstimates()).flatMap(list -> list.stream())
//                .forEach(estimate -> System.out.println("id : " + estimate.getId() + " , cost total :" + estimate.getCostTotal() + " created at : " + estimate.getCreationDate() + " validated at : " + estimate.getValidatedAt() + " status : " + estimate.isAccepted()));
//    }
//
//    public static void assignProject() throws SQLException {
//        Scanner inputInt = new Scanner(System.in);
//        Scanner input = new Scanner(System.in);
//
//        System.out.println("1-Create Client \n 2-Add an existing Client");
//        int option = inputInt.nextInt();
//        if (option == 1) {
//            createUser();
//        } else {
//            System.out.println("Enter client Name");
//            String name = input.next();
//            User user = userService.getUser(name);
//            if (user == null) {
//                System.out.println("Try again");
//                assignProject();
//            } else {
//                System.out.println("Name : " + user.getName());
//                System.out.println("Address: " + user.getAddress());
//                System.out.println("Phone: " + user.getPhone());
//                System.out.println("Do you want to continue with this client (y/n)");
//                String choice = input.next();
//                if (choice.equals("y")) {
//                    createProject(user);
//                } else {
//                    adminMenu();
//                }
//            }
//        }
//
//    }
//
//    public static void createProject(User client) {
//        Scanner input = new Scanner(System.in);
//        Scanner inputDouble = new Scanner(System.in);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd ");
//        System.out.println("----General Information----\n");
//        System.out.print("Enter Project Name : ");
//        String name = input.next();
//        System.out.println("Enter Cuisine surface (m²)");
//        double surface = inputDouble.nextDouble();
//        List<Material> materials = materialService.addMaterials();
//        List<Labor> labors = laborService.addLabors();
//        System.out.println("Do you want to apply TVA to this project ?(y/n)");
//        String choice = input.next();
//        double TVA = 0;
//        if (choice.equals("y")) {
//            System.out.println("Enter TVA percentage (%)");
//            TVA = inputDouble.nextDouble() / 100;
//        }
//        System.out.println("Do you want to apply profit margin to this project ?(y/n)");
//        choice = input.next();
//        double profitMargin = 0;
//        if (choice.equals("y")) {
//            System.out.println("Enter profit margin percentage (%)");
//            profitMargin = inputDouble.nextDouble();
//        }
//        Project project = projectService.addProject(client, name, materials, labors, TVA, profitMargin);
//        projectService.showProject(project);
//        System.out.println("do you want to save the estimate for this project");
//        String option = input.next();
//        if (option.equals("y")) {
//            System.out.println("Enter creation date of estimate");
//            String startDate = input.next();
//            System.out.println("Enter validation date of estimate");
//            String validatedDate = input.next();
//            estimateService.addEstimate(project, LocalDate.parse(startDate, formatter), LocalDate.parse(validatedDate, formatter));
//
//        }
//    }
}