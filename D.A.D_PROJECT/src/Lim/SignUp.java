package Lim;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.Font;
import org.json.JSONObject;

public class SignUp {

	private JFrame frame;
	private JTextField username;
	private JTextField password;
	private JTextField email;
	private JTextField phonenumber;
	private JLabel number;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUp window = new SignUp();
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
	public SignUp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 567, 395);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("USERNAME:");
		lblNewLabel.setBounds(97, 104, 91, 13);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblPassword = new JLabel("PASSWORD:");
		lblPassword.setBounds(97, 127, 91, 13);
		frame.getContentPane().add(lblPassword);
		
		username = new JTextField();
		username.setBounds(215, 98, 96, 19);
		frame.getContentPane().add(username);
		username.setColumns(10);
		
		password = new JTextField();
		password.setBounds(215, 121, 96, 19);
		frame.getContentPane().add(password);
		password.setColumns(10);
		
		JButton btnNewButton = new JButton("Sign Up");
		btnNewButton.setBounds(162, 201, 85, 21);
		frame.getContentPane().add(btnNewButton);
		btnNewButton.addActionListener(e -> signUp());
		
		JLabel lblNewLabel_1 = new JLabel("Sign Up Page");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel_1.setBounds(140, 56, 121, 21);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("EMAIL:");
		lblNewLabel_2.setBounds(97, 150, 45, 13);
		frame.getContentPane().add(lblNewLabel_2);
		
		email = new JTextField();
		email.setColumns(10);
		email.setBounds(215, 144, 96, 19);
		frame.getContentPane().add(email);
		
		phonenumber = new JTextField();
		phonenumber.setBounds(215, 166, 96, 19);
		frame.getContentPane().add(phonenumber);
		phonenumber.setColumns(10);
		
		number = new JLabel("PHONE NUMBER:");
		number.setBounds(97, 169, 91, 13);
		frame.getContentPane().add(number);
	}

	private void signUp() {
		try {
			URL url = new URL("http://localhost/dad_lim/signup.php");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json; utf-8");
			conn.setRequestProperty("Accept", "application/json");
			conn.setDoOutput(true);

			// Create JSON payload
			JSONObject jsonInput = new JSONObject();
			jsonInput.put("username", username.getText());
			jsonInput.put("password", password.getText());
			jsonInput.put("email", email.getText());
			jsonInput.put("phone_number", phonenumber.getText());

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
				JOptionPane.showMessageDialog(frame, "Sign Up successful! Returning to the login page.", "Success", JOptionPane.INFORMATION_MESSAGE);
				frame.setVisible(false);
				
				// Navigate to the login page
				login loginWindow = new login();
				loginWindow.frame.setVisible(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame, "Error during sign up: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	// Add this getter method
	public JFrame getFrame() {
		return frame;
	}
}
