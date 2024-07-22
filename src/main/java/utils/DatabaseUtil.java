package utils;

import models.ComputerBuild;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtil {
    private static final String URL = "jdbc:postgresql://localhost:5432/MarksComputerBuildsDB";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Mastercode##99";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
            throw e;
        }
        return connection;
    }

    // Method to validate user credentials
    public static boolean validateUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to insert a new ComputerBuild record
    public static void insertComputerBuild(ComputerBuild computerBuild) throws SQLException {
        String query = "INSERT INTO MarksComputerBuilds (title, case_type, motherboard, cpu, cpu_cooler, ram, gpu, power_supply, mass_storage) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, computerBuild.getTitle());
            pstmt.setString(2, computerBuild.getCaseType());
            pstmt.setString(3, computerBuild.getMotherboard());
            pstmt.setString(4, computerBuild.getCpu());
            pstmt.setString(5, computerBuild.getCpuCooler());
            pstmt.setString(6, computerBuild.getRam());
            pstmt.setString(7, computerBuild.getGpu());
            pstmt.setString(8, computerBuild.getPowerSupply());
            pstmt.setString(9, computerBuild.getMassStorage());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Successfully added new build.");
            } else {
                System.out.println("Failed to add new build.");
            }
        }
    }

    // Method to update an existing ComputerBuild record
    public static void updateComputerBuild(ComputerBuild computerBuild) throws SQLException {
        String sql = "UPDATE MarksComputerBuilds SET title = ?, case_type = ?, motherboard = ?, cpu = ?, cpu_cooler = ?, ram = ?, gpu = ?, power_supply = ?, mass_storage = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, computerBuild.getTitle());
            stmt.setString(2, computerBuild.getCaseType());
            stmt.setString(3, computerBuild.getMotherboard());
            stmt.setString(4, computerBuild.getCpu());
            stmt.setString(5, computerBuild.getCpuCooler());
            stmt.setString(6, computerBuild.getRam());
            stmt.setString(7, computerBuild.getGpu());
            stmt.setString(8, computerBuild.getPowerSupply());
            stmt.setString(9, computerBuild.getMassStorage());
            stmt.setInt(10, computerBuild.getId()); // Ensure the ID is set correctly
            stmt.executeUpdate();
        }
    }

    // Method to delete an existing ComputerBuild record
    public static void deleteComputerBuild(int id) throws SQLException {
        String sql = "DELETE FROM MarksComputerBuilds WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No record found with ID: " + id);
            }
        }
    }

    // Method to get all ComputerBuild records
    public static List<ComputerBuild> getAllComputerBuilds() throws SQLException {
        List<ComputerBuild> builds = new ArrayList<>();
        String sql = "SELECT * FROM MarksComputerBuilds";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ComputerBuild build = new ComputerBuild(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("case_type"),
                        rs.getString("motherboard"),
                        rs.getString("cpu"),
                        rs.getString("cpu_cooler"),
                        rs.getString("ram"),
                        rs.getString("gpu"),
                        rs.getString("power_supply"),
                        rs.getString("mass_storage")
                );
                builds.add(build);
            }
        }
        return builds;
    }
}
