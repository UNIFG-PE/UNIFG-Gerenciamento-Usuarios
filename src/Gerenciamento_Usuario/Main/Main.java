package Gerenciamento_Usuario.Main;

import Gerenciamento_Usuario.Services.AdminAUTH;
import Gerenciamento_Usuario.Services.GerenciarDAO;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/library";
        String user = "root";
        String password = "yourPassword";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Scanner scanner = new Scanner(System.in)) {

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

                        System.out.print("Do you want to update your data? (yes/no): ");
                        String updateChoice = scanner.nextLine().trim().toLowerCase();

                        if (updateChoice.equals("yes")) {
                            System.out.println("Enter new data to update:");

                            System.out.println("=================");
                            System.out.print("New name: ");
                            String newName = scanner.nextLine();

                            System.out.print("New address: ");
                            String newAddress = scanner.nextLine();

                            System.out.print("New telephone: ");
                            String newTelephone = scanner.nextLine();

                            System.out.print("New e-mail: ");
                            String newEmail = scanner.nextLine();

                            System.out.print("New birth date (YYYY-MM-DD): ");
                            String newDateOfBirth = scanner.nextLine();
                            System.out.println("=================");

                            String updateSQL = "UPDATE library  SET name = ?,  address = ? ,telephone = ?, email = ?, dateOfBirth = ? WHERE id = ?";
                            try (PreparedStatement updateStmt = connection.prepareStatement(updateSQL)) {
                                updateStmt.setString(1, newName);
                                updateStmt.setString(2, newAddress);
                                updateStmt.setString(3, newTelephone);
                                updateStmt.setString(4, newEmail);
                                updateStmt.setString(5, newDateOfBirth);


                                int rowsUpdated = updateStmt.executeUpdate();
                                if (rowsUpdated > 0) {
                                    System.out.println("User updated successfully!");
                                } else {
                                    System.out.println("Failed to update user.");
                                }
                            } catch (SQLException e) {
                                System.out.println("Error to update user: " + e.getMessage());
                            }
                        }

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

                                System.out.println("Listing all registered users:");

                                String listAllUsersSQL = "SELECT u.*, ut.type_name FROM users u JOIN user_type ut ON u.user_type_id = ut.id";

                                try (PreparedStatement listStmt = connection.prepareStatement(listAllUsersSQL);
                                     ResultSet allUsers = listStmt.executeQuery()) {

                                    while (allUsers.next()) {
                                        System.out.println("-------------------------------");
                                        System.out.println("ID: " + allUsers.getInt("id"));
                                        System.out.println("Name: " + allUsers.getString("username"));
                                        System.out.println("Address: " + allUsers.getString("address"));
                                        System.out.println("Telephone: " + allUsers.getString("telephone"));
                                        System.out.println("E-mail: " + allUsers.getString("email"));
                                        System.out.println("Birth date: " + allUsers.getDate("dateOfBirth"));
                                        System.out.println("User Type: " + allUsers.getString("type_name"));
                                    }
                                } catch (SQLException e) {
                                    System.out.println("Error retrieving all users: " + e.getMessage());
                                }
                                break;

                            case "2":

                                System.out.print("Insert User ID: ");
                                int idSearch = scanner.nextInt();
                                scanner.nextLine();

                                String selectByID = "SELECT * FROM users WHERE id = ?";
                                try (PreparedStatement stmt = connection.prepareStatement(selectByID)) {
                                    stmt.setInt(1, idSearch);
                                    try (ResultSet rs = stmt.executeQuery()) {
                                        if (rs.next()) {
                                            System.out.println("Found User: ");
                                            System.out.println("ID: " + rs.getInt("id"));
                                            System.out.println("Name: " + rs.getString("username"));
                                            System.out.println("E-mail: " + rs.getString("email"));
                                            System.out.println("Phone number: " + rs.getString("telephone"));
                                            System.out.println("Birth date: " + rs.getString("dateOfBirth"));
                                            System.out.println("Permission ID: " + rs.getInt("user_type_id"));
                                        }
                                        else {
                                            System.out.println("User with the ID " + idSearch + " was not found.");
                                        }
                                    }
                                }
                                break;

                            default:
                                System.out.println("Not available option, please try again.");

                                break;
                        }

                        break;

                    case "4":

                        GerenciarDAO Delete = new GerenciarDAO(connection, scanner);
                        Delete.deleteUser();
                        break;


                    case "0":
                        running = false;
                        System.out.println("Shutting down...");
                        break;

                    default:
                        System.out.println("Invalid option, please try again.");
                }
            }





        }  catch (SQLException e) {
            e.printStackTrace();
        }
    }
}