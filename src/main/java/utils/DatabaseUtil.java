package utils;

import models.ComputerBuild;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DatabaseUtil {
    private static String LOCAL_URL;
    private static String LOCAL_USER;
    private static String LOCAL_PASSWORD;

    private static String REMOTE_URL;
    private static String REMOTE_USER;
    private static String REMOTE_PASSWORD;

    static {
        try {
            Class.forName("org.postgresql.Driver");
            loadDatabaseConfig();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void loadDatabaseConfig() {
        Properties properties = new Properties();
        try (InputStream input = DatabaseUtil.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return;
            }
            properties.load(input);
            LOCAL_URL = properties.getProperty("local.db.url");
            LOCAL_USER = properties.getProperty("local.db.user");
            LOCAL_PASSWORD = properties.getProperty("local.db.password");

            REMOTE_URL = properties.getProperty("remote.db.url");
            REMOTE_USER = properties.getProperty("remote.db.user");
            REMOTE_PASSWORD = properties.getProperty("remote.db.password");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Connection getConnection(boolean useRemote) throws SQLException {
        if (useRemote) {
            return DriverManager.getConnection(REMOTE_URL, REMOTE_USER, REMOTE_PASSWORD);
        } else {
            return DriverManager.getConnection(LOCAL_URL, LOCAL_USER, LOCAL_PASSWORD);
        }
    }

    public static boolean validateUser(String username, String password) {
        String sql = "SELECT password FROM users WHERE username = ?";
        try (Connection connection = getConnection(true); // Change to true for remote
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String storedPassword = rs.getString("password");
                    System.out.println("Stored password: " + storedPassword);
                    System.out.println("Entered password: " + password);
                    return storedPassword.equals(password);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void insertComputerBuild(ComputerBuild computerBuild) throws SQLException {
        String query = "INSERT INTO markscomputerbuilds (title, case_type, motherboard, cpu, cpu_cooler, ram, gpu, power_supply, mass_storage) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(true); // Change to true for remote
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

    public static void updateComputerBuild(ComputerBuild computerBuild) throws SQLException {
        String sql = "UPDATE markscomputerbuilds SET case_type = ?, motherboard = ?, cpu = ?, cpu_cooler = ?, ram = ?, gpu = ?, power_supply = ?, mass_storage = ? WHERE title = ?";
        try (Connection conn = getConnection(true); // Change to true for remote
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, computerBuild.getCaseType());
            stmt.setString(2, computerBuild.getMotherboard());
            stmt.setString(3, computerBuild.getCpu());
            stmt.setString(4, computerBuild.getCpuCooler());
            stmt.setString(5, computerBuild.getRam());
            stmt.setString(6, computerBuild.getGpu());
            stmt.setString(7, computerBuild.getPowerSupply());
            stmt.setString(8, computerBuild.getMassStorage());
            stmt.setString(9, computerBuild.getTitle());
            stmt.executeUpdate();
        }
    }

    public static void deleteComputerBuild(String title) throws SQLException {
        String sql = "DELETE FROM markscomputerbuilds WHERE title = ?";
        try (Connection conn = getConnection(true); // Change to true for remote
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, title);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No record found with title: " + title);
            }
        }
    }

    public static List<ComputerBuild> getAllComputerBuilds() throws SQLException {
        List<ComputerBuild> builds = new ArrayList<>();
        String sql = "SELECT title, case_type, motherboard, cpu, cpu_cooler, ram, gpu, power_supply, mass_storage FROM markscomputerbuilds";
        try (Connection conn = getConnection(true); // Change to true for remote
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ComputerBuild build = new ComputerBuild(
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

    public static ComputerBuild getComputerBuildByTitle(String title) throws SQLException {
        String sql = "SELECT title, case_type, motherboard, cpu, cpu_cooler, ram, gpu, power_supply, mass_storage FROM markscomputerbuilds WHERE title = ?";
        try (Connection conn = getConnection(true); // Change to true for remote
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new ComputerBuild(
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
                } else {
                    throw new SQLException("No record found with title: " + title);
                }
            }
        }
    }
}
