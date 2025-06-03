package Gerenciamento_Usuario.Main;

import Gerenciamento_Usuario.Services.AdminAUTH;
import Gerenciamento_Usuario.Services.GerenciarDAO;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/library";
        String user = "root";
        String password = "1234";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Scanner scanner = new Scanner(System.in)) {
            GerenciarDAO DAO = new GerenciarDAO(connection, scanner);

            boolean running = true;

            while (running) {
                System.out.println("====== MENU ======");
                System.out.println("1 - Register user");
                System.out.println("2 - Edit user");
                System.out.println("3 - Show user by ID");
                System.out.println("4 - Delete user");
                System.out.println("0 - Exit");
                System.out.println("=================");
                System.out.print("Choose an option: ");


                String option = scanner.nextLine();

                switch (option) {
                    case "1":
                        System.out.print("Enter your name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter your address: ");
                        String address = scanner.nextLine();
                        System.out.print("Enter your telephone: ");
                        String telephone = scanner.nextLine();
                        System.out.print("Enter your e-mail: ");
                        String email = scanner.nextLine();
                        System.out.print("Enter your birth date (YYYY-MM-DD): ");
                        String dateOfBirth = scanner.nextLine();


                        String insertSQL = "INSERT INTO users (username, address, telephone, email, dateOfBirth, user_type_id) VALUES (?, ?, ?, ?, ?, ?)";
                        try (PreparedStatement insertStmt = connection.prepareStatement(insertSQL)) {
                            insertStmt.setString(1, name);
                            insertStmt.setString(2, address);
                            insertStmt.setString(3, telephone);
                            insertStmt.setString(4, email);
                            insertStmt.setString(5, dateOfBirth);
                            insertStmt.setInt(6, 3);
                            insertStmt.executeUpdate();
                            System.out.println("Success for user creation!");
                        } catch (SQLException e) {
                            System.out.println("Error to register user: " + e.getMessage());
                        }
                        break;

                    case "2":
                        DAO.updateUser();
                        break;

                    case "3":

                        System.out.print("Enter your administrator e-mail: ");
                        String adminEmail = scanner.nextLine();

                        AdminAUTH adminAUTH = new AdminAUTH(connection);
                        if (!adminAUTH.isAdmin(adminEmail)) {
                            System.out.println("Access denied. Only administrators can delete users.");
                            break;
                        }

                        System.out.println("=================");
                        System.out.println("1 - Show all data");
                        System.out.println("2 - Show specific data");
                        System.out.println("=================");
                        System.out.print("Choose an option: ");

                        String readOption = scanner.nextLine();

                        switch (readOption) {

                            case "1":
                                DAO.listAllUsers();
                                break;

                            case "2":
                                DAO.readUserById();
                                break;

                            default:
                                System.out.println("Not available option, please try again.");

                                break;
                        }

                        break;

                    case "4":

                        String optionDelete;

                        System.out.println("=================");
                        System.out.println("1 - Delete specific user");
                        System.out.println("2 - Delete all data (WARNING, THIS WILL DELETE THE WHOLE DATA FROM THE DATABASE!)");
                        System.out.println("=================");
                        System.out.print("Choose an option: ");
                        optionDelete = scanner.nextLine();

                        switch(optionDelete) {

                            case "1":
                                DAO.deleteUser();
                                break;

                            case "2":
                                DAO.deleteAllUsers();
                                break;
                        }


                    case "0":
                        running = false;
                        System.out.println("Shutting down...");
                        break;

                    default:
                        System.out.println("Invalid option, please try again.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving user: " + e.getMessage());
        }
    }
}