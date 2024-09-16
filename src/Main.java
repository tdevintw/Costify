import Auth.Login;
import Auth.Register;
import domain.User;
import domain.enums.Role;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    private static User currentUser;

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

    public static void registerMenu() throws SQLException {
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
        User user = Register.createUser(name, password, address, phone, isProfessional);
        if (user == null) {
            System.out.println("Do you want to 1-try again or anyNumber-exit");
            int choice = inputInt.nextInt();
            if (choice == 1) {
                registerMenu();
            } else {
                notAuthenticatedMenu();
            }
        } else {
            currentUser = user;
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
        if (option == 4) {
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

}