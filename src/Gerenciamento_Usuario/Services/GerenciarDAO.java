package Gerenciamento_Usuario.Services;

import java.sql.*;
import java.util.Scanner;

public class GerenciarDAO {

    private final Connection connection;
    private final Scanner scanner;
    private final AdminAUTH adminAUTH;

    public GerenciarDAO(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
        this.adminAUTH = new AdminAUTH(connection);
    }

    public void listAllUsers() {
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
    }


    public void updateUser() {
        System.out.print("Enter your current e-mail to update your data: ");
        String currentEmail = scanner.nextLine();

        System.out.print("Are you sure you want to update your data? (yes/no): ");
        String updateChoice = scanner.nextLine().trim().toLowerCase();

        if (!updateChoice.equals("yes")) {
            System.out.println("Update cancelled.");
            return;
        }

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

        String updateSQL = "UPDATE users SET username = ?, address = ?, telephone = ?, email = ?, dateOfBirth = ? WHERE email = ?";

        try (PreparedStatement updateStmt = connection.prepareStatement(updateSQL)) {
            updateStmt.setString(1, newName);
            updateStmt.setString(2, newAddress);
            updateStmt.setString(3, newTelephone);
            updateStmt.setString(4, newEmail);
            updateStmt.setString(5, newDateOfBirth);
            updateStmt.setString(6, currentEmail);

            int rowsUpdated = updateStmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("User updated successfully!");
            } else {
                System.out.println("Failed to update user. No user found with the provided e-mail.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating user: " + e.getMessage());
        }
    }


    public void readUserById() {
        System.out.print("Insert User ID: ");
        int idSearch = scanner.nextInt();
        scanner.nextLine();  // Consumir quebra de linha

        String selectByID = "SELECT u.*, ut.type_name FROM users u JOIN user_type ut ON u.user_type_id = ut.id WHERE u.id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(selectByID)) {
            stmt.setInt(1, idSearch);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Found User: ");
                    System.out.println("--------------------------");
                    System.out.println("ID: " + rs.getInt("id"));
                    System.out.println("Name: " + rs.getString("username"));
                    System.out.println("E-mail: " + rs.getString("email"));
                    System.out.println("Phone number: " + rs.getString("telephone"));
                    System.out.println("Address: " + rs.getString("address"));
                    System.out.println("Birth date: " + rs.getDate("dateOfBirth"));
                    System.out.println("User Type: " + rs.getString("type_name"));
                    System.out.println("--------------------------");
                } else {
                    System.out.println("User with the ID " + idSearch + " was not found.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving user: " + e.getMessage());
        }
    }


    public void deleteUser() {
        boolean check = false;

        do {
            System.out.print("Enter your administrator e-mail: ");
            String adminEmail = scanner.nextLine();

            if (!adminAUTH.isAdmin(adminEmail)) {
                System.out.println("Access denied. Only administrators can delete users.");
                break;
            }

            System.out.print("Enter the e-mail of the user you want to delete: ");
            String deleteEmail = scanner.nextLine();

            String deleteSQL = "DELETE FROM users WHERE email = ?";

            try (PreparedStatement deleteStmt = connection.prepareStatement(deleteSQL)) {
                deleteStmt.setString(1, deleteEmail);
                int rowsAffected = deleteStmt.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("User deleted successfully.");
                    check = true;
                } else {
                    System.out.println("No user found with the provided e-mail, please try again.");
                }
            } catch (SQLException e) {
                System.out.println("Error during deletion: " + e.getMessage());
                break;
            }
        } while (!check);
    }



    public void deleteAllUsers() {
        System.out.print("Enter your administrator e-mail to confirm deletion of all users: ");
        String adminEmail = scanner.nextLine();

        if (!adminAUTH.isAdmin(adminEmail)) {
            System.out.println("Access denied. Only administrators can delete all users.");
            return;
        }

        System.out.print("Are you sure you want to delete ALL users? This action cannot be undone! (yes/no): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();

        if (!confirmation.equals("yes")) {
            System.out.println("Deletion cancelled.");
            return;
        }

        String deleteAllSQL = "DELETE FROM users";

        try (PreparedStatement deleteStmt = connection.prepareStatement(deleteAllSQL)) {
            int rowsDeleted = deleteStmt.executeUpdate();

            System.out.println(rowsDeleted + " users have been deleted.");
        } catch (SQLException e) {
            System.out.println("Error during deletion: " + e.getMessage());
   }
}


}