import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://127.0.0.1:3306/USERS";
        String user = "root";
        String password = "1234";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Scanner scanner = new Scanner(System.in)) {

            System.out.print("Enter your name: ");
            String username = scanner.nextLine();
            System.out.print("Enter your password: ");
            String pass = scanner.nextLine();
            System.out.print("Enter your email: ");
            String email = scanner.nextLine();
            System.out.print("Enter you cpf: ");
            String cpf = scanner.nextLine();


            String insertSQL = "INSERT INTO users (username, password, email, cpf) VALUES (?, ?, ?, ?)";
            try (PreparedStatement insertStmt = connection.prepareStatement(insertSQL)) {
                insertStmt.setString(1, username);
                insertStmt.setString(2, pass);
                insertStmt.setString(3, email);
                insertStmt.setString(4, cpf);
                insertStmt.executeUpdate();
                System.out.println("Sucess for user creation!");
            }

            System.out.println("Insert ID User: ");
            int idSearch = scanner.nextInt();
            scanner.nextLine();

            String selectByID = "SELECT * FROM users WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(selectByID)) {
                stmt.setInt(1, idSearch);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        System.out.println("\nFound User: ");
                        System.out.println("ID: " + rs.getInt("id"));
                        System.out.println("Name: " + rs.getInt("username"));
                        System.out.println("Email: " + rs.getInt("email"));
                        System.out.println("CPF: " + rs.getInt("cpf"));
                    }
                    else {
                        System.out.println("User not Found with ID "+ idSearch + " not found.");
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
