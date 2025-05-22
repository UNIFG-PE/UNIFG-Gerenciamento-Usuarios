import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://<host>:<port>/<your-database>";
        String user = "<user>";
        String password = "<your-password>";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Scanner scanner = new Scanner(System.in)) {

            System.out.print("Enter your name: ");
            String username = scanner.nextLine();
            System.out.print("Enter your password: ");
            String pass = scanner.nextLine();
            System.out.print("Enter your email: ");
            String email = scanner.nextLine();
            System.out.print("Enter your cpf: ");
            String cpf = scanner.nextLine();


            String insertSQL = "INSERT INTO users (username, password, email, cpf, status) VALUES (?, ?, ?, ?)";
            try (PreparedStatement insertStmt = connection.prepareStatement(insertSQL)) {
                insertStmt.setString(1, username);
                insertStmt.setString(2, pass);
                insertStmt.setString(3, email);
                insertStmt.setString(4, cpf);
                insertStmt.setString(5, "ACTIVE");
                insertStmt.executeUpdate();
                System.out.println("User created successfully!");
            }

            // luiz felipe

            // Atualização de dados
            System.out.print("Do you want to update your data? (yes/no): ");
            String updateChoice = scanner.nextLine().trim().toLowerCase();

            if (updateChoice.equals("yes")) {
                System.out.println("Enter new data to update:");

                System.out.print("New username: ");
                String newUsername = scanner.nextLine();

                System.out.print("New password: ");
                String newPass = scanner.nextLine();

                System.out.print("New email: ");
                String newEmail = scanner.nextLine();

                System.out.print("New CPF: ");
                String newCpf = scanner.nextLine();

                String updateSQL = "UPDATE users SET username = ?, password = ?, email = ?, cpf = ? WHERE id = ?";
                try (PreparedStatement updateStmt = connection.prepareStatement(updateSQL)) {
                    updateStmt.setString(1, newUsername);
                    updateStmt.setString(2, newPass);
                    updateStmt.setString(3, newEmail);
                    updateStmt.setString(4, newCpf);


                    int rowsUpdated = updateStmt.executeUpdate();
                    if (rowsUpdated > 0) {
                        System.out.println("User updated successfully!");
                    } else {
                        System.out.println("Failed to update user.");
                    }
                }
            }

            //Vinicius Rocha
            boolean Check = false;
            do {
                System.out.print("Enter your cpf to delete: ");
                String deleteData = scanner.nextLine();

                String deleteSQL = "DELETE FROM users WHERE cpf = ?";
                try (PreparedStatement deleteStmt = connection.prepareStatement(deleteSQL)) {
                    deleteStmt.setString(1, deleteData);
                    int rowsAffected = deleteStmt.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("User deleted successfully.");
                        Check = true;
                    } else {
                        System.out.println("No user found with the provided CPF, please try again");
                    }
                }
            } while (!Check);
            //Nícolas

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
