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

            //Nícolas

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
