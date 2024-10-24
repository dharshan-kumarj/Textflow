import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class RegisterFrame extends JFrame {

    // Constructor to create the registration form
    public RegisterFrame() {
        // Set the frame title
        setTitle("User Registration");

        // Set the layout
        setLayout(new GridLayout(4, 2, 10, 10));

        // Create fields for user input
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(20);

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);

        JButton registerButton = new JButton("Register");

        // Add components to the frame
        add(nameLabel);
        add(nameField);
        add(emailLabel);
        add(emailField);
        add(passwordLabel);
        add(passwordField);
        add(new JLabel()); // Empty label for layout
        add(registerButton);

        // Add action listener for the register button
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                // Validate input
                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(RegisterFrame.this, "All fields must be filled out", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    // Insert user into the MySQL database
                    insertUserIntoDatabase(name, email, password);
                }
            }
        });

        // Set default close operation and window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
    }

    // Method to connect to MySQL and insert user data
    private void insertUserIntoDatabase(String name, String email, String password) {
        String url = "jdbc:mysql://localhost:3306/textflow"; // Ensure your DB name is correct
        String username = "root"; // Your MySQL username
        String dbPassword = "12345"; // Your MySQL password

        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            Connection conn = DriverManager.getConnection(url, username, dbPassword);
            String query = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "User registered successfully!");
            }

            // Close resources
            stmt.close();
            conn.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the database", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Main method to launch the registration form
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RegisterFrame frame = new RegisterFrame();
            frame.setVisible(true);
        });
    }
}
