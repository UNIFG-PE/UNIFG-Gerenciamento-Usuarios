package Services;

import java.sql.*;

public class AdminAUTH {

    private final Connection connection;

    public AdminAUTH(Connection connection) {
        this.connection = connection;
    }

    public boolean isAdmin(String email) {
        String verifyAdminSQL =
                "SELECT ut.type_name " +
                        "FROM users u " +
                        "JOIN user_type ut ON u.user_type_id = ut.id " +
                        "WHERE u.email = ? ";

        try (PreparedStatement adminStmt = connection.prepareStatement(verifyAdminSQL)) {
            adminStmt.setString(1, email);

            try (ResultSet rs = adminStmt.executeQuery()) {
                if (rs.next()) {
                    String userType = rs.getString("type_name");
                    return userType.equalsIgnoreCase("Administrator");
                } else {
                    System.out.println("User not found with the provided e-mail.");
                    return false;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error during the authentication: " + e.getMessage());
            return false;
        }
    }
}