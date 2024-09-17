import Auth.Login;
import Auth.Register;
import domain.Labor;
import domain.Material;
import domain.User;
import domain.enums.Role;
import services.implementations.LaborServiceImpl;
import services.implementations.MaterialServiceImpl;
import services.implementations.ProjectServiceImpl;
import services.implementations.UserServiceImpl;
import services.interfaces.LaborService;
import services.interfaces.MaterialService;
import services.interfaces.ProjectService;
import services.interfaces.UserService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static User currentUser;
    private static UserService userService = new UserServiceImpl();
    private static MaterialService materialService = new MaterialServiceImpl();
    private static LaborService laborService = new LaborServiceImpl();
    private static ProjectService projectService = new ProjectServiceImpl();





    public static void main(String[] args) throws SQLException {
        while (currentUser == null) {
            notAuthenticatedMenu();
        }
        authenticatedMenu();
    }

    public static void notAuthenticatedMenu() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("""
                Welcome to Costify , start a new journey with us
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
                notAuthenticatedMenu();
                break;
        }
    }

    public static void loginLMenu() throws SQLException {
        Scanner input = new Scanner(System.in);
        Scanner inputInt = new Scanner(System.in);

        System.out.println("Enter Your name");
        String name = input.nextLine();
        System.out.println("Enter Your Password");
        String password = input.nextLine();
        User user = Login.isUserExist(name, password);
        if (user == null) {
            System.out.println("Do you want to try again or exit | 1-try again 2-exit");
            int choice = inputInt.nextInt();
            if (choice == 1) {
                loginLMenu();
            } else {
                notAuthenticatedMenu();
            }
        } else {
            currentUser = user;
            authenticatedMenu();
        }
    }

    public static User createUser() throws SQLException {
        Scanner input = new Scanner(System.in);
        Scanner inputInt = new Scanner(System.in);

        System.out.println("Welcome aboard");
        System.out.print("Your Name :  ");
        String name = input.nextLine();
        System.out.print("Your Password :  ");
        String password = input.nextLine();
        System.out.print("Your Address :  ");
        String address = input.nextLine();
        System.out.print("Your phone  ex : +212 xxx-xx-xx-xx:  ");
        String phone = input.nextLine();
        boolean isProfessional;
        System.out.print("are you an individual or a company 1-individual anyNumber-company:  "); //need check what professional means
        int option = inputInt.nextInt();
        isProfessional = option != 1;
        return Register.createUser(name, password, address, phone, isProfessional);
    }

    public static void registerMenu() throws SQLException {
        Scanner inputInt = new Scanner(System.in);
        if (createUser() == null) {
            System.out.println("Do you want to 1-try again or anyNumber-exit");
            int choice = inputInt.nextInt();
            if (choice == 1) {
                registerMenu();
            } else {
                notAuthenticatedMenu();
            }
        } else {
            currentUser = createUser();
            authenticatedMenu();
        }
    }

    public static void authenticatedMenu() throws SQLException {
        if (currentUser.getRole().equals(Role.Admin)) {
            adminMenu();
        } else {
            clientMenu();
        }
    }

    public static void adminMenu() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome Back , " + currentUser.getName());
        System.out.println("""
                1-My personal info
                2-Assign Project to a client
                3-Change user role
                4-Logout
                """);
        int option = input.nextInt();
        if (option == 2) {
            assignProject();
        } else if (option == 4) {
            currentUser = null;
            notAuthenticatedMenu();
        }
    }

    public static void clientMenu() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome Back , " + currentUser.getName());
        System.out.println("""
                1-My personal info
                2-Edit Personal info
                3-Check My Estimates
                4-View My personal Projects
                5-Logout
                6-Delete Account
                """);
        int option = input.nextInt();
        if (option == 5) {
            currentUser = null;
            notAuthenticatedMenu();
        }
    }

    public static void assignProject() throws SQLException {
        Scanner inputInt = new Scanner(System.in);
        Scanner input = new Scanner(System.in);

        System.out.println("1-Create Client \n 2-Add an existing Client");
        int option = inputInt.nextInt();
        if (option == 1) {
            createUser();
        } else {
            System.out.println("Enter client Name");
            String name = input.nextLine();
            User user = userService.getUser(name);
            if(user ==null){
                System.out.println("Try again");
                assignProject();
            }else{
                System.out.println("Name : "+user.getName());
                System.out.println("Address: "+user.getAddress());
                System.out.println("Phone: "+user.getPhone());
                System.out.println("Do you want to continue with this client (y/n)");
                String choice = input.nextLine();
                if(choice.equals("y")){
                     createProject(user);
                }else{
                    adminMenu();
                }
            }
        }

    }

    public static void createProject(User client){
        Scanner input = new Scanner(System.in);
        Scanner inputDouble = new Scanner(System.in);
        System.out.println("----General Information----\n");
        System.out.print("Enter Project Name : ");
        String name = input.nextLine();
        System.out.println("Enter Cuisine surface (m²)");
        double surface = inputDouble.nextDouble();
        List<Material> materials = materialService.addMaterials();
        List<Labor> labors = laborService.addLabors();
        System.out.println("Do you want to apply TVA to this project ?(y/n)");
        String choice = input.nextLine();
        double TVA = 0;
        if(choice.equals("y")){
            System.out.println("Enter TVA percentage (%)");
           TVA = inputDouble.nextDouble()/100;
        }
        System.out.println("Do you want to apply profit margin to this project ?(y/n)");
         choice = input.nextLine();
        double profitMargin = 0;
        if(choice.equals("y")){
            System.out.println("Enter profit margin percentage (%)");
            profitMargin = inputDouble.nextDouble();
        }
        projectService.showProject(projectService.addProject(client , name , materials , labors , TVA , profitMargin ));

    }
}