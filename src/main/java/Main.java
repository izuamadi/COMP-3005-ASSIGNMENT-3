import java.sql.*;
import java.time.LocalDate;

public class Main {
    private static String url = "jdbc:postgresql://localhost:5432/student_database";
    private static String user = "postgres";
    private static String password = "postgres";


    private static Connection connect() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);
            if (connection != null){
                System.out.println("Connected successful.");
            }else{
                System.out.println("Connection failed.");
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return connection;
    }

    //method to get all students and their information from the database
    public static void getAllStudents(){
        String query = "SELECT * FROM students";
        try (Connection conn = connect();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(query)) {

            while (rs.next()) {
                System.out.println(rs.getInt("student_id") + "\t" +
                                   rs.getString("first_name") + "\t" +
                                   rs.getString("last_name") + "\t" +
                                   rs.getString("email") + "\t" +
                                   rs.getString("enrollment_date"));
            }
        } catch (Exception e) {
            System.out.println("Error retrieving students" + e.getMessage());
        }

    }

    //method to add student and their corresponding information to the database
    public static void addStudent(String first_Name, String last_Name, String email, LocalDate enrollment_date) {
        String query = "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES (?, ?, ?, ?)";
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, first_Name);
            preparedStatement.setString(2, last_Name);
            preparedStatement.setString(3, email);
            preparedStatement.setDate(4, java.sql.Date.valueOf(enrollment_date));
            preparedStatement.executeUpdate();

            System.out.println("Student added successfully " + first_Name + " " + last_Name + " " + email + " " + enrollment_date);
        } catch (Exception e) {
            System.out.println("Student can not be added" + e.getMessage());
        }
    }

    //method to update student email based on student ID
    public static void updateStudentEmail(int studentId, String newEmail) {
        String query = "UPDATE students SET email = ? WHERE student_id = ?";
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, newEmail);
            preparedStatement.setInt(2, studentId);
            int rows = preparedStatement.executeUpdate();

            if (rows > 0)
                System.out.println("Email updated successfully for student_id " + studentId);
            else
                System.out.println("No student found with that ID.");

        } catch (Exception e) {
            System.out.println("Error updating email" + e.getMessage());
        }
    }

    //method to delete student based on student ID
    public static void deleteStudent(int studentId) {
        String query = "DELETE FROM students WHERE student_id = ?";
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, studentId);
            int rows = preparedStatement.executeUpdate();

            if (rows > 0)
                System.out.println("Student deleted successfully");
            else
                System.out.println("No student found with that ID");

        } catch (Exception e) {
            System.out.println("Error deleting student " + e.getMessage());
        }
    }

    //main method to test our application
    public static void main(String[] args) {
        //getAllStudents();
        //addStudent("Izu","Amadi","izuamadi@cmail.carleton.ca",LocalDate.of(2025,6,23));
        //updateStudentEmail(13,"izuamadi2406@gmail.com");
        deleteStudent(13);

    }
}
