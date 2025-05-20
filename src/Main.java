import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://127.0.0.1:3306/USERS";
        String user = "root";
        String password = "Jogador123$";

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


            String insertSQL = "INSERT INTO users (username, password, email, cpf) VALUES (?, ?, ?, ?)";
            try (PreparedStatement insertStmt = connection.prepareStatement(insertSQL)) {
                insertStmt.setString(1, username);
                insertStmt.setString(2, pass);
                insertStmt.setString(3, email);
                insertStmt.setString(4, cpf);
                insertStmt.executeUpdate();
                System.out.println("User created successfully!");
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
