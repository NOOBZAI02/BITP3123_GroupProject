package Lim;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

public class user {

    JFrame frame;
    private String userName;
    private JTextField nameField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    user window = new user();
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
    public user() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 858, 551);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Welcome to YSL Hotel Booking System");
        lblNewLabel.setBounds(110, 20, 324, 38);
        frame.getContentPane().add(lblNewLabel);

        nameField = new JTextField();
        nameField.setBounds(100, 60, 200, 20);
        nameField.setEditable(false);
        frame.getContentPane().add(nameField);
        nameField.setColumns(10);

        JButton viewAvailableButton = new JButton("View & Book Room");
        viewAvailableButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Close the current frame and open the ViewRoom frame
                frame.setVisible(false);
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            ViewRoom window = new ViewRoom();
                            window.setUserName(userName);
                            window.frame.setVisible(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        viewAvailableButton.setBounds(100, 110, 200, 25);
        frame.getContentPane().add(viewAvailableButton);

        JButton manageBookingButton = new JButton("Manage Booking");
        manageBookingButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Close the current frame and open the ManageBooking frame
                frame.setVisible(false);
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            UserBooking window = new UserBooking(userName);
                            window.frame.setVisible(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        manageBookingButton.setBounds(100, 145, 200, 25);
        frame.getContentPane().add(manageBookingButton);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Exit the application
                System.exit(0);
            }
        });
        exitButton.setBounds(100, 180, 200, 25);
        frame.getContentPane().add(exitButton);
    }

    public void setUserName(String userName) {
        this.userName = userName;
        nameField.setText(userName);
    }

    // Placeholder method to view bookings
    private void viewBookings() {
        if (userName == null || userName.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter your name.");
            return;
        }
        JOptionPane.showMessageDialog(frame, "Displaying all bookings for " + userName + "...");
        // Implement the logic to retrieve and display bookings for the customer here
    }
}
