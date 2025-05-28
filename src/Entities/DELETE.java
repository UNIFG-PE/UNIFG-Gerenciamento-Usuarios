package Entities;

import java.sql.*;
import java.util.Scanner;

public class DELETE {

    private final Connection connection;
    private final Scanner scanner;

    public DELETE(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void deleteUser() {
        boolean check = false;

        do {
            System.out.print("Enter your administrator e-mail: ");
            String adminEmail = scanner.nextLine();

            if (!isAdmin(adminEmail)) {
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

    private boolean isAdmin(String email) {
        String verifyAdminSQL =
                "SELECT ut.type_name " +
                        "FROM users u " +
                        "JOIN user_type ut ON u.user_type_id = ut.id " +
                        "WHERE u.email = ?";

        try (PreparedStatement adminStmt = connection.prepareStatement(verifyAdminSQL)) {
            adminStmt.setString(1, email);

            try (ResultSet rs = adminStmt.executeQuery()) {
                if (rs.next()) {
                    String userType = rs.getString("type_name");
                    return userType.equalsIgnoreCase("Administrator");
                } else {
                    System.out.println("Administrator not found with the provided e-mail.");
                    return false;
                }
            }
        } catch (SQLException e) {
            System.out.println("Verification failed: " + e.getMessage());
            return false;
        }
    }
}