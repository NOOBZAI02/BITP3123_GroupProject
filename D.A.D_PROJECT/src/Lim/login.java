package Lim;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class login {

    JFrame frame;
    private JTextField textField;
    private JTextField textField_1;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    login window = new login();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public login() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 850, 544);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Welcome To The YSL Hotel Booking System");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
        lblNewLabel.setBounds(217, 74, 281, 25);
        frame.getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("NAME:");
        lblNewLabel_1.setBounds(228, 109, 45, 13);
        frame.getContentPane().add(lblNewLabel_1);

        textField = new JTextField();
        textField.setBounds(271, 106, 187, 19);
        frame.getContentPane().add(textField);
        textField.setColumns(10);

        JButton btnNewButton = new JButton("Log In");
        btnNewButton.setBounds(361, 189, 97, 21);
        frame.getContentPane().add(btnNewButton);
        
        textField_1 = new JTextField();
        textField_1.setBounds(271, 135, 187, 19);
        frame.getContentPane().add(textField_1);
        textField_1.setColumns(10);
        
        JLabel lblNewLabel_2 = new JLabel("PASSWORD:");
        lblNewLabel_2.setBounds(203, 138, 70, 13);
        frame.getContentPane().add(lblNewLabel_2);
        
        JButton btnNewButton_1 = new JButton("Sign Up");
        btnNewButton_1.setBounds(218, 189, 85, 21);
        frame.getContentPane().add(btnNewButton_1);

        btnNewButton.addActionListener(e -> {
            String username = textField.getText();
            String password = textField_1.getText();
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter your name and password.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    URL url = new URL("http://localhost/dad_lim/login.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json; utf-8");
                    conn.setRequestProperty("Accept", "application/json");
                    conn.setDoOutput(true);

                    // Create JSON payload
                    JSONObject jsonInput = new JSONObject();
                    jsonInput.put("username", username);
                    jsonInput.put("password", password);

                    // Write JSON input to the connection
                    try (OutputStream os = conn.getOutputStream()) {
                        byte[] input = jsonInput.toString().getBytes("utf-8");
                        os.write(input, 0, input.length);
                    }

                    // Read the response
                    try (BufferedReader br = new BufferedReader(
                            new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                        StringBuilder response = new StringBuilder();
                        String responseLine;
                        while ((responseLine = br.readLine()) != null) {
                            response.append(responseLine.trim());
                        }
                        System.out.println(response.toString());
                        JSONObject jsonResponse = new JSONObject(response.toString());
                        String message = jsonResponse.getString("message");
                        JOptionPane.showMessageDialog(frame, message, "Login Status", JOptionPane.INFORMATION_MESSAGE);
                        if (message.equals("Login successful")) {
                            frame.setVisible(false);
                            user userWindow = new user();
                            userWindow.setUserName(username);
                            userWindow.frame.setVisible(true);
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error during login: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnNewButton_1.addActionListener(e -> {
            frame.setVisible(false);
            SignUp signUpWindow = new SignUp();
            signUpWindow.getFrame().setVisible(true);  // Use the getter method
        });
    }
}
