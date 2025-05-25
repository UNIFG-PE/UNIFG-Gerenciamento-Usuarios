import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/user";
        String user = "root";
        String password = "1234";

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
                System.out.print("Choice one option: ");


                int option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1:
                        System.out.print("Enter your name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter your address: ");
                        String address = scanner.nextLine();
                        System.out.print("Enter your telephone: ");
                        String telephone = scanner.nextLine();
                        System.out.print("Enter you e-mail: ");
                        String email = scanner.nextLine();
                        System.out.print("Enter you date of birth(dd-MM-yyyy): ");
                        String dateOfBirth = scanner.nextLine();


                        String insertSQL = "INSERT INTO libraryusers (name, address, telephone, email, dateOfBirth, userType) VALUES (?, ?, ?, ?, ?, ?)";
                        try (PreparedStatement insertStmt = connection.prepareStatement(insertSQL)) {
                            insertStmt.setString(1, name);
                            insertStmt.setString(2, address);
                            insertStmt.setString(3, telephone);
                            insertStmt.setString(4, email);
                            insertStmt.setString(5, dateOfBirth);
                            insertStmt.setString(6, "User");
                            insertStmt.executeUpdate();
                            System.out.println("Sucess for user creation!");
                        } catch (SQLException e) {
                            System.out.println("Error to register user: " + e.getMessage());
                        }
                        break;


                    case 2:

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

                            System.out.print("New date of birth(dd/MM/yyyy): ");
                            String newDateOfBirth = scanner.nextLine();
                            System.out.println("=================");

                            String updateSQL = "UPDATE libraryusers  SET name = ?,  address = ? ,telephone = ?, email = ?, dateOfBirth = ? WHERE id = ?";
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

                    case 3:

                        System.out.println("Enter your email: ");
                        String emailSearch = scanner.nextLine();

                        String selectByEmail = "SELECT * FROM libraryusers WHERE email = ?";
                        try (PreparedStatement stmt = connection.prepareStatement(selectByEmail)) {
                            stmt.setString(1, emailSearch);
                            try (ResultSet rs = stmt.executeQuery()) {
                                if (rs.next()) {
                                    System.out.println("=================");
                                    System.out.println("Found User: ");
                                    System.out.println("ID: " + rs.getInt("id"));
                                    System.out.println("Name: " + rs.getString("name"));
                                    System.out.println("Address: " + rs.getString("address"));
                                    System.out.println("Telephone: " + rs.getString("telephone"));
                                    System.out.println("Email: " + rs.getString("email"));
                                    System.out.println("Date of Birth: " + rs.getString("dateOfBirth"));
                                    System.out.println("=================");

                                    if (rs.getString("userType").equalsIgnoreCase("Administrator")) {
                                        System.out.println("Administrator verified. Listing all registered users:");

                                        String listAllUsersSQL = "SELECT * FROM libraryusers";
                                        try (PreparedStatement listStmt = connection.prepareStatement(listAllUsersSQL);
                                             ResultSet allUsers = listStmt.executeQuery()) {

                                            while (allUsers.next()) {
                                                System.out.println("-------------------------------");
                                                System.out.println("ID: " + allUsers.getInt("id"));
                                                System.out.println("Name: " + allUsers.getString("name"));
                                                System.out.println("Email: " + allUsers.getString("email"));
                                                System.out.println("User Type: " + allUsers.getString("userType"));
                                            }
                                        }
                                    } else {
                                            System.out.println("Access denied. Only Administrators can view all users.");
                                    }
                                } else {
                                    System.out.println("No user found with email: " + emailSearch);
                                }
                            }
                        } catch (SQLException e) {
                            System.out.println("Erro ao atualizar usuário: " + e.getMessage());
                        }
                        break;


                    case 4:

                        boolean Check = false;
                        do {
                            System.out.print("Enter your email (to check if you're an administrator): ");
                            String adminEmail = scanner.nextLine();

                            String verifyAdminSQL = "SELECT * FROM libraryusers WHERE email = ?";
                            try (PreparedStatement adminStmt = connection.prepareStatement(verifyAdminSQL)) {
                                adminStmt.setString(1, adminEmail);
                                try (ResultSet rs = adminStmt.executeQuery()) {
                                    if (rs.next()) {
                                        String userType = rs.getString("userType");
                                        if (!userType.equalsIgnoreCase("Administrator")) {
                                            System.out.println("Access denied. Only administrators can delete users.");
                                            break;
                                        }

                                        System.out.print("Enter the email of the user you want to delete: ");
                                        String deleteEmail = scanner.nextLine();


                                        String deleteData = "DELETE FROM libraryusers  WHERE email  = ?";
                                        try (PreparedStatement deleteStmt = connection.prepareStatement(deleteData)) {
                                            deleteStmt.setString(1, deleteData);
                                            int rowsAffected = deleteStmt.executeUpdate();

                                            if (rowsAffected > 0) {
                                                System.out.println("User deleted successfully.");
                                                Check = true;
                                            } else {
                                                System.out.println("No user found with the provided CPF, please try again");
                                            }
                                        } catch (SQLException e) {
                                            System.out.println("Error during deletion: " + e.getMessage());
                                        }
                                    } else {
                                        System.out.println("Administrator not found with the given email.");
                                        break;
                                    }
                                }
                            } catch (SQLException e) {
                                System.out.println("Error during deletion: " + e.getMessage());
                                break;
                            }
                        } while (!Check);
                        break;


                    case 0:
                        running = false;
                        System.out.println("Saindo...");
                        break;

                    default:
                        System.out.println("Opção inválida! Tente novamente.");
                    }
                }

            String selectAll = "SELECT * FROM users";
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(selectAll)) {

                System.out.println("\n=== Lista de Todos os Usuários ===");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("username");
                    String emailResult = rs.getString("email");
                    String cpfResult = rs.getString("cpf");

                    System.out.println("ID: " + id);
                    System.out.println("Nome: " + name);
                    System.out.println("Email: " + emailResult);
                    System.out.println("CPF: " + cpfResult);
                    System.out.println("-----------------------------");
                }
            }
            }  catch (SQLException e) {
                e.printStackTrace();
        }
    }
}

